package app.backend.service;

import app.backend.dao.RoleRepository;
import app.backend.model.entity.Role;
import org.springframework.stereotype.Service;

/**
 * RoleService class is used to handle the role related operations
 */
@Service
public class RoleService {

    // Autowire the RoleRepository bean
    private final RoleRepository roleRepository;

    /**
     * RoleService constructor
     *
     * @param roleRepository RoleRepository bean
     */
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * This method is used to create a new role if it doesn't exist
     *
     * @param roleName Role name
     * @return Role object
     */
    public Role createOrRetrieveRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }
}
