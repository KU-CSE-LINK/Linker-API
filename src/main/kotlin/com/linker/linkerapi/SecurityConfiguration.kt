package com.linker.linkerapi

import com.linker.linkerapi.security.JwtAuthenticationFilter
import com.linker.linkerapi.security.JwtProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    @Value("\${linker.allowed-origins}")
    private val allowedOrigins: String,
    private val jwtProvider: JwtProvider
) {
    @Bean
    fun filterChain(httpSecurity: HttpSecurity): DefaultSecurityFilterChain? {
        httpSecurity
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/admin/**")
                    .hasAnyAuthority("ADMIN")
            }
            .authorizeHttpRequests { it.requestMatchers("/**").permitAll() }
            .addFilterBefore(
                JwtAuthenticationFilter(allowedOrigins, jwtProvider),
                UsernamePasswordAuthenticationFilter::class.java
            ).logout {
                it.disable()
            }

        return httpSecurity.build()
    }
}


