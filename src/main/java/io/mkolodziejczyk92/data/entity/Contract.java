package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.ECommodityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "contract")
public class Contract extends AbstractEntity {

    private String number;

    private LocalDate signatureDate;
    private LocalDate plannedImplementationDate;

    @Builder.Default
    private boolean completed = false;

    @Enumerated(EnumType.STRING)
    private ECommodityType commodityType;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "contracts_addresses",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private Set<Address> investmentAndResidenceAddresses;

}
