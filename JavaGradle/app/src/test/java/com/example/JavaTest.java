package com.example;

import com.example.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JavaTest {
    @Autowired
    private TestService service;
    @Test
    public void test(){
        System.out.println(service.test());
    }
}
