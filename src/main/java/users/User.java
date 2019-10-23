package users;

import java.time.LocalDate;
import java.util.Objects;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String phone;

    public User(final String userId, final String firstName, final String lastName, final LocalDate birthDate,
                final String email, final String password, final String phone) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                birthDate.equals(user.birthDate) &&
                email.equals(user.email) &&
                password.equals(user.password) &&
                phone.equals(user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, birthDate, email, password, phone);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + firstName + '\'' +
                ", surname='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", hashedPassword='" + password + '\'' +
                ", phoneNumber='" + phone + '\'' +
                '}';
    }
}
