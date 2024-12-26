package com.muzika.homeworksb.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfigurationMy.class)
class TodoServiceImplTest {
    @Autowired
    private MySQLContainer<?> mysqlContainer;

    @Test
    @DisplayName("Test work through test container config class.")
    void testDatabaseConnection() {
        System.out.println("MySQL Container URL: " + mysqlContainer.getJdbcUrl());
    }
}