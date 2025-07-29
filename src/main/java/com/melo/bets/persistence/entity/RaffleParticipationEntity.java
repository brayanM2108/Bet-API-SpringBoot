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
public class RaffleParticipationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "rifa_id", nullable = false)
    private UUID raffleId;

    @Column(name = "usuario_id", nullable = false)
    private UUID userId;

    @Column(name = "fecha_participacion", nullable = false)
    private LocalDateTime participationDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "rifa_id", insertable = false, updatable = false)
    private RaffleEntity raffleEntity;

    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private UserEntity userEntity;


}
