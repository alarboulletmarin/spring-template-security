package app.backend.model.entity;

public class RegisterRequest {
    private final String username;
    private final String password;
    private final String roleName;

    public RegisterRequest(String username, String password, String roleName) {
        this.username = username;
        this.password = password;
        this.roleName = roleName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRoleName() {
        return roleName;
    }


}

