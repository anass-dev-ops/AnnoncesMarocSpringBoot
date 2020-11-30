package com.annoncema.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Ad implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private double price;
    private Date CreationDate;

    // private List<String> imagesS;
    private String image;

    @ManyToOne
    private Category category;
    @ManyToOne
    private City city;

    @ManyToOne
    private TypeOD typeOD; // Offre, Demande
    @ManyToOne
    private TypePP typePP; // Particulier, Professionnel

    private boolean state = false;

    @ManyToOne
    private AppUser appUser;

}
