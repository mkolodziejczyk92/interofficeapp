package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.ECommodityType;
import io.mkolodziejczyk92.data.enums.EPurchaseStatus;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "purchase")
public class Purchase extends AbstractEntity {


    private String netAmount;
    private String contractNumber;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.STRING)
    private EPurchaseStatus status;

    private String supplierPurchaseNumber;
    private String comment;

    @Enumerated(EnumType.STRING)
    private ECommodityType commodityType;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name="manufacturer_id")
    private Manufacturer manufacturer;

}
