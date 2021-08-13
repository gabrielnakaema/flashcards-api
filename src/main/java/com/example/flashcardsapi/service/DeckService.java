package com.example.flashcardsapi.service;

import com.example.flashcardsapi.model.Deck;
import com.example.flashcardsapi.payload.DeckDetailResponse;
import com.example.flashcardsapi.repository.CardRepository;
import com.example.flashcardsapi.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DeckService {

    private final DeckRepository deckRepository;

    private final CardRepository cardRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
    }

    public List<Deck> getAll() {
        return deckRepository.findAll();
    }

    public Deck createDeck(Deck deck) {
        return deckRepository.save(deck);
    }

    public Deck updateDeck(Deck deck, Long id){
        Deck foundDeck = checkIfExists(id);
        foundDeck.setCards(deck.getCards());
        foundDeck.setDescription(deck.getDescription());
        foundDeck.setTitle(deck.getTitle());
        return deckRepository.save(foundDeck);
    }

    public DeckDetailResponse getDeckById(Long id){
        Deck foundDeck = checkIfExists(id);
        Long cardCount = cardRepository.countByDeckId(id);
        return DeckDetailResponse
                .builder()
                .id(foundDeck.getId())
                .title(foundDeck.getTitle())
                .description(foundDeck.getDescription())
                .cardCount(cardCount)
                .build();
    }

    public void deleteDeck(Long id){
        Deck foundDeck = checkIfExists(id);
        deckRepository.delete(foundDeck);
    }

    private Deck checkIfExists(Long id) {
        return deckRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found with id: "  + id));
    }
}
