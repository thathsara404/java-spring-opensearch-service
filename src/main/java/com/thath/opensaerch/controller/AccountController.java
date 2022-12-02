package com.thath.opensaerch.controller;

import com.sun.istack.NotNull;
import com.thath.opensaerch.dto.AccountDTO;
import com.thath.opensaerch.dto.ResponseDTO;
import com.thath.opensaerch.service.specification.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Account specific Endpoints
 * */
@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {

    private IAccountService iAccountService;

    @GetMapping(path = "{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getAccountsByAccountNumber (@PathVariable @NotNull Long accountNumber) {
        AccountDTO account = iAccountService.findByAccountNumber(accountNumber);
        ResponseDTO responseDTO = new ResponseDTO(Arrays.asList(account), null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> getAccountsByAge (@RequestParam @NotNull Long age) {
        List<AccountDTO> accounts = iAccountService.findByAge(age);
        ResponseDTO responseDTO = new ResponseDTO(accounts, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

}
