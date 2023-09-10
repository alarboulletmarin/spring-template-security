package app.backend.service;

import app.backend.dao.UserRepository;
import app.backend.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This class implements the UserDetailsService interface.
 * It is used by Spring Security to authenticate and authorize a user.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // This is the user repository that we will use to retrieve the user from the database
    private final UserRepository userRepository;

    // This is the password encoder that we will use to encode the password
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor.
     *
     * @param userRepository  the user repository.
     * @param passwordEncoder the password encoder.
     */
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method is used by Spring Security to authenticate and authorize a user.
     *
     * @param username the username identifying the user whose data is required.
     * @return a UserDetails object containing the user's data.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    /**
     * This method is used by Spring Security to get the user's authorities.
     *
     * @param user the user whose authorities are required.
     * @return a collection of authorities.
     */
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }
}
