package com.example.flashcardsapi.controller;


import com.example.flashcardsapi.model.Deck;
import com.example.flashcardsapi.payload.DeckDetailResponse;
import com.example.flashcardsapi.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/v1/decks")
@RestController()
public class DeckController {

    private final DeckService deckService;

    @Autowired
    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping
    public List<Deck> getAllDecks() {
        return deckService.getAll();
    }

    @GetMapping("/{id}")
    public DeckDetailResponse getDeckById(@PathVariable("id") Long id){return deckService.getDeckById(id);}

    @PostMapping
    public Deck createDeck(@RequestBody Deck deck){
        return deckService.createDeck(deck);
    }

    @PutMapping("/{id}")
    public Deck updateDeck(@PathVariable("id") Long id, @RequestBody Deck deck) {
        return deckService.updateDeck(deck, id);
    }

    @DeleteMapping("/{id}")
    public void deleteDeck(@PathVariable("id") Long id) {
        deckService.deleteDeck(id);
    }

}
