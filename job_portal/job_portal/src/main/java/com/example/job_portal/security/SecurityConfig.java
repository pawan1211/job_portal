package com.example.job_portal.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtFilter = new JwtAuthFilter(jwtUtil);

        http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth

            // Public endpoints
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/jobs", "/api/jobs/search", "/api/jobs/{id}").permitAll()

            // Candidate-only routes
            .requestMatchers("/api/candidates/**").hasAuthority("ROLE_CANDIDATE")
            .requestMatchers("/api/jobs/*/apply").hasAuthority("ROLE_CANDIDATE")

            // Recruiter-only routes
            .requestMatchers("/api/jobs/mine").hasAuthority("ROLE_RECRUITER")
            .requestMatchers("/api/jobs").hasAuthority("ROLE_RECRUITER") // POST new job
            .requestMatchers("/api/applications/**").hasAuthority("ROLE_RECRUITER")

            // Fallback
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
