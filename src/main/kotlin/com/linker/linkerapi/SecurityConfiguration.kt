package com.linker.linkerapi

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.DefaultSecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration {
    @Bean
    fun filterChain(httpSecurity: HttpSecurity): DefaultSecurityFilterChain? {
        httpSecurity
            .csrf { it.disable() }
            .authorizeHttpRequests { it.requestMatchers("/**").permitAll() }.logout {
                it.disable()
            }

        return httpSecurity.build()
    }
}


