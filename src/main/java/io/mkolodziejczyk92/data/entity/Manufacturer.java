package io.mkolodziejczyk92.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "manufacturer")
public class Manufacturer extends AbstractEntity{

    private String nameOfCompany;
    private String nip;
    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.EAGER)
    private Set<Purchase> purchases;
}
