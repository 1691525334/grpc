package com.example.grpcdemo.interceptor;


import com.google.common.util.concurrent.RateLimiter;
import io.grpc.*;

public class RateLimitInterceptor implements ServerInterceptor {

    private final RateLimiter rateLimiter = RateLimiter.create(5); // 每秒允许5个请求

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        if (!rateLimiter.tryAcquire()) {
            call.close(Status.RESOURCE_EXHAUSTED.withDescription("Too many requests - rate limit exceeded"), new Metadata());
            return new ServerCall.Listener<>() {}; // 返回空监听器表示不处理
        }

        return next.startCall(call, headers);
    }

}

