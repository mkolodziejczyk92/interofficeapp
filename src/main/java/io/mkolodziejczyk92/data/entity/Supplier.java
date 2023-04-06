package io.mkolodziejczyk92.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "supplier")
public class Supplier extends AbstractEntity {

    private String nameOfCompany;
    private String nip;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    private Set<Purchase> purchases;
}
