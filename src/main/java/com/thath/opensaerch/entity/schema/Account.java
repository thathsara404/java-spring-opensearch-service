package com.thath.opensaerch.entity.schema;

import javax.persistence.*;

@Entity
@Table(name="Account")
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", length = 50)
    private String username;


}
