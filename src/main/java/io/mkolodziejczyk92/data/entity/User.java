package io.mkolodziejczyk92.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.mkolodziejczyk92.data.enums.ERole;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    private String userName;
    private String firstName;
    private String lastName;

    @Email
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ERole> ERoles;

}
