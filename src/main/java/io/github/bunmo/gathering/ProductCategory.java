package io.github.bunmo.gathering;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_category")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    private List<Gathering> gatherings = new ArrayList<>();
}
