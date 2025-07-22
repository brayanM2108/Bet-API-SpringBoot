package com.melo.bets.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "apuestas")
@Setter
@Getter
@NoArgsConstructor
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "titulo", nullable = false)
    private String title;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @Column(name = "cuota", nullable = false)
    private BigDecimal odds;

    @Column(name = "evento")
    private String event;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime date;

    @Column(name = "estado", nullable = false)
    private String status;

    @Column(name = "precio", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "creador_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "bet", cascade = CascadeType.ALL)
    private List<BetPurchase> purchases;


}
