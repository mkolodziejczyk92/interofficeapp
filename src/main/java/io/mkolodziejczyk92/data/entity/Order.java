package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.ECommodityType;
import io.mkolodziejczyk92.data.enums.EOrderStatus;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "purchase")
public class Order extends AbstractEntity {


    private String netAmount;
    private String client;
    private String contractNumber;

    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    @OneToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    private String supplierOrderNumber;
    private String comment;

    @Enumerated(EnumType.STRING)
    private ECommodityType commodityType;

}
