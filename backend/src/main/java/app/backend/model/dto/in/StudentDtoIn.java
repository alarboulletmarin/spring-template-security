package app.backend.model.dto.in;

public class StudentDtoIn {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;

    public StudentDtoIn() {
    }

    public StudentDtoIn(String firstName, String lastName, String email, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }


}
