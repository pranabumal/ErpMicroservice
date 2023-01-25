package com.pranab.inventory.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="inventory")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private Integer quantity;

}
