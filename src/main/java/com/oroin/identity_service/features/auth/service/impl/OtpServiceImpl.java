package com.oroin.identity_service.features.auth.service.impl;

import com.oroin.identity_service.common.model.response.ApiResponse;
import com.oroin.identity_service.configuration.OtpConfig;
import com.oroin.identity_service.features.auth.grpc.GrpcMailClient;
import com.oroin.identity_service.features.auth.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import orion.grpc.mail.EmailResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpConfig otpConfig;
    private final StringRedisTemplate redis;
    private final GrpcMailClient mailClient;

    private static final String OTP_USER_PREFIX = "otp:user:";
    private static final String OTP_RATE_PREFIX = "otp:rate:";
    private static final String OTP_IP_PREFIX = "otp:ip:";
    private static final String OTP_BLOCK_PREFIX = "otp:block:";
    private static final String OTP_FAIL_PREFIX = "otp:fail:";

    @Override
    public ApiResponse generateOtp(String email, String ipAddr) {

        /*
         * @ validation
         * */
        blockCheckEmail(email);
        blockCheckIpAddr(ipAddr);
        userRateLimitCheck(email);
        ipRateLimitCheck(ipAddr);

        /*
         * @ generate otp
         * */
        String otp = randomOpt(otpConfig.getDigit());

        /*
         * @ send otp mail
         * */
        String emailBodyHtml = "<span>" + otp + "</span>";
        EmailResponse response = mailClient.sendOtpMailRequestToMailService(
                "Welcome! Please Verify Your Email",
                List.of(email),
                new ArrayList<>(),
                new ArrayList<>(),
                emailBodyHtml
        );

        /*
         * @ if sending mail is success, save in redis , or not
         * */
        if (response.getCode() == 200 && response.getSuccess()) {
            redis.opsForValue().set(OTP_USER_PREFIX + email, otp, otpConfig.getTtl());
            redis.opsForValue().set(OTP_RATE_PREFIX + email, "1", otpConfig.getRateLimitTtl());
            redis.opsForValue().set(OTP_IP_PREFIX + ipAddr, "1", otpConfig.getIpRateLimitTtl());

            return ApiResponse.builder()
                    .code(200)
                    .success(1)
                    .message("successfully sent otp mail to " + email)
                    .data(otp)
                    .metadata(Map.of("timestamp", System.currentTimeMillis()))
                    .build();
        }
        return ApiResponse.builder()
                .code(500)
                .success(0)
                .message(response.getMessage())
                .data(null)
                .metadata(Map.of("timestamp", System.currentTimeMillis()))
                .build();

    }

    @Override
    public ApiResponse verifyOtp(String email, String otp, String ipAddr) {
        String storedOtp = redis.opsForValue().get(OTP_USER_PREFIX + email);

        /*
         * @ validation
         * */
        blockCheckEmail(email);
        blockCheckIpAddr(ipAddr);
        userRateLimitCheck(email);
        ipRateLimitCheck(ipAddr);

        boolean success = false;
        if (storedOtp == null) {
            success = false;
            saveOtpFailAttemptByEmail(email);
            return ApiResponse.builder()
                    .code(success ? 200 : 500)
                    .success(1)
                    .message(success ? "otp verification is successful." : "invalid otp")
                    .data(null)
                    .metadata(Map.of("timestamp", LocalDateTime.now()))
                    .build();
        }
        boolean isMatch = storedOtp.equals(otp);
        if (!isMatch) {
            saveOtpFailAttemptByEmail(email);
            success = false;

        }
        /*
         * @ if correct otp, send back success
         * */
        redis.delete(OTP_USER_PREFIX + email); // delete opt
        redis.delete(OTP_FAIL_PREFIX + email); // delete fail attempts if there are
        success = true;
        return ApiResponse.builder()
                .code(success ? 200 : 500)
                .success(1)
                .message(success ? "otp verification is successful." : "invalid otp")
                .data(null)
                .metadata(Map.of("timestamp", LocalDateTime.now()))
                .build();
    }

    private String randomOpt(int digit) {
        Random random = new Random();
        // 10 power digit
        int bound = (int) Math.pow(10, digit);
        int opt = random.nextInt(bound);
        return String.format("%0" + digit + "d", opt);
    }

    private void userRateLimitCheck(String email) {
        if (Boolean.TRUE.equals(redis.hasKey(OTP_RATE_PREFIX + email))) {
            throw new RuntimeException(String.format("Wait %s seconds before requesting another OTP", otpConfig.getRateLimitTtl().getSeconds()));
        }
    }

    private void ipRateLimitCheck(String ipAddr) {
        if (Boolean.TRUE.equals(redis.hasKey(OTP_IP_PREFIX + ipAddr))) {
            throw new RuntimeException(String.format("Wait %s seconds before requesting another OTP", otpConfig.getIpRateLimitTtl().getSeconds()));
        }
    }

    private void blockCheckEmail(String email) {
        if (Boolean.TRUE.equals(redis.hasKey(OTP_BLOCK_PREFIX + email))) {
            throw new RuntimeException(String.format("Account temporarily blocked %s minutes and %s seconds due to too many failed attempts.", otpConfig.getBlockTtl().getSeconds() / 60 , otpConfig.getBlockTtl().getSeconds() % 60));
        }
    }

    private void blockCheckIpAddr(String ipAddr) {
        if (Boolean.TRUE.equals(redis.hasKey(OTP_BLOCK_PREFIX + ipAddr))) {
            throw new RuntimeException(String.format("Account temporarily blocked %s minutes and %s seconds due to too many failed attempts.",  otpConfig.getBlockTtl().getSeconds() / 60 , otpConfig.getBlockTtl().getSeconds() % 60));
        }
    }

    /*
     * @ save opt verifications fail
     * */
    private void saveOtpFailAttemptByEmail(String email) {
        String failKey = OTP_FAIL_PREFIX + email;

        Long attempts = redis.opsForValue().increment(failKey);

        /*
         * @ first attempt set TTL
         * */
        if (attempts == 1)
            redis.expire(failKey, otpConfig.getBlockTtl());
        if (attempts >= 5)
            redis.opsForValue().set(OTP_BLOCK_PREFIX + email, "1", otpConfig.getBlockTtl());
    }


}
