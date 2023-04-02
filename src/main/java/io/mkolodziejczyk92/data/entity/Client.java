package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.EClientType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "client")
public class Client extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private EClientType clientType;

    private String nip;

    @OneToMany(mappedBy = "client")
    private Set<Address> allAddresses;

    @OneToMany(mappedBy = "client")
    private Set<Invoice> allInvoices;

    @OneToMany(mappedBy = "client")
    private Set<Contract> allContracts;

    @OneToMany(mappedBy = "client")
    private Set<Purchase> allPurchases;

}
