package com.example.flashcardsapi.service;

import com.example.flashcardsapi.exception.FlashcardsApiException;
import com.example.flashcardsapi.model.Deck;
import com.example.flashcardsapi.model.User;
import com.example.flashcardsapi.payload.DeckDetailResponse;
import com.example.flashcardsapi.payload.DeckRequest;
import com.example.flashcardsapi.repository.CardRepository;
import com.example.flashcardsapi.repository.DeckRepository;
import com.example.flashcardsapi.repository.UserRepository;
import com.example.flashcardsapi.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class DeckService {

    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public List<Deck> getAll() {
        return deckRepository.findAll();
    }

    public Deck createDeck(DeckRequest deck) {
        Deck entityDeck = new Deck();
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null){
            User foundUser = userRepository.findById(principal.getId()).orElseThrow(() -> new FlashcardsApiException(HttpStatus.FORBIDDEN, "user not found with provided auth information"));
            entityDeck.setUser(foundUser);
        }
        entityDeck.setTitle(deck.getTitle());
        entityDeck.setDescription(deck.getDescription());
        return deckRepository.save(entityDeck);
    }

    public Deck updateDeck(DeckRequest deck, Long id){
        Deck foundDeck = checkIfDeckExists(id);
        if(!checkIfDeckIsOwnedByCurrentUser(foundDeck.getUser())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "cannot update deck created by another user");
        }
        foundDeck.setDescription(deck.getDescription());
        foundDeck.setTitle(deck.getTitle());
        return deckRepository.save(foundDeck);
    }

    public DeckDetailResponse getDeckById(Long id){
        Deck foundDeck = checkIfDeckExists(id);
        User foundUser = userRepository.findById(foundDeck.getUser().getId()).orElse(null);
        Long cardCount = cardRepository.countByDeckId(id);
        return DeckDetailResponse
                .builder()
                .id(foundDeck.getId())
                .title(foundDeck.getTitle())
                .description(foundDeck.getDescription())
                .cardCount(cardCount)
                .user(foundUser)
                .build();
    }

    public void deleteDeck(Long id){
        Deck foundDeck = checkIfDeckExists(id);
        if(!checkIfDeckIsOwnedByCurrentUser(foundDeck.getUser())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "cannot delete deck created by another user");
        }
        deckRepository.delete(foundDeck);
    }

    private Boolean checkIfDeckIsOwnedByCurrentUser(User deckOwner){
        if(deckOwner == null || deckOwner.getId() == null){
            return true;
        }
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long principalId = principal.getId();
        if(principalId == null) {
            return false;
        }
        return Objects.equals(deckOwner.getId(), principalId);
    }

    private Deck checkIfDeckExists(Long id) {
        return deckRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found with id: "  + id));
    }
}
