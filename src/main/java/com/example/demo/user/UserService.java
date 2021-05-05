package com.example.demo.user;

import com.example.demo.product.Product;
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

    public void create(String name, String pass, String email, String type) {
        User u = new User();
        u.setName(name);
        u.setPassword(bCryptPasswordEncoder.encode(pass));
        u.setEmail(email);
        u.setType(type);
        userRepository.save(u);
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

}