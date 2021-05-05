package com.dataart.cerebro.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Boolean orderable;

    // FIXME: 5/5/2021 do we need non-used field?
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "services_of_category",
    joinColumns = @JoinColumn(name = "category_id"),
    inverseJoinColumns = @JoinColumn(name = "service_id"))
    private Set<AdditionalService> additionalServices;

    // FIXME: 5/5/2021 dead code
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "category_of_types",
//    joinColumns = @JoinColumn(name = "category_id"),
//    inverseJoinColumns = @JoinColumn(name = "type_id"))
//    private Set<Type> types;

}
