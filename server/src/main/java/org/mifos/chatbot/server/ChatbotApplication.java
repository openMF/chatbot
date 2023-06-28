package org.mifos.chatbot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients("org.mifos.chatbot")
@EntityScan(basePackages = {"com.mifos.*"})
@ComponentScan({"org.mifos.chatbot"})
@ComponentScan(basePackages = "org.mifos.chatbot.server")
public class ChatbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotApplication.class, args);
    }

}
