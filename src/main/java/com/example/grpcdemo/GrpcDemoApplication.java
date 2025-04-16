
package com.example.grpcdemo;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.example.grpcdemo.interceptor.RateLimitInterceptor;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.ServerInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.alibaba.nacos.api.naming.pojo.Instance;

@SpringBootApplication
@EnableDiscoveryClient
public class GrpcDemoApplication implements CommandLineRunner {

    @Autowired
    private NamingService namingService; // Nacos命名服务

    public static void main(String[] args) {
        SpringApplication.run(GrpcDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Server server = ServerBuilder.forPort(9090)
                .addService(ServerInterceptors.intercept(new GreeterServiceImpl(),new RateLimitInterceptor()))
                .build()
                .start();
        System.out.println("gRPC server started on port 9090");
        // 注册到 Nacos
        registerToNacos();
        server.awaitTermination();
    }
    private void registerToNacos() throws NacosException {
        // 注册服务到 Nacos
        Instance instance = new Instance();
        instance.setIp("192.168.1.203");
        instance.setPort(9090);
        instance.setServiceName("grpc-server");

        namingService.registerInstance("grpc-server", instance);
        System.out.println("gRPC server registered to Nacos");
    }
}
