package com.melo.bets.persistence.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "monto", nullable = false)
    private BigDecimal amount;

    @Column(name = "metodo_pago", nullable = false)
    private String paymentMethod;

    @Column(name = "tipo_pago", nullable = false)
    private String paymentType;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Column(name = "usuario_id", nullable = false)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private UserEntity userEntity;

}
