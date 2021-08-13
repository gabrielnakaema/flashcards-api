package com.example.flashcardsapi.repository;

import com.example.flashcardsapi.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByDeckId(Long deckId);

    Long countByDeckId(Long deckId);
}
