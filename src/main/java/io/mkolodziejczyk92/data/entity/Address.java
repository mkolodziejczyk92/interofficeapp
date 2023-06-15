package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.EAddressType;
import io.mkolodziejczyk92.data.enums.ECountry;
import io.mkolodziejczyk92.data.enums.EVoivodeship;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


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

    @ManyToMany(mappedBy = "investmentAndResidenceAddresses")
    private Set<Contract> contract;

    public String investmentAddressToString(){
        return "Zip-code: " + zipCode + "\nGmina: " + municipality + "\n Numer działki: " + plotNumber
                + "\n Województwo: " + voivodeship.getNameOfVoivodeship() + "\n Kraj: " + country.getCountry();
    }

    public String residenceAddressToString(){
        if(this.apartmentNumber.isBlank()){
            return "Ulica: " + street + houseNumber + "\n Zip-Code: " + zipCode + "\n Miasto: " + city + "\n Gmina: " + municipality +
                    "\n Województwo: " + voivodeship.getNameOfVoivodeship() + "\n Kraj: " + country.getCountry();
        } else return "Ulica: " + street + " " + houseNumber + "/" + apartmentNumber + "\nZip-Code: " + zipCode + "\nMiasto: " + city + "\nGmina: " + municipality +
                "\nWojewództwo: " + voivodeship.getNameOfVoivodeship() + "\nKraj: " + country.getCountry();
    }


}
