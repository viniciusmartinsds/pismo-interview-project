package com.pismo.interview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"spring.liquibase.enabled=false"})
class PismoInterviewProjectApplicationTests {

    @Test
    void contextLoads() {
    }

}
