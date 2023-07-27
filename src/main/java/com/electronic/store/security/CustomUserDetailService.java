package com.electronic.store.security;

import com.electronic.store.entites.User;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    /**
     * @param userName the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = this.userRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("user Not Found...!!"));


        return user;
    }
}
