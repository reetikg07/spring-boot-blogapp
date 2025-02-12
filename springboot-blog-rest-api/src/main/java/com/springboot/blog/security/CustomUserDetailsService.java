package com.springboot.blog.security;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRespository userRespository;
@Autowired
    public CustomUserDetailsService(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
      User user =userRespository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(()->new UsernameNotFoundException("User not found with username or email :"+usernameOrEmail));
      Set<GrantedAuthority> authorites=user.getRoles().stream()
              .map((role)->new SimpleGrantedAuthority(role.getName()))
              .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorites);
    }
}
