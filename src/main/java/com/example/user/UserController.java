package com.example.user;

import com.example.user.api.IUser;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4204"}, maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("user")
public class UserController {

    @Autowired private UserService userService;

    @GetMapping(value="all")
    @ApiOperation(value = "Get All Users",	notes = "Return a list of Users", response = List.class)
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping(value="/{id}")
    @ApiOperation(value = "Get User By ID",	notes = "Return a User given his id", response = User.class)
    public List<User> findById(@PathVariable String id) {
        return userService.findById(id);
    }

    @PostMapping("/save")
    @ResponseBody
    @ApiOperation(value = "save a user",	notes = "Save a list of IUsers from Angular", response = IUser.class)
    public ResponseEntity<User> save(@RequestBody User user) {
        User us = userService.save(user);
        if(null != us.getId()) {
            log.info("added User : " + us.toString());
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/user/{id}")
                    .buildAndExpand(us.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(us);
        }else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }


    @PutMapping("/saveAll")
    @ResponseBody
    @ApiOperation(value = "Save a list of users",	notes = "Save a list of user from Angular", response = List.class)
    public ResponseEntity<List<User>> saveAll(@RequestBody List<User> users) {

        log.debug(">>>>>>>>>  RestController==== saveAll Called !!! ");
        List<User> us = userService.saveAll(users);
        if(null != us) {
            log.info("saved Users : " + us.toString());
            URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/all")
                    .buildAndExpand(us)
                    .toUri();
            return ResponseEntity.created(uri).body(us);
        }else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a user",	notes = "Delete a user from an id", response = IUser.class)
    public ResponseEntity<User> delete(@PathVariable String id) {
        log.debug("========== Delete  : " + id);
        User user = userService.delete(id);
        if(null!=user) {
            log.info( "==== Deleted : " + user.toString());
            return ResponseEntity.accepted().body(user);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="allGroupByGroupId")
    @ApiOperation(value = "Get All Users Group By ID",	notes = "Return All Users Group By ID", response = User.class)
    public Map<String, List<User>> findAllGroupByGroupId() {
        return userService.findAllGroupByGroupId();
    }
}
