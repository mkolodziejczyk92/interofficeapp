package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.ECommodityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "contract")
public class Contract extends AbstractEntity {

    private String number;
    private String netAmount;
    private LocalDate signatureDate;
    private LocalDate plannedImplementationDate;

    @Builder.Default
    private boolean completed = false;

    @Enumerated(EnumType.STRING)
    private ECommodityType commodityType;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;


}
