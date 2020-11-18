package com.misolab.multi.site.config;

import com.misolab.core.crypto.CipherTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CipherConfig {

    @Value("${seed.aes}")
    String seed;

    @Bean
    public CipherTemplate aes() {
        CipherTemplate cipherTemplate = new CipherTemplate(seed, "AES");
        return cipherTemplate;
    }
}
