package com.example.test;

import com.example.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestService.class)
public class JavaGradleTest {
    @Autowired
    private TestService service;

    @Test
    public void test() {
        System.out.println(service.test());
    }
}
