package ua.vholovetskyi.gateway.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class GatewayController {

    @GetMapping("/profile")
    public String hello() {
        return "Hello World";
    }
}
