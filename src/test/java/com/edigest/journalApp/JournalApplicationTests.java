package com.edigest.journalApp;

import com.edigest.journalApp.repository.UserRepositoryImpl;
import com.edigest.journalApp.repository.UserRepositoryImplTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JournalApplicationTests {

	@Autowired
	private UserRepositoryImpl userRepository;
	@Test
	public void testSaveNewUser() {
		userRepository.getUsersForSA();
	}

}
