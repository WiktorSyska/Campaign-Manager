package com.futurum.campaign_manager;

import com.futurum.campaign_manager.repository.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CampaignManagerApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> {});
	}

	@Test
	void testDatabaseConnection() throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			assertTrue(connection.isValid(1));
		}
	}

	@Test
	void testApiEndpoint() {
		ResponseEntity<String> response = restTemplate.getForEntity("/api/campaigns", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
