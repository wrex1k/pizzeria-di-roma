package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.dto.RegisterRequest;
import com.pizzeriadiroma.pizzeria.entity.Role;
import com.pizzeriadiroma.pizzeria.entity.User;
import com.pizzeriadiroma.pizzeria.exception.UserNotFoundException;
import com.pizzeriadiroma.pizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public String validateAndRegister(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return "Passwords do not match";
        }

        if (existsByEmail(registerRequest.getEmail())) {
            return "Email already exists";
        }

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        return null;
    }

    @Transactional
    public void save(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public String changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return "Current password is incorrect.";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        save(user);

        return null;
    }

    @Transactional
    public String deleteUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Password is incorrect.";
        }

        userRepository.delete(user);
        return null;
    }
}
