package com.melo.bets.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name ="constrasena", nullable = false)
    private String password;

    @Column(name = "saldo")
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Column(name = "estado", nullable = false)
    private String status;

    @Column(name = "documento", nullable = false)
    private String document;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Raffle> raffles;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Bet> bets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RaffleParticipation> participations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BetPurchase> betPurchases;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments;


}
