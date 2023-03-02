package com.nahush.springboot.firstrestapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCommandLineRunner implements CommandLineRunner {
    private UserDetailRepository repository;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public UserCommandLineRunner(UserDetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.save(new UserDetail("Nahush", "Admin"));
        repository.save(new UserDetail("Kanishka", "Admin"));
        repository.save(new UserDetail("Rahul", "User"));
        List<UserDetail> users = repository.findAll();
        users.forEach(user -> logger.info(user.toString()));
        repository.findByRole("Admin").forEach(user -> logger.info(user.toString()));
    }
}
