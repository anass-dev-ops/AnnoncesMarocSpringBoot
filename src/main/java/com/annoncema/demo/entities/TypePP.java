package com.annoncema.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class TypePP implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeName;
    @OneToMany(mappedBy = "typePP")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Ad> ads;
}
