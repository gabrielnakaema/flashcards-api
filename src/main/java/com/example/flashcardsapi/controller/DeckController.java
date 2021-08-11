package com.example.flashcardsapi.controller;


import com.example.flashcardsapi.model.Deck;
import com.example.flashcardsapi.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/decks")
@RestController()
public class DeckController {

    private final DeckService deckService;

    @Autowired
    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping
    public List<Deck> getAll() {
        return deckService.findAll();
    }

    @PostMapping
    public Deck create(@RequestBody Deck deck){
        return deckService.create(deck);
    }

    @PutMapping("/{id}")
    public Deck update(@PathVariable("id") Long id, @RequestBody Deck deck) {
        return deckService.update(deck, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        deckService.delete(id);
    }

}
