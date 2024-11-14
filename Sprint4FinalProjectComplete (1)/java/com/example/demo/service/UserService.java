package com.example.demo.service;

import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Pageable;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserService(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        final Optional<User> record = repository.findByUsername(username);

        if (record.isEmpty()) {
            throw new UsernameNotFoundException("User not found - " + username);
        }

        final User user = record.get();

        // @formatter:off
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority()))
        );
        // @formatter:on
    }

    public User save(final User user) {
        return repository.save(user);
    }

    public Optional<User> getUserById(final Integer id) {
        return repository.findById(id);
    }

    public Page<User> findPaginated(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository.findAll(pageable);
    }

    // Add this method
    @Transactional
    public void deleteByUsername(final String username) {
        repository.deleteByUsername(username);
    }
}
