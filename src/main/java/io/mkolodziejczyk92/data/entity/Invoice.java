package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.EInvoice;
import io.mkolodziejczyk92.data.enums.EPaymentMethod;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "invoice")
public class Invoice extends AbstractEntity {

    private String number;
    private String amount;

    private String issueDate;
    private String paymentTime;

    @Builder.Default
    private boolean paid = false;

    @Enumerated(EnumType.STRING)
    private EInvoice type;

    @Enumerated(EnumType.STRING)
    private EPaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
