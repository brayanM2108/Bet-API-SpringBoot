package com.melo.bets.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "competencias")
@Getter
@Setter
@NoArgsConstructor
public class CompetitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "fecha")
    private LocalDateTime date;

    @Column(name = "categorias_id", nullable = false)
    private UUID categoryId;

    @ManyToOne
    @JoinColumn(name = "categorias_id", insertable = false, updatable = false)
    private CategoryEntity category;

}
