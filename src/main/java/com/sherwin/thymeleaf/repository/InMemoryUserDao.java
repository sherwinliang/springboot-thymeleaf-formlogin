package com.sherwin.thymeleaf.repository;

import com.sherwin.thymeleaf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component("inMemoryUserDao")
public class InMemoryUserDao implements UserDao{

    private static AtomicLong counter = new AtomicLong();

    private static final Map<String, User> userMap = new ConcurrentHashMap();
    @Value("${test.username}")//from application properties
    private String defaultUsername;
    @Value("${test.password}")//from application properties
    private String defaultPassword;
    @Autowired
    PasswordEncoder myPasswordEncoder;
    /* @description: Default username and password are set. Remember password should be encoded.
     * @author: Sherwin Liang
     * @timestamp: 2021/2/27 16:33
     * @param:
     * @return:
    */
    @PostConstruct
    public void init() {
        if(!userMap.isEmpty()){
            userMap.clear();
        }
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        User user = new User(counter.getAndIncrement(),defaultUsername,myPasswordEncoder.encode(defaultPassword),null, roles);
        userMap.put(user.getUsername(), user);
    }

    @Override
    public Iterable<User> findAll() {
        return userMap.values();
    }

    @Override
    public boolean save(User user) {
        user.setId(counter.getAndIncrement());
        user.setPassword(myPasswordEncoder.encode(user.getPassword()));
        Assert.isNull(userMap.get(user.getUsername()),"This username is already existed.");
        return userMap.put(user.getUsername(), user)!=null;
    }

    @Override
    public boolean update(User user) {
        return userMap.put(user.getUsername(), user)!=null;
    }

    @Override
    public User findByUsername(String username) {
        return userMap.get(username);
    }

    @Override
    public boolean deleteById(Long id) {
        return userMap.remove(id) !=null;
    }


}
