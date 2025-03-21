package com.codecool.backend.repository.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;


    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private final Set<UserEntity> friends = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Set<UserEntity> getFriends() {
        return friends;
    }

    public void addFriend(UserEntity user) {
        if (user != null && !this.equals(user)) {
            friends.add(user);
            user.getFriends().add(this);
        }
    }

    public void deleteFriend(UserEntity user) {
        if(user != null && !this.equals(user)) {
            friends.remove(user);
            user.getFriends().remove(this);
        }
    }
}
