package ru.bortnik.first;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstApplication {

    public static void main(String[] args) throws Exception {
        Server.createTcpServer().start();
        SpringApplication.run(FirstApplication.class, args);
    }

}
