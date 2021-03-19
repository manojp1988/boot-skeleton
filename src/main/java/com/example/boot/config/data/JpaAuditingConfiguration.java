package com.example.boot.config.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {
       return () -> {

           final Authentication authentication = SecurityContextHolder.getContext()
                                                                      .getAuthentication();
           String auditor = "System";
           if(authentication != null){
               auditor = authentication.getName();
           }

           return Optional.ofNullable(auditor);
       };
    }
}
