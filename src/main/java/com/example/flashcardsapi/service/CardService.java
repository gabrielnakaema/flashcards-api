package com.example.flashcardsapi.service;

import com.example.flashcardsapi.model.Card;
import com.example.flashcardsapi.model.Deck;
import com.example.flashcardsapi.repository.CardRepository;
import com.example.flashcardsapi.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    @Autowired
    public CardService(CardRepository cardRepository, DeckRepository deckRepository) {
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    }

    public Card addCard(Long deckId, Card card){
        Deck foundDeck = checkIfDeckExists(deckId);
        Card newCard = new Card();
        newCard.setAnswer(card.getAnswer());
        newCard.setHint(card.getHint());
        newCard.setQuestion(card.getQuestion());
        newCard.setDeck(foundDeck);

        return cardRepository.save(newCard);
    }

    public List<Card> getCardsByDeckId(Long deckId){
        checkIfDeckExists(deckId);
        return cardRepository.findByDeckId(deckId);
    }

    public Card updateCard(Long deckId, Long id, Card card) {
        checkIfDeckExists(deckId);
        Card foundCard = checkIfCardExists(id);
        foundCard.setQuestion(card.getQuestion());
        foundCard.setHint(card.getHint());
        foundCard.setAnswer(card.getAnswer());
        return cardRepository.save(foundCard);
    }

    public void deleteCard(Long deckId, Long id) {
        checkIfDeckExists(deckId);
        Card foundCard = checkIfCardExists(id);
        cardRepository.delete(foundCard);
    }

    private Deck checkIfDeckExists(Long deckId) {
        return deckRepository.findById(deckId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found with id: " + deckId));
    }

    private Card checkIfCardExists(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found with id: " + cardId));

    }

}