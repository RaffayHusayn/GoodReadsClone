package com.example.goodreadsclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@SpringBootApplication
//@RestController
public class GoodReadsCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodReadsCloneApplication.class, args);
    }

      /*
       Using the downloaded Secure-Connect-Goodreads bundle to connect this SpringBoot
       application to the remote Astra.Datastax instance
      */
    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }


}
