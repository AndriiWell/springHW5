package com.muzika.homeworksb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
class HomeworkApplicationTests {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Test connection started") // Added to check work with containers and without.
	void testConnection() {
		Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
		Assertions.assertEquals(1, result);
		log.warn("Test connection finished");
	}
}
