package com.example.flashcardsapi.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CardRequest {

    @NotBlank
    private String question;

    @NotBlank
    private String answer;

    private String hint;
}
