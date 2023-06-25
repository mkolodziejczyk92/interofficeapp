package io.mkolodziejczyk92.data.entity;

import io.mkolodziejczyk92.data.enums.EInvoiceType;
import io.mkolodziejczyk92.data.enums.EPaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "invoice")
public class Invoice extends AbstractEntity {

    private String number;

    @JoinColumn(name = "net_amount")
    private String netAmount;

    @JoinColumn(name = "gross_amount")
    private String grossAmount;

    private String vat;

    private LocalDate issueDate;
    private Integer paymentTime;

    @Builder.Default
    private boolean paid = false;

    @Enumerated(EnumType.STRING)
    private EInvoiceType type;

    @Enumerated(EnumType.STRING)
    private EPaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
