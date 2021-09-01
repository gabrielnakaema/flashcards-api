package com.example.flashcardsapi.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeckRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;


}
