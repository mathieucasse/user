package com.example.user;

import com.example.user.api.IUser;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of= {"id","name", "groupId"})
@ToString(of= {"id","name", "groupId"})
public class User implements IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String groupId;

    public User(String name, String groupId){
        this.name = name;
        this.groupId = groupId;
    }

}
