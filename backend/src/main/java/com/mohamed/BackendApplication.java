package com.mohamed;

import com.mohamed.Entities.User;
import com.mohamed.Enums.Role;
import com.mohamed.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	/*
		if an admin user exists in the database.
		If not, it creates a new admin user with the email "admin@mail.com" and the password "admin"
	*/
	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return args -> {
			User adminAcc = userRepository.findByRole(Role.ADMIN);
			if (adminAcc == null) {
				User newAdminAccount = new User();
				newAdminAccount.setUsername("admin");
				newAdminAccount.setEmail("admin@mail.com");
				newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
				newAdminAccount.setFirstName("Admin");
				newAdminAccount.setLastName("Admin");
				newAdminAccount.setPhone("00000000000");
				newAdminAccount.setAge(30);
				newAdminAccount.setRole(Role.ADMIN);
				userRepository.save(newAdminAccount);
				System.out.println("ADMIN Account Created Successfully.");
			}
		};
	}

}
