package com.thath.opensaerch.exception;

import com.thath.opensaerch.dto.ResponseDTO;
import com.thath.opensaerch.dto.ErrorResponseDTO;
import com.thath.opensaerch.enums.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handle exception and build error response.
 * */
@ControllerAdvice
public class GenericErrorHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ResponseDTO> generateAccountNotFoundResponse(AccountNotFoundException accountNotFoundException) {
        ResponseDTO responseDTO = new ResponseDTO(null, new ErrorResponseDTO(ErrorMessage.NOT_FOUND.toString()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler(UnExpectedResultFoundException.class)
    public ResponseEntity<ResponseDTO> generateUnExpectedDataResultResponse(AccountNotFoundException accountNotFoundException) {
        ResponseDTO responseDTO = new ResponseDTO(null, new ErrorResponseDTO(ErrorMessage.UNEXPECTED_RESULT_FOUND.toString()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

}
