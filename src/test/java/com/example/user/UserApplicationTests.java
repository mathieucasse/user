package com.example.user;

import com.example.user.api.IUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserApplicationTests {

    @Autowired private UserService userService;

    @Test
    public void contextLoads() {
        List<User> users = new ArrayList<>();
        users.add(new User("AAA", "A"));
        users.add(new User("AAB", "A"));
        users.add(new User("ABB", "A"));
        users.add(new User("BBB", "B"));
        users.add(new User("BBA", "B"));
        users.add(new User("CCC", "C"));
        users = userService.saveAll(users);
        log.debug(users.toString());
        assertNotNull("Shoul not be null !!", users);
        assertTrue("Id Should be set" , null != users.get(0).getId());
    }
}
