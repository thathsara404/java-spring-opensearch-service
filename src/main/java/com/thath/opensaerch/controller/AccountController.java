package com.thath.opensaerch.controller;

import com.thath.opensaerch.dto.AccountDTO;
import com.thath.opensaerch.dto.ResponseDTO;
import com.thath.opensaerch.service.specification.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Account specific Endpoints
 * */
@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

    private IAccountService iAccountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getAccountsByAge (@RequestParam Long accountNumber) {
        AccountDTO account = iAccountService.findByAccountNumber(accountNumber);
        ResponseDTO responseDTO = new ResponseDTO(Arrays.asList(account), null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

}
