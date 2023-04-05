package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.EAddressType;
import io.mkolodziejczyk92.data.enums.ECountry;
import io.mkolodziejczyk92.data.enums.EVoivodeship;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "address")
public class Address extends AbstractEntity {

    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String zipCode;
    private String city;
    private String municipality;
    private String plotNumber;

    @Enumerated(EnumType.STRING)
    private ECountry country;

    @Enumerated(EnumType.STRING)
    private EVoivodeship voivodeship;

    @Enumerated(EnumType.STRING)
    private EAddressType addressType;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;


}
