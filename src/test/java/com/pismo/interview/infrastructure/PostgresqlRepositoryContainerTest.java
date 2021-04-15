package com.pismo.interview.infrastructure;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
public abstract class PostgresqlRepositoryContainerTest {

    @Container
    static PostgreSQLContainer postgreContainer = new PostgreSQLContainer(DockerImageName.parse("postgres:13.2"));

    @Container
    static GenericContainer redisContainer = new GenericContainer(DockerImageName.parse("redis:6.2.1")).withExposedPorts(6379);

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreContainer::getUsername);
        registry.add("spring.datasource.password", postgreContainer::getPassword);
        registry.add("spring.redis.host", redisContainer::getContainerIpAddress);
        registry.add("spring.redis.port", redisContainer::getFirstMappedPort);
    }
}
