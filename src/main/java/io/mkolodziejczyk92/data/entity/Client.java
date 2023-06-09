package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.EClientType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "client")
public class Client extends AbstractEntity {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private EClientType clientType;

    private String nip;

    private boolean officeClient;

    @NotNull
    private String addedBy;

    @OneToMany(mappedBy = "client", fetch = EAGER)
    private Set<Address> allAddresses;

    @OneToMany(mappedBy = "client")
    private Set<Invoice> allInvoices;

    @OneToMany(mappedBy = "client")
    private Set<Contract> allContracts;

    @OneToMany(mappedBy = "client")
    private Set<Purchase> allPurchases;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
