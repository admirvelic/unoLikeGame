package com.vella.unoLikeGame.service;

import com.vella.unoLikeGame.exception.CustomErrorException;
import com.vella.unoLikeGame.model.card.*;
import com.vella.unoLikeGame.model.room.CreateRoomRequest;
import com.vella.unoLikeGame.model.room.GameStatus;
import com.vella.unoLikeGame.model.room.Room;
import com.vella.unoLikeGame.model.room.RoomStatus;
import com.vella.unoLikeGame.repository.CardRepo;
import com.vella.unoLikeGame.repository.RoomRepo;
import com.vella.unoLikeGame.security.token.TokenRepository;
import com.vella.unoLikeGame.model.user.UserRepository;
import com.vella.unoLikeGame.model.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.vella.unoLikeGame.model.room.RoomStatus.PRIVATE;
import static com.vella.unoLikeGame.model.room.RoomStatus.PUBLIC;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoomService {

  private final RoomRepo roomRepo;
  private final CardRepo cardRepo;
  private final TokenRepository tokenRepo;
  private final UserRepository userRepo;

  public Room createRoom(String token) {
    try {
      if (tokenRepo.findUserByToken(token).isEmpty()) {
        throw new IllegalArgumentException("Failed creating a room. User not found.");
      }
      User user = tokenRepo.findUserByToken(token).get();

        Room createdRoom = new Room();
        createdRoom.setHost(user);
        createdRoom.setGameStatus(GameStatus.WAITING);
        log.info("Created new room");
        return roomRepo.save(createdRoom);

    } catch (Exception e) {
      log.error("Error creating new blog {}", e.getMessage());
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  public Room startGame(Long id, String token) {
    try {
      Optional<Room> roomOptional = roomRepo.findById(id);
      Optional<User> userOptional = tokenRepo.findUserByToken(token);

      if (roomOptional.isEmpty()) {
        throw new IllegalArgumentException("Failed starting a game. Room not found");
      }

      Room room = roomOptional.get();

      if (userOptional.isEmpty()) {
        throw new IllegalArgumentException("Failed starting a game. User not found.");
      }

      User user = userOptional.get();

      if (!user.equals(room.getHost())) {
        throw new IllegalArgumentException("Failed starting a game. User not admin.");
      }
      room.setGameStatus(GameStatus.RUNNING);

      for (CardValue value : CardValue.values()) {
        for (CardColour colour : CardColour.values()) {
          Card card = new Card();
          card.setCardColour(colour);
          card.setCardValue(value);
          card.setCardLocation(CardLocation.IN_DECK);
          card.setUser(null);
          card.setInRoom(room);

          if (value == CardValue.CHANGE_COLOUR) {
            card.setCardEffect(CardEffect.CHANGE_COLOUR);
            card.setCardColour(null);
            cardRepo.save(card);
          } else if (value == CardValue.ZERO) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          } else if (value == CardValue.ONE) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          } else if (value == CardValue.TWO) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          } else if (value == CardValue.THREE) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          } else if (value == CardValue.FOUR) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.FIVE) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.SIX) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.SEVEN) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.EIGHT) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.NINE) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.SKIP) {
            card.setCardEffect(CardEffect.SKIP);
            cardRepo.save(card);
          }else if (value == CardValue.CHANGE_DIRECTION) {
            card.setCardEffect(CardEffect.CHANGE_DIRECTION);
            cardRepo.save(card);
          }else if (value == CardValue.DRAW2) {
            card.setCardEffect(CardEffect.DRAW2);
            cardRepo.save(card);
          }
          log.info("Created card {}", card);
          cardRepo.save(card);
        }
      }

      for (CardValue value : CardValue.values()) {
        for (CardColour colour : CardColour.values()) {
          Card card = new Card();
          card.setCardColour(colour);
          card.setCardValue(value);
          card.setCardLocation(CardLocation.IN_DECK);
          card.setUser(null);
          card.setInRoom(room);

          if (value == CardValue.DRAW4) {
            card.setCardEffect(CardEffect.DRAW4);
            card.setCardColour(null);
            cardRepo.save(card);
          } else if (value == CardValue.ONE) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          } else if (value == CardValue.TWO) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          } else if (value == CardValue.THREE) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          } else if (value == CardValue.FOUR) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.FIVE) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.SIX) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.SEVEN) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.EIGHT) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.NINE) {
            card.setCardEffect(CardEffect.NONE);
            cardRepo.save(card);
          }else if (value == CardValue.SKIP) {
            card.setCardEffect(CardEffect.SKIP);
            cardRepo.save(card);
          }else if (value == CardValue.CHANGE_DIRECTION) {
            card.setCardEffect(CardEffect.CHANGE_DIRECTION);
            cardRepo.save(card);
          }else if (value == CardValue.DRAW2) {
            card.setCardEffect(CardEffect.DRAW2);
            cardRepo.save(card);
          }
          log.info("Created card {}", card);
          cardRepo.save(card);
        }
      }

      List<Optional<User>> usersInRoom = userRepo.getUsersByRoom(room);
      List<Optional<Card>> cards = cardRepo.getAllByRoom(room);
      for (Optional<User> u : usersInRoom) {
        while (cardRepo.getAllByUser(u.get()).size() < 7) {
          Random random = new Random();
          Optional<Card> cardOptional = cards.get(random.nextInt(cards.size()));
          if (cardOptional.isEmpty()) {
            throw new CustomErrorException("Filed fetching cards");
          }
          Card card = cardOptional.get();

          if (card.getUser() == null) {
            card.setUser(u.get());
            card.setCardLocation(CardLocation.IN_HAND);
            cardRepo.save(card);
            log.info("Card was assigned to user{}", card);
          }
        }
      }
      User userOnTurn = room.getUsersInRoom().get(0);
      room.setUserOnTurn(user);
      log.info("User on turn {}", userOnTurn);
      return roomRepo.save(room);

    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  public Room stopGame(Long id, String token) {
    try {

      Optional<Room> roomOptional = roomRepo.findById(id);
      Optional<User> userOptional = tokenRepo.findUserByToken(token);

      if (roomOptional.isEmpty()) {
        throw new IllegalArgumentException("Failed stopping a game. Room not found");
      }

      Room room = roomOptional.get();

      if (userOptional.isEmpty()) {
        throw new IllegalArgumentException("Failed stopping a game. User not found.");
      }

      User user = userOptional.get();

      if (!user.equals(room.getHost())) {
        throw new IllegalArgumentException("Failed stopping a game. User not admin.");
      }

      room.setGameStatus(GameStatus.WAITING);

      cardRepo.deleteAllByRoom(room);

      log.info("Game stopped {}", room);
      return roomRepo.save(room);

    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  public void deleteRoom(Long id, String token) {
    try {

      Optional<Room> roomOptional = roomRepo.findById(id);
      Optional<User> userOptional = tokenRepo.findUserByToken(token);

      if (roomOptional.isEmpty()) {
        throw new IllegalArgumentException("Failed deleting a game. Room not found");
      }

      Room room = roomOptional.get();

      if (userOptional.isEmpty()) {
        throw new IllegalArgumentException("Failed deleting a game. User not found.");
      }

      User user = userOptional.get();

      if (!user.equals(room.getHost())) {
        throw new IllegalArgumentException("Failed deleting a game. User not admin.");
      }
      roomRepo.delete(stopGame(id, token));

    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  public List<Room> availableRooms() {

    List<Room> availableRooms = roomRepo.getAllByRoomStatus(PUBLIC);

    availableRooms.removeIf(room -> room.getUsersInRoom().size() > 9);

    return availableRooms;
  }

  public Room joinRoom(Long id, String token) {
    try {

      Optional<Room> roomOptional = roomRepo.findById(id);
      Optional<User> userOptional = tokenRepo.findUserByToken(token);

      if (roomOptional.isEmpty()) {
        throw new IllegalArgumentException("Failed joining a game. Room not found");
      }

      Room room = roomOptional.get();

      if (userOptional.isEmpty()) {
        throw new IllegalArgumentException("Failed joining a game. User not found.");
      }

      User user = userOptional.get();

      if (room.getGameStatus().equals(GameStatus.RUNNING)) {
        throw new IllegalArgumentException("Can't join game while it's running");
      }

      if (room.getUsersInRoom().size() > 9) {
        throw new IllegalArgumentException("Can't join game, player limit reached");
      }

      user.setRoom(room);

      List<User> newListOfUsers = room.getUsersInRoom();
      newListOfUsers.add(user);
      room.setUsersInRoom(newListOfUsers);
      log.info("Successfully joined room{}", room);
      return room;
    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  public Room leaveRoom(String token) {
    try {

      Optional<User> userOptional = tokenRepo.findUserByToken(token);
      if (userOptional.isEmpty()) {
        throw new IllegalArgumentException("Failed starting a game. User not found.");
      }

      User user = userOptional.get();

      Optional<Room> roomOptional = Optional.ofNullable(user.getRoom());
      if (roomOptional.isEmpty()) {
        throw new IllegalArgumentException("Failed starting a game. Room not found");
      }

      Room room = roomOptional.get();

      user.setRoom(null);
      userRepo.save(user);

      List<User> newListOfUsers = room.getUsersInRoom();
      newListOfUsers.remove(user);
      room.setUsersInRoom(newListOfUsers);
      roomRepo.save(room);
      log.info("Successfully left room{}", room);

      if (room.getGameStatus().equals(GameStatus.RUNNING)) {
        List<Card> usersCards = cardRepo.getAllByUser(user);

        if (!usersCards.isEmpty()) {
          for (Card card : usersCards) {
            card.setUser(null);
            card.setCardLocation(CardLocation.IN_DECK);
          }
        }
      }

      return roomRepo.save(room);

    } catch (Exception e) {
      throw new CustomErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }
}
