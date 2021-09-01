package com.example.flashcardsapi.service;

import com.example.flashcardsapi.model.Card;
import com.example.flashcardsapi.model.Deck;
import com.example.flashcardsapi.payload.CardRequest;
import com.example.flashcardsapi.repository.CardRepository;
import com.example.flashcardsapi.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    @Autowired
    public CardService(CardRepository cardRepository, DeckRepository deckRepository) {
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    }

    public List<Card> addCards(Long deckId, List<CardRequest> cards){
        Deck foundDeck = checkIfDeckExists(deckId);
        List<Card> newCards = cards.stream().map(cardR -> {
            Card newCard = new Card();
            newCard.setDeck(foundDeck);
            newCard.setQuestion(cardR.getQuestion());
            newCard.setAnswer(cardR.getAnswer());
            newCard.setHint(cardR.getHint());
            return newCard;
        }).collect(Collectors.toList());
        return cardRepository.saveAll(newCards);
    }

    public List<Card> getCardsByDeckId(Long deckId){
        checkIfDeckExists(deckId);
        return cardRepository.findByDeckId(deckId);
    }

    public List<Card> getRandomCardsByDeckId(Long deckId) {
        checkIfDeckExists(deckId);
        List<Card> cards =  cardRepository.findByDeckId(deckId);
        Collections.shuffle(cards);
        return cards;
    }

    public Card updateCard(Long deckId, Long id, CardRequest card) {
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

    public Card getCardById(Long deckId, Long id) {
        checkIfDeckExists(deckId);
        return checkIfCardExists(id);
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
