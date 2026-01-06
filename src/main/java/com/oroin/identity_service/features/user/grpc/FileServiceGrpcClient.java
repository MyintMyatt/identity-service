package com.oroin.identity_service.features.user.grpc;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import orion.grpc.file_service.FileServiceGrpc;
import orion.grpc.file_service.MetaData;
import orion.grpc.file_service.UploadRequest;
import orion.grpc.file_service.UploadResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class FileServiceGrpcClient {

    private static final int CHUNK_SIZE = 1024 * 1024;

    @GrpcClient("fileServer")
    private FileServiceGrpc.FileServiceStub fileServiceStub;

    public String uploadFile(MultipartFile file) throws IOException {

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> fileIdRef = new AtomicReference<>();
        AtomicReference<Throwable> errorRef = new AtomicReference<>();

        StreamObserver<UploadResponse> responseObserver = new StreamObserver<UploadResponse>() {
            @Override
            public void onNext(UploadResponse uploadResponse) {
                fileIdRef.set(uploadResponse.getFileId());
            }

            @Override
            public void onError(Throwable throwable) {
                errorRef.set(throwable);
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };


        StreamObserver<UploadRequest> requestObserver = fileServiceStub.uploadFile(responseObserver);

        try {
            /*
             * send metadata
             * */

            requestObserver.onNext(
                    UploadRequest.newBuilder()
                            .setMetadata(
                                    MetaData.newBuilder()
                                            .setFilename(file.getOriginalFilename())
                                            .setFiletype(file.getContentType())
                                            .setFilesize(file.getSize())
                                            .build()
                            )
                            .build()
            );


            /*
             *  stream chunks
             * */

            try (InputStream in = file.getInputStream()) {
                byte[] buffer = new byte[CHUNK_SIZE];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    requestObserver.onNext(
                            UploadRequest.newBuilder()
                                    .setChunk(
                                            ByteString.copyFrom(buffer, 0, bytesRead)
                                    )
                                    .build()
                    );
                }
            } catch (Exception e) {
                requestObserver.onError(e);
                throw e;
            }

            latch.await(2, TimeUnit.MINUTES);

            if (errorRef.get() != null) {
                throw new RuntimeException("Upload failed", errorRef.get());
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return fileIdRef.get();
    }
}
