package com.example.flashcardsapi.payload;

import com.example.flashcardsapi.model.Deck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsResponse {

    private String username;
    private String name;
    private Long userId;
    private List<Deck> deckList;

    public List<Deck> getDeckList() {
        return Objects.requireNonNullElseGet(deckList, ArrayList::new);
    }
}
