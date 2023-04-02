package io.mkolodziejczyk92.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany(mappedBy = "supplier")
    private Set<Purchase> purchases;
}
