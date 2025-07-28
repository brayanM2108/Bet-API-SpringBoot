package com.melo.bets.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Competition {
    private UUID id;
    private String name;
    private String date;
    private String categoryId;
}