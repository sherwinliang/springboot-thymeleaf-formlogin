package com.sherwin.thymeleaf.service;

import com.sherwin.thymeleaf.model.User;
import com.sherwin.thymeleaf.repository.InMemoryUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    InMemoryUserDao inMemoryUserDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = inMemoryUserDao.findByUsername(username);
        if(user!=null){
            List<GrantedAuthority> authorities = new ArrayList<>();
            for(String role:user.getRoles()){
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
            }
            org.springframework.security.core.userdetails.User securityUser =
                    new org.springframework.security.core.userdetails.User(
                            user.getUsername(), user.getPassword(), authorities);
            return securityUser;
        }else{
            throw new UsernameNotFoundException("Username is not existed.");
        }
    }
}
