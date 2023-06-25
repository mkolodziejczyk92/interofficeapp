package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.ECommodityType;
import io.mkolodziejczyk92.data.enums.EPurchaseStatus;
import io.mkolodziejczyk92.data.enums.EVat;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "purchase")
public class Purchase extends AbstractEntity {


    private String netAmount;
    private String grossAmount;
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

    @Enumerated(EnumType.STRING)
    private EVat eVat;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name="manufacturer_id")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "purchase")
    private List<Invoice> allInvoices;

}
