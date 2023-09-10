package app.backend;

import app.backend.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }


    /**
     * This method is used to create the roles if they don't exist
     *
     * @param roleService RoleService bean
     * @return CommandLineRunner object
     */
    @Bean
    public CommandLineRunner initRoles(RoleService roleService) {
        return args -> {
            roleService.createOrRetrieveRole("ADMIN");
            roleService.createOrRetrieveRole("USER");
            roleService.createOrRetrieveRole("SUPERADMIN");
        };
    }
}
