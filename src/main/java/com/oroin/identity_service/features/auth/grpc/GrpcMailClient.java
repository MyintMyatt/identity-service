package com.oroin.identity_service.features.auth.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import orion.grpc.mail.EmailRequest;
import orion.grpc.mail.EmailResponse;
import orion.grpc.mail.EmailServiceGrpc;

import java.util.List;

@Component
public class GrpcMailClient {

    @GrpcClient("mailServer")
    private EmailServiceGrpc.EmailServiceBlockingStub emailServiceBlockingStub;

    public EmailResponse sendOtpMailRequestToMailService(String subject, List<String> receivers, List<String> cc, List<String> bcc, String emailBodyHtml) {
        EmailRequest request = EmailRequest.newBuilder()
                .setSubject(subject)
                .addAllReceivers(receivers)
                .addAllCc(cc)
                .addAllBcc(bcc)
                .setEmailBodyHtml(emailBodyHtml)
                .build();

        return emailServiceBlockingStub.sendEmail(request);
    }
}
