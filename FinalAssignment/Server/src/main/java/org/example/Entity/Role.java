package org.example.Entity;

import java.util.List;
import java.util.Set;

public class Role {
    private Long id;
    private String name;
    private List<User> user;

    public Role(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
