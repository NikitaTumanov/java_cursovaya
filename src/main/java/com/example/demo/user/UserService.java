package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = userRepository.findByName(s);
        return u;
    }

    public void create(String name, String pass, String email) {
        User u = new User();
        u.setName(name);
        u.setPassword(bCryptPasswordEncoder.encode(pass));
        u.setEmail(email);
        userRepository.save(u);
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

}