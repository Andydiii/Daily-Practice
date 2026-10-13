package com.andy.todo_api;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import jakarta.servlet.DispatcherType;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

// this class is registered as bean by annotation @Configuration
@Configuration 
public class SecurityConfig {
    // Spring calls this method while starting the app. If configuring or building the filter chain throws an exception, the method passes it back to Spring(Spring is the caller). Spring can then report that startup failed. It is not an exception sent to the frontend for a normal /tasks request.
    // Because this is a Spring @Bean method, Spring supplies the HttpSecurity object when it calls the method. You do not call securityFilterChain(...) yourself or create new HttpSecurity(...). You do not need to write an @Bean method for HttpSecurity. Spring Boot detects the security libraries when the app starts and activates its security configuration; Spring Security then makes HttpSecurity available to your SecurityFilterChain method.
    // The method returns a SecurityFilterChain object. @Bean tells Spring to keep that returned object and use it as part of the application.
    // In your app, the CSRF protection is turned on  when you added Spring Security and register your own SecurityFilterChain. Spring Security enables CSRF protection by default.
    @Bean 
    SecurityFilterChain securityFilterChain(HttpSecurity http, CsrfTokenRepository csrfTokenRepository) throws Exception {
        http.csrf((csrf) -> {
            csrf.csrfTokenRepository(csrfTokenRepository);
        });

        http.authorizeHttpRequests((auth) -> {
            // This allows matched requests without an authenticated user.
            // Spring Security recommends permitting ERROR dispatches so Spring Boot can return the actual error response. This does not make normal /tasks requests public.
            auth.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/csrf").permitAll()
                // .anyRequest().authenticated() is the fallback rule: every request that did not match an earlier rule requires a signed-in user.
                .anyRequest().authenticated();
        });

        // Up to this point, http holds the rules we configured. build() creates the SecurityFilterChain from them, and return gives that object back to Spring. Spring then uses that chain when HTTP requests arrive.
        return http.build();
    }

    // Spring finds the @Bean method in your @Configuration class and registers the object it returns. Other Spring beans receive that object through injection; they do not need to call securityContextRepository() directly. You can add public if you prefer—it works too
    // Tells Spring to call this method and manage the object it returns, so other Spring components can receive it through injection.
    // leaving out the access modifier means package-private (also called default access). 
    // package-private: ordinary code in the same package can call it, but code in other packages cannot.
    @Bean
    SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
            new RequestAttributeSecurityContextRepository(),
            new HttpSessionSecurityContextRepository()
        );
    }
    // SecurityContextRepository: The method’s return type. This is an interface for saving and retrieving a SecurityContext, which holds authentication information.
    // securityContextRepository(): The method’s name. By default, it also becomes the bean’s name. There are no parameters because this method creates its own objects.
    // return: Gives the finished repository object to Spring.
    // new DelegatingSecurityContextRepository(...): Creates a repository that passes saving and loading work to the repositories inside its parentheses.
    // new RequestAttributeSecurityContextRepository(): Keeps the security context available during the current HTTP request, including an error dispatch within that request.
    // new HttpSessionSecurityContextRepository(): Stores the security context in the user’s server-side HTTP session, so it can be loaded on a later request.
    // A server-side HTTP session is a place on the backend that holds data for one browser’s ongoing visit. Server: Keeps a session, such as session ABC, containing data for that browser.

    @Bean
    // CsrfTokenRepository is the interface for storing and retrieving CSRF tokens.
    CsrfTokenRepository csrfTokenRepository() {
        // HttpSessionCsrfTokenRepository stores each token in its own HTTP session. Making the repository a bean does not give every user the same token
        return new HttpSessionCsrfTokenRepository();
    }

}
