package com.example

import com.example.service.TestService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KotlinTest {
    @Autowired
    lateinit var service: TestService
    @Test
    fun test(){
        println(service.test())
    }
}