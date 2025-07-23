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
@Table(name = "rifas")
@Getter
@Setter
@NoArgsConstructor
public class RaffleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "titulo" , nullable = false)
    private String title;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "precio_participacion", nullable = false)
    private BigDecimal ticketPrice;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "estado", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "creador_id", nullable = false)
    private UserEntity creator;

    @OneToMany(mappedBy = "raffleEntity", cascade = CascadeType.ALL)
    private List<RaffleParticipationEntity> participations;
}
