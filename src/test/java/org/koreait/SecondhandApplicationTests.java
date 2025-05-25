package org.koreait;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"default", "test"})
class SecondhandApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void contextLoads() {
		long total = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM MEMBER", long.class);
		System.out.println(total);
	}
}
