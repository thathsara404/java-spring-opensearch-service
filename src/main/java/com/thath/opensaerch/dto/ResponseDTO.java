package com.thath.opensaerch.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Data
public class ResponseDTO <T extends ISuccessResponse> {

    private List<T> data;

    private ErrorResponseDTO error;

}
