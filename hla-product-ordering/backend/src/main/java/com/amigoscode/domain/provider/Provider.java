package com.amigoscode.domain.provider;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@ToString
public class Provider {

    private Integer id;
    private String name;
    private String email;
    private ZonedDateTime createdAt;
    private Integer createdBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return Objects.equals(id, provider.id) && Objects.equals(name, provider.name) && Objects.equals(email, provider.email) && Objects.equals(createdAt, provider.createdAt) && Objects.equals(createdBy, provider.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, createdAt, createdBy);
    }
}
