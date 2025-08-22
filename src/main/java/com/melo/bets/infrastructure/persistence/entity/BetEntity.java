package com.melo.bets.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "apuestas")
@Setter
@Getter
@NoArgsConstructor
public class BetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "titulo", nullable = false)
    private String title;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "cuota", nullable = false)
    private BigDecimal odds;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime date;

    @Column(name = "estado")
    private Boolean status;

    @Column(name = "precio", nullable = false)
    private BigDecimal price;

    @Column(name = "resultado")
    private String result;

    @Column(name = "imagen")
    private String image;

    @Column(name = "tipo_apuesta")
    private String betType;

    @Column(name = "creador_id")
    private UUID userId;

    @Column(name = "competicion_id")
    private UUID competitionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creador_id", insertable = false, updatable = false)
    private UserEntity creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competicion_id", insertable = false, updatable = false)
    private CompetitionEntity competition;


}
