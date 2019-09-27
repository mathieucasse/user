package com.example.user;

import com.example.user.api.IUser;
import com.example.user.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService<User, String> {

    @Autowired private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findById(String id) {
        List<User> users = new ArrayList<>();
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            users.add(user.get());
        }
        return users;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> users) {
        return userRepository.saveAll(users);
    }

    @Override
    public <S extends User> S save(S user) {
        return userRepository.save(user);
    }

    @Override
    public User delete(String id) {
        User user = userRepository.getOne(id);
        userRepository.deleteById(id);
        return user;
    }

    @Override
    public Map<String, List<User>> findAllGroupByGroupId() {
        List<User> all = this.findAll();
        Map<String, List<User>> map =  all.stream().collect(Collectors.groupingBy(IUser::getGroupId));
        return map;
    }
}
