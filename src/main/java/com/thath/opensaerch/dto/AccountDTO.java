package com.thath.opensaerch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO implements ISuccessResponse {

    private Long account_number;

    private String address;

    private Long balance;

    private String city;

    private Long age;

}
