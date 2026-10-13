package com.andy.todo_api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController 
public class AuthController {
    private final AuthService authService;
    // declares a field for the repository that saves and retrieves login information.
    private final SecurityContextRepository securityContextRepository;

    public AuthController(AuthService authService, SecurityContextRepository SecurityContextRepository) {
        this.authService = authService;
        this.securityContextRepository = SecurityContextRepository;
    }

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public int register(@RequestBody RegisterUserRequest request) {
        // authService.register returns the postgreSQL generated Id for the new user got created.
        return authService.register(request);
    }

    @GetMapping("/auth/csrf")
    // I think we dont need to make it a bean in SecurityConfig is beacause its autoly registered by Spring when we import the Csrftoken? didnt confirm yet. confirm with gpt.
    public CsrfToken getCsrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }

    @PostMapping("/auth/login")
    public int login(
        @RequestBody LoginRequest loginRequest,
        //  "HttpServletRequest request" represents the incoming request POST /auth/login. It contains information such as its headers and cookies.
        HttpServletRequest request,
        // response is the outgoing HTTP response to this login request. Spring gives your controller a HttpServletResponse object for it. A response can contain a status, headers, cookies, and a body.
        HttpServletResponse response
    ) {
        // check whether the username/password is correct.
        int userId = authService.authenticate(loginRequest);


        /* s
        var authentication = ...: var asks Java to infer the variable’s type. Here its type is UsernamePasswordAuthenticationToken

        UsernamePasswordAuthenticationToken.authenticated(userId, null, List.of()): creates an object that tells Spring Security the credentials have already been verified. userId is the user identity, null means no password is stored in this object, and List.of() is an empty list of roles or permissions. This line belongs after your password check.

        purpose:
            my own words: mark the user is authenticated.

            For example, if the service verifies a user and returns ID 7, this creates an object meaning: “User 7 has been verified; no credentials or roles are stored in this object.” We put that object in the SecurityContext so Spring Security can recognize the login.

            There is also unauthenticated(principal, credentials), which creates an object with isAuthenticated() == false for credentials that have not been verified yet. The difference is a security promise, so only call authenticated(...) after your password check succeeds.

            Your authService.authenticate(loginRequest) checks the password and returns an int user ID. But Spring Security cannot treat a plain number as proof that the user is logged in. It expects an Authentication object in the SecurityContext.

            authenticated(...) is a static factory method that creates a UsernamePasswordAuthenticationToken whose isAuthenticated() value is true. It does not check the password. Your AuthService.authenticate(loginRequest) must do that first.

        
        */
        var authentication = UsernamePasswordAuthenticationToken.authenticated(
            userId,  // principal: WHO the user is
            null,  // credentials: no password stored here
            List.of() // authorities: roles/permissions; none assigned yet
        );

        /* 
        1. gets the current HTTP session, creating one if necessary. The current HTTP session means the server-side session associated with that request’s session cookie:
            - If the login request includes a JSESSIONID cookie for an existing session, request.getSession() returns that session. For example, it may be the session created when you called GET /auth/csrf.
            - If there is no existing session, request.getSession() creates one and returns it.

        2. Purpose: Ensure a session exists. If there is no session, getSession() creates one. That matters because changeSessionId() needs a session to change; it would throw an exception without one. If the CSRF request already created a session, getSession() simply finds it.
        */
        request.getSession();

        /*
        1. changeSessionId() changes the session ID after login. The session remains, but its identifying cookie value changes; this protects against someone having fixed a session ID before login.

        2. changeSessionId()  returns a value—the new ID—but we have no need to read it in our code. The server handles the session ID change and sends the updated cookie to the client. 

        3. purpose: Imagine someone learns your browser to use session ID ABC before you log in. If your app keeps ABC after login, that person could try using the same ID to access your logged-in session.
            So after the password is verified, we change it:
            - Before login: session ID ABC
            - After login: session ID XYZ
            - The browser receives the new session cookie. The old ID can no longer identify that logged-in session. The session’s data stays; only its ID changes. Because we wrote our own login endpoint, we included this step explicitly.
        */
        request.changeSessionId();

        /*
        before this step we have done:
            1. userid -> 2. UsernamePasswordAuthenticationToken object(means the user is authenticated)
        now we are doing:
            -> 3. create a context(a container for "who is logged in") and put the 2. in it 
            -> 4. use it by make the context available to Spring Security (the holder is where Spring Security checks for the current authentication)
            -> 5. save it for later requests.
        */
        // Creates a fresh, empty SecurityContext. Think of it as a container for “who is logged in.” We create a new one so we can fill it with this login’s authentication information.
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        // Places the verified UsernamePasswordAuthenticationToken—containing your user ID—inside that container. Now the context says who the authenticated user is.
        context.setAuthentication(authentication);
        // Makes that context available to Spring Security during this HTTP request. The holder is the place Spring Security checks for the current authentication.
        SecurityContextHolder.setContext(context);
        // Saves the context through the repository we configured. Its session repository can load that authentication when the browser sends its session cookie with the next HTTP request. With our custom login method, setting the holder alone does not save it for later. 

        /*
        The returned userId is part of the response—specifically, its body. HttpServletResponse response represents the whole outgoing HTTP response while your method is running.

        For example, after return 7, the response could look roughly like:

            HTTP/1.1 200 OK
            Set-Cookie: JSESSIONID=XYZ

            7

        Here, 7 is the body produced from your return value. The response parameter gives Spring and the session handling code access to the response’s headers and cookies before it is sent.
        
        The order is:
            1. The server creates an initially empty HttpServletResponse and passes it to your login method.
            2. saveContext(context, request, response) runs. It receives that response object; the body does not need to exist yet.
            3. Your method reaches return userId;.
            4. Spring takes that returned number and writes it into the same response’s body.
            5. The server sends the completed response to the client.
            So passing response to saveContext does not mean the response already contains userId. It’s an object that Spring is still building.
        */
        securityContextRepository.saveContext(context, request, response);
        return userId;
    }
}
