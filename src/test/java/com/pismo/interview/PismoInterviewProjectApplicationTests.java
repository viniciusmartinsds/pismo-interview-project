package com.pismo.interview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "spring.liquibase.enabled=false")
@SpringBootTest
class PismoInterviewProjectApplicationTests {

    @Test
    void contextLoads() {
    }

}
