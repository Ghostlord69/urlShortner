package com.am.urlShortnerBackend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class ClickEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate clickDate;

    @ManyToOne
    @JoinColumn(name = "url_mmapping_id")
    private urlMapping urlMapping;
}
