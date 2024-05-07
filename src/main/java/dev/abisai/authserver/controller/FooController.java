package dev.abisai.authserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @GetMapping(path = "/api/foo")
    public String foo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return "foo";
    }
}