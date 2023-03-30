package io.mkolodziejczyk92.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

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
}
