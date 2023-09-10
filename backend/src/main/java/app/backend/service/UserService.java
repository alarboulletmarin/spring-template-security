package app.backend.service;

import app.backend.config.security.JwtTokenProvider;
import app.backend.dao.RoleRepository;
import app.backend.dao.UserRepository;
import app.backend.model.entity.Role;
import app.backend.model.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserService class is used to handle the user related operations
 */
@Service
public class UserService {

    // Autowire the PasswordEncoder, UserRepository, RoleRepository and JwtTokenProvider beans
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    /**
     * UserService constructor
     *
     * @param passwordEncoder  PasswordEncoder bean
     * @param userRepository   UserRepository bean
     * @param roleRepository   RoleRepository bean
     * @param jwtTokenProvider JwtTokenProvider bean
     */
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * This method is used to create a new user
     *
     * @param username Username
     * @param password Password
     * @param roleName Role name
     * @return User object
     */
    public User createUser(String username, String password, String roleName) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    /**
     * This method is used to update the password of a user
     *
     * @param userId      User id
     * @param newPassword New password
     * @return User object
     */
    public User updateUserPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
}
