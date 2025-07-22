package com.melo.bets.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "participaciones_rifas")
@Getter
@Setter
@NoArgsConstructor
public class RaffleParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "rifa_id", nullable = false)
    private Raffle raffle;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    @Column(name = "fecha_participacion", nullable = false)
    private LocalDateTime participationDate = LocalDateTime.now();
}
