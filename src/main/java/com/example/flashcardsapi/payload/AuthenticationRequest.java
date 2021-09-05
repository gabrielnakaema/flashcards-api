package com.example.flashcardsapi.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank
    @Size(min=5, max=50)
    private String username;

    @NotBlank
    @Size(min=6, max=50)
    private String password;

}
