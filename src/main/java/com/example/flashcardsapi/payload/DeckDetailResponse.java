package com.example.flashcardsapi.payload;

import com.example.flashcardsapi.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeckDetailResponse {

    private Long id;
    private String title;
    private String description;
    private Long cardCount;
    private User user;

}
