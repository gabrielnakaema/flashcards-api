package com.example.flashcardsapi.service;

import com.example.flashcardsapi.model.Deck;
import com.example.flashcardsapi.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DeckService {

    private final DeckRepository deckRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public List<Deck> findAll() {
        return deckRepository.findAll();
    }

    public Deck create(Deck deck) {
        return deckRepository.save(deck);
    }

    public Deck update(Deck deck, Long id){
        Deck foundDeck = checkIfExists(id);
        foundDeck.setCards(deck.getCards());
        foundDeck.setDescription(deck.getDescription());
        foundDeck.setTitle(deck.getTitle());
        return deckRepository.save(foundDeck);
    }

    public void delete(Long id){
        Deck foundDeck = checkIfExists(id);
        deckRepository.delete(foundDeck);
    }

    private Deck checkIfExists(Long id) {
        return deckRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found with id: "  + id));
    }
}
