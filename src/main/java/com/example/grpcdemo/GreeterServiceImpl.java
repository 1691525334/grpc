
package com.example.grpcdemo;

import io.grpc.stub.StreamObserver;

public class GreeterServiceImpl extends HelloServiceGrpc.HelloServiceImplBase{
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
