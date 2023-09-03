package com.vella.unoLikeGame.repository;

import com.vella.unoLikeGame.model.card.Card;
import com.vella.unoLikeGame.model.card.CardLocation;
import com.vella.unoLikeGame.model.room.Room;
import com.vella.unoLikeGame.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepo extends JpaRepository<Card, Long> {

    @Override
    Optional<Card> findById(Long id);

    List<Optional<Card>> getAllByCardLocation(CardLocation cardLocation);

    List<Card> getAllByUser(User user);

    List<Optional<Card>> getAllByRoom(Room room);

    void deleteAllByUser(User user);

    void deleteAllByRoom(Room room);
}
