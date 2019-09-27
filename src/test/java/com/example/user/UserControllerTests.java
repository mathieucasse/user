package com.example.user;

import com.example.user.api.IUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    static public List<User> users;
    static public User user = new User("1","ZZZ","Z");
    static {
        users = new ArrayList<>();
        users.add(new User("AAA", "A"));
        users.add(new User("AAB", "A"));
        users.add(new User("ABB", "A"));
        users.add(new User("BBB", "B"));
        users.add(new User("BBA", "B"));
        users.add(new User("CCC", "C"));
    }

    @Test
    public void shouldGetAll() throws Exception {
        when(userServiceMock.findAll()).thenReturn(users);

        mockMvc.perform(get("/user/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("AAA")))
                .andExpect(content().string(containsString("BBA")));
    }

    @Test
    public void shouldGetOne() throws Exception {

        List<User> lu = new ArrayList<>();
        lu.add(user);

        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(lu);
        log.debug(jsonUser);

        when(userServiceMock.findById(any(String.class))).thenReturn(lu);

        mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(jsonUser));
    }

    @Test
    public void shouldSaveAll() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonUsers = mapper.writeValueAsString(users);
        log.debug(jsonUsers);
        when(userServiceMock.saveAll(any(List.class))).thenReturn(UserControllerTests.users);

        mockMvc.perform(put("/user/saveAll")
                .content(jsonUsers)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string("Location", "http://localhost/all"))
                .andExpect(content().json(jsonUsers));
    }

    @Test
    public void shouldSaveOne() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(user);
        log.debug(jsonUser);
        when(userServiceMock.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user/save")
                .content(jsonUser)
                .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string("Location", "http://localhost/user/1"))
                .andExpect(content().json(jsonUser));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonUser = mapper.writeValueAsString(user);
        log.debug(jsonUser);
        when(userServiceMock.delete(any(String.class))).thenReturn(user);
        this.mockMvc.perform(delete("/user/1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(jsonUser));
    }

    @Test
    public void shouldFindAllGroupByGroupId() throws Exception {
        Map<String, List<User>> map =  users.stream().collect(Collectors.groupingBy(IUser::getGroupId));
        ObjectMapper mapper = new ObjectMapper();
        String jsonUsers = mapper.writeValueAsString(map);
        log.debug(jsonUsers);

        when(userServiceMock.findAllGroupByGroupId()).thenReturn(map);

        mockMvc.perform(get("/user/allGroupByGroupId"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(jsonUsers));
    }

}
