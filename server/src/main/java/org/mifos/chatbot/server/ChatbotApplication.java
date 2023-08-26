package org.mifos.chatbot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableFeignClients({"org.mifos.chatbot"})
@SpringBootApplication(scanBasePackages = { "org.mifos.chatbot.server.service", "org.mifos.chatbot" })
@ComponentScan(basePackages = { "org.mifos.chatbot.server", "org.mifos.chatbot.server.service" })
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ChatbotApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatbotApplication.class, args);
    }

}
