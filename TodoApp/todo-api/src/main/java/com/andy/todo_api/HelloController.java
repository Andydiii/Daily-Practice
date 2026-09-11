package com.andy.todo_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // 告诉 Spring：这个 class负责接收 HTTP request并返回 response。
public class HelloController {
    @GetMapping("/hello") // 收到 GET /hello 时，执行下面的 hello() 方法。
    public String hello() {
        return "Hello, Spring Boot!";
    }
}

