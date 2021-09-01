package com.example.flashcardsapi.controller;

import com.example.flashcardsapi.model.Card;
import com.example.flashcardsapi.payload.CardRequest;
import com.example.flashcardsapi.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/v1/decks/{deckId}/cards")
@RestController
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<Card> getAllCards(@PathVariable("deckId") Long deckId, @RequestParam(value="random", required=false, defaultValue = "false") Boolean random){
        if(random){
            return cardService.getRandomCardsByDeckId(deckId);
        }else{
            return cardService.getCardsByDeckId(deckId);
        }
    }

    @GetMapping("/{id}")
    public Card getById(@PathVariable("deckId") Long deckId, @PathVariable("id") Long id){
        return cardService.getCardById(deckId, id);
    }

    @PostMapping
    public List<Card> createCard(@PathVariable("deckId") Long deckId, @RequestBody @Validated @NotEmpty List<@Valid CardRequest> cards){
        return cardService.addCards(deckId, cards);
    }

    @PutMapping("/{id}")
    public Card updateCard(@PathVariable("deckId") Long deckId, @PathVariable("id") Long id, @RequestBody @Valid CardRequest card){
        return cardService.updateCard(deckId, id, card);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable("deckId") Long deckId, @PathVariable("id") Long id){
        cardService.deleteCard(deckId, id);
    }
}
