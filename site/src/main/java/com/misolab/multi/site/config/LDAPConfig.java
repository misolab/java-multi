package com.misolab.multi.site.config;

import com.misolab.core.util.LDAPAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LDAPConfig {

    @Value("${ldap.principal}")
    private String principal;

    @Value("${ldap.providerUrl}")
    private String providerUrl;

    @Bean
    public LDAPAuthenticator ldap() {
        LDAPAuthenticator ldap = new LDAPAuthenticator(principal, providerUrl);
        return ldap;
    }
}
