package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.dto.RegisterRequest;
import com.pizzeriadiroma.pizzeria.entity.Role;
import com.pizzeriadiroma.pizzeria.entity.User;
import com.pizzeriadiroma.pizzeria.exception.UserNotFoundException;
import com.pizzeriadiroma.pizzeria.exception.ValidationException;
import com.pizzeriadiroma.pizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public void validateAndRegister(RegisterRequest registerRequest) {
        String password = registerRequest.getPassword();
        String confirmPassword = registerRequest.getConfirmPassword();

        if (!StringUtils.hasText(password) || !StringUtils.hasText(confirmPassword)) {
            throw new ValidationException("Password and confirm password are required.");
        }

        if (!password.equals(confirmPassword)) {
            throw new ValidationException("Passwords do not match.");
        }

        if (existsByEmail(registerRequest.getEmail())) {
            throw new ValidationException("Email already exists.");
        }

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Transactional
    public void save(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional
    public void changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (!StringUtils.hasText(currentPassword) || !StringUtils.hasText(newPassword)) {
            throw new ValidationException("Current password and new password are required.");
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new ValidationException("Current password is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        save(user);
    }

    @Transactional
    public void deleteUser(String email, String currentPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (!StringUtils.hasText(currentPassword)) {
            throw new ValidationException("Password is required.");
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new ValidationException("Current password is incorrect.");
        }

        userRepository.delete(user);
    }
}
