package com.thath.opensaerch.entity.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long account_number;

    private String address;

    private Long balance;

    private String city;

    private Long age;

    private String email;

    private String employer;

    private String firstname;

    private String gender;

    private String lastname;

    private String state;

}
