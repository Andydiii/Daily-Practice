package com.andy.todo_api;


import org.springframework.security.config.Customizer;

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

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// this class is registered as bean by annotation @Configuration
@Configuration 
public class SecurityConfig {
    // Spring calls this method while starting the app. If configuring or building the filter chain throws an exception, the method passes it back to Spring(Spring is the caller). Spring can then report that startup failed. It is not an exception sent to the frontend for a normal /tasks request.
    // Because this is a Spring @Bean method, Spring supplies the HttpSecurity object when it calls the method. You do not call securityFilterChain(...) yourself or create new HttpSecurity(...). You do not need to write an @Bean method for HttpSecurity. Spring Boot detects the security libraries when the app starts and activates its security configuration; Spring Security then makes HttpSecurity available to your SecurityFilterChain method.
    // The method returns a SecurityFilterChain object. @Bean tells Spring to keep that returned object and use it as part of the application.
    // In your app, the CSRF protection is turned on  when you added Spring Security and register your own SecurityFilterChain. Spring Security enables CSRF protection by default.
    @Bean 
    SecurityFilterChain securityFilterChain(HttpSecurity http, CsrfTokenRepository csrfTokenRepository) throws Exception {
        // tells Spring Security to use those rules before its authentication checks.
        /*
            Enable CORS handling in the security filter chain, using the available CORS configuration.
            Spring can obtain those rules from:
                - An explicit CorsConfigurationSource bean, like the one we proposed.
                - Spring MVC’s CORS configuration, including controller @CrossOrigin annotations, when no explicit source is supplied.
            So it can reuse controller rules, but it does more than copy annotations: it enables a filter that handles CORS before requests reach controllers

            For /tasks, Spring MVC can find your controller’s CORS rules. However, /auth/logout is handled by Spring Security’s LogoutFilter, without a controller method. A controller annotation therefore does not provide rules for that endpoint.
        */
        http.cors(Customizer.withDefaults());

        /*
        sets the repository Spring Security will use to generate, save, load, and remove the expected CSRF token. In your setup, that repository stores the token in the server-side HTTP session. This line configures future requests; it does not generate a token immediately at startup.
        */
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

        /*
            With CSRF protection enabled, logout requires POST and a valid CSRF token. The logout filter handles this request, so you don’t need a @PostMapping method in AuthController
        */
        // configures spring security's logout handling
        http.logout((logout) -> {
            // Sets the URL the frontend will call
            logout.logoutUrl("/auth/logout")
            // invalidate the server-side session containing the saved login
            /*
                HTTP session
                    SecurityContext
                        Authentication — user ID, login status, authorities
                    Other session data
            */
            .invalidateHttpSession(true)
            // clears authentication from the current secirity context
            // Session invalidation and authentication clearing are already defaults; we are writing them explicitly so the intended behaviour is clear
            .clearAuthentication(true)
            // sends an instruction to the browser to remove the session cookie
            .deleteCookies("JSESSIONID")
            // Defines the response after successful logout
            .logoutSuccessHandler((request, response, authentication) -> {
                // Returns 204 instead of redirecting to a login page
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            });
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

    // Registers the returned configuration source with Spring.
    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {

        // 	Holds the allowed origins, methods, headers and credentials setting.
        CorsConfiguration configuration = new CorsConfiguration();

        // allows your react frontend's origin
        configuration.setAllowedOrigins(
            List.of("http://localhost:5173")
        );

        // 	Allows these HTTP methods in cross-origin requests.
        configuration.setAllowedMethods(
            List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        // Allows JSON’s Content-Type header and your CSRF header.
        configuration.setAllowedHeaders(
            List.of("Content-Type", "X-CSRF-TOKEN")
        );

        //	Allows credentialed cross-origin requests, including session cookies. Your frontend still needs credentials: "include"
        configuration.setAllowCredentials(true);

        /*
            Associates CORS rules with URL paths. Applies these rules to every path, including /auth/logout.

            For /tasks, Spring MVC can find your controller’s CORS rules. However, /auth/logout is handled by Spring Security’s LogoutFilter, without a controller method. A controller annotation therefore does not provide rules for that endpoint.

            Our proposed configuration: provides CORS rules for all URL paths, including /tasks and /auth/logout. That is why we’re adding it. It still allows only the origins, methods and headers you specify.
        */
        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

/* Explaination of Security configuration and how does a request uses security configuration.
2. Do requests follow the configuration lines one by one?

These lines run when the application starts:
http.cors(...);
http.csrf(...);
http.authorizeHttpRequests(...);
http.logout(...);

They configure and build filters. They are not instructions executed again for every incoming request.

Spring puts its standard filters in a predefined order. A request proceeds through the selected chain until a filter either:
- Passes it to the next filter.
- Rejects it and returns a response.
- Handles it successfully and returns a response.
Therefore, a request does not necessarily reach every filter or a controller. docs.spring.io

Considering only the four features you mentioned, their relevant order is:
Order	Filter	Main question
1	CorsFilter	Does this cross-origin request satisfy our CORS rules?
2	CsrfFilter	Does this request require a CSRF token, and is it valid?
3	LogoutFilter	Is this a request to our logout endpoint?
4	AuthorizationFilter	Does this request satisfy the configured access rule?


There are other filters between these. Logout comes before authorization, even though you wrote .logout(...) afterward. docs.spring.io

3. Example: a logged-in user loads tasks

Assume React at localhost:5173 sends GET localhost:8080/tasks with the session cookie.
1. CORS: The frontend origin is allowed. Add the appropriate response headers and continue.
2. CSRF: GET does not require token validation. Continue.
3. Logout: /tasks does not match /auth/logout. Continue.
4. Authorization: /tasks requires authentication. The session identifies a logged-in user, so allow it.
5. Controller: getTasks() runs and retrieves that user’s tasks.
Notice: passing through a filter does not mean its main action happens. GET passes through CsrfFilter, but the filter skips token validation. docs.spring.io

4. Example: the browser sends a logout preflight

Before the actual POST with your custom CSRF header, the browser may send:
OPTIONS /auth/logout
Origin: http://localhost:5173
Access-Control-Request-Method: POST
Access-Control-Request-Headers: x-csrf-token

CorsFilter checks the requested origin, method and headers.

If allowed, it returns the preflight response immediately. This OPTIONS request stops there: no CSRF validation, no logout, no authorization check, no controller. If the preflight fails the CORS check, it also stops at CorsFilter, but returns a rejection—normally 403 Forbidden.

| Preflight result | Server response | Browser’s next action |
|---|---|---|
| **Allowed** | Successful response with CORS permission headers | Sends the actual POST/DELETE/etc. |
| **Rejected** | Error response, normally 403 | Does **not** send the actual request; reports a CORS error |

In both cases, the preflight does not continue to CSRF, logout, authorization, or the controller. Other filters earlier in the chain may already have run, so saying it “only goes through CORS” is a slight simplification.

The browser can then send a separate POST request. Preflight only asks permission; it does not log the user out.

5. Example: the actual logout POST
POST /auth/logout
Cookie: JSESSIONID=...
X-CSRF-TOKEN: ...
Origin: http://localhost:5173

1. CORS: Allowed origin → continue.
2. CSRF: Valid token → continue.
3. Logout: Matches POST /auth/logout → invalidate the session, clear authentication, and run your success handler.
4. Your success handler returns 204 No Content.
Processing ends at logout. This request does not reach AuthorizationFilter or a controller. docs.spring.io

6. Examples where a request fails
Request	                                            Where it stops
Preflight from a disallowed origin	                CORS: rejects it.
Logout POST with a missing or invalid  CSRF token	CSRF: rejects it before logout happens.
GET /tasks after logout	                            Authorization: rejects it because there is no authenticated session.


A missing or invalid CSRF token normally produces 403 Forbidden. docs.spring.io

The distinction to remember is: configuration creates the filter chain at startup; each request travels through that chain only as far as needed.

*/