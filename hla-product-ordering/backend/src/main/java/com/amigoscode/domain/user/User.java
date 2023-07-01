package com.amigoscode.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@ToString
public class User {

    Integer id;
    String email;
    String name;
    String password;
    UserRole role;
    ZonedDateTime createdAt;


    public User withPassword(String newPassword) {
        return new User(
                id,
                email,
                name,
                newPassword,
                role,
                createdAt);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(role, user.role)) return false;
        if (!isItTheSameDate(createdAt, user.createdAt)) return false;
        return true;
    }

    private boolean isItTheSameDate(final ZonedDateTime date1, final ZonedDateTime date2) {
        if(date1 == null && date2 == null) return true;
        if(date1 != null && date2 == null) return false;
        if(date1 == null && date2 != null) return false;
        return Objects.equals(date1.toInstant(), date2.toInstant());
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}