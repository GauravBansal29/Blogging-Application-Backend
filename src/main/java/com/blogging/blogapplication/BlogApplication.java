package com.blogging.blogapplication;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blogging.blogapplication.Entities.Role;
import com.blogging.blogapplication.Repositories.RoleRepository;

@SpringBootApplication
public class BlogApplication {

	@Autowired
	private RoleRepository roleRepo;

	public static void main(String[] args) {

		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public void run(String... args) throws Exception {
		try {

			Role role1 = new Role();
			role1.setId((long) 1);
			role1.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setId((long) 2);
			role2.setName("ROLE_USER");

			List<Role> roles = List.of(role1, role2);

			this.roleRepo.saveAll(roles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
