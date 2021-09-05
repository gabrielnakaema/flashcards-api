package com.example.flashcardsapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashcardsApiResponse {
    private String message;
    private Integer status;
    private Object data;

    public FlashcardsApiResponse(String message, Integer status) {
        this.message = message;
        this.status = status;
    }
}
