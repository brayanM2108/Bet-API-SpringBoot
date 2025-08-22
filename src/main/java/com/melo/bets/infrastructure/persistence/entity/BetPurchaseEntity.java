package com.melo.bets.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "compras_apuestas")
@Getter
@Setter
@NoArgsConstructor
public class BetPurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "fecha_compra")
    private LocalDateTime purchaseDate = LocalDateTime.now();

    @Column(name = "apuesta_id", nullable = false)
    private UUID betId;

    @Column(name = "usuario_id", nullable = false)
    private UUID userId;

    @Column(name = "numero_orden", nullable = false, unique = true)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "apuesta_id", insertable = false, updatable = false)
    private BetEntity betEntity;

    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private UserEntity userEntity;


}
