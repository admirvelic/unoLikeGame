package com.vella.unoLikeGame.service;

import com.jayway.jsonpath.internal.function.sequence.Last;
import com.vella.unoLikeGame.exception.CustomErrorException;
import com.vella.unoLikeGame.model.card.*;
import com.vella.unoLikeGame.model.room.Room;
import com.vella.unoLikeGame.model.user.User;
import com.vella.unoLikeGame.model.user.UserRepository;
import com.vella.unoLikeGame.repository.CardRepo;
import com.vella.unoLikeGame.repository.RoomRepo;
import com.vella.unoLikeGame.security.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GameService {

  private final RoomRepo roomRepo;
  private final CardRepo cardRepo;
  private final TokenRepository tokenRepo;
  private final UserRepository userRepo;

  public SeeCardsResponse seeCards(Long id, String token) {
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

      List<Card> lastPlayed = room.getLastPlayed();

      User lastPlayedBy = room.getLastPlayedBy();

      List<Card> cardsInHand = cardRepo.getAllByUser(user);

      User userOnTurn = roomRepo.getUserOnTurn(room);

      Boolean canPlay;

      canPlay = user.equals(userOnTurn);

      SeeCardsResponse response = new SeeCardsResponse();

      response.setLastPlayed(lastPlayed);
      response.setLastPlayedBy(lastPlayedBy);
      response.setCardsInHand(cardsInHand);
      response.setUserOnTurn(userOnTurn);
      response.setCanPlay(canPlay);

      return response;
    } catch (Exception e) {
      throw new CustomErrorException("Failed fetching cards {}", e);
    }
  }

  public List<Card> playCards(List<PlayCardRequest> request, Long id, String token) {
    try {

      Optional<Room> roomOptional = roomRepo.findById(id);
      Optional<User> userOptional = tokenRepo.findUserByToken(token);

      if (roomOptional.isEmpty()) {
        throw new IllegalArgumentException("Room not found");
      }

      Room room = roomOptional.get();

      if (userOptional.isEmpty()) {
        throw new IllegalArgumentException("User not found.");
      }

      User user = userOptional.get();

      if (request.isEmpty()) {
        throw new IllegalArgumentException("No cards selected");
      }
      if (user.equals(room.getUserOnTurn())) {
        throw new IOException("Not your turn, wait");
      }

      CardValue setCardValue;
      setCardValue = request.get(0).getCardValue();
      CardColour setCardColour;
      setCardColour = request.get(0).getCardColour();
      List<Card> response = null;

      for (PlayCardRequest cardRequest : request) {
        Card selectedCard = userHasCard(cardRequest, user);
        if (selectedCard == null) {
          throw new IllegalArgumentException("Can't play selected card, player has no card. {}");
        }

        if (!cardRequest.getCardValue().equals(setCardValue)) {
          throw new IllegalArgumentException("Can't play selected cards. Cards don't match.");
        }
      }

      if (!(setCardValue.equals(
          room.getLastPlayed().get(room.getLastPlayed().size() - 1).getCardValue()))) {
        if (room.getLastPlayed().get(room.getLastPlayed().size() - 1).getCardValue() != null) {
          if (!(setCardColour.equals(
              room.getLastPlayed().get(room.getLastPlayed().size() - 1).getCardColour()))) {
            throw new IllegalArgumentException("First selected card can't be played!");
          }
        }
      }

      CardColour changeColour = null;
      boolean changingColourBool = setCardValue.equals(CardValue.DRAW4) || setCardValue.equals(CardValue.CHANGE_COLOUR);
      if (changingColourBool) {
        for (PlayCardRequest cardRequest : request) {
          if (cardRequest.getChangeColour() != null) {
            changeColour = cardRequest.getChangeColour();
          }
        }
      }

      if (changingColourBool) {
        if (changeColour == null) {
          throw new IllegalArgumentException("No colour selected for colour change");
        }
      }

      for (PlayCardRequest cardRequest : request) {

        Card selectedCard = userHasCard(cardRequest, user);

        selectedCard.setCardLocation(CardLocation.PLAYED);
        selectedCard.setUser(null);
        cardRepo.save(selectedCard);
        response.add(selectedCard);
      }

      room.setLastPlayed(response);
      room.setLastPlayedBy(user);

      room.setUserOnTurn(room.getUsersInRoom().get(room.getUsersInRoom().indexOf(user) + 1));
      roomRepo.save(room);
      if (setCardValue.equals(CardValue.CHANGE_DIRECTION)) {

        if ((request.size() % 2) == 0) {
          List<User> usersInRoom;
          usersInRoom = room.getUsersInRoom();
          Collections.reverse(usersInRoom);

          room.setUsersInRoom(usersInRoom);
          roomRepo.save(room);
        }

      } else if (setCardValue == CardValue.DRAW2) {

        User nextUser = room.getUsersInRoom().get(room.getUsersInRoom().indexOf(user) + 1);

        List<Optional<Card>> cards = cardRepo.getAllByRoom(room);

        int noOfNextUsersCards = cardRepo.getAllByUser(nextUser).size();

        int amountToDraw = request.size() * 2;
        int correctNoOdCards = noOfNextUsersCards + amountToDraw;

        while (noOfNextUsersCards < correctNoOdCards) {
          Random random = new Random();
          Optional<Card> cardOptional = cards.get(random.nextInt(cards.size()));
          if (cardOptional.isEmpty()) {
            throw new CustomErrorException("Filed fetching cards");
          }
          Card card = cardOptional.get();

          if (card.getUser() == null) {
            card.setUser(nextUser);
            card.setCardLocation(CardLocation.IN_HAND);
            cardRepo.save(card);
            log.info("Card was assigned to next user{}", card);
            noOfNextUsersCards++;
          }
        }

      } else if (setCardValue.equals(CardValue.DRAW4)) {

        User nextUser = room.getUsersInRoom().get(room.getUsersInRoom().indexOf(user) + 1);

        List<Optional<Card>> cards = cardRepo.getAllByRoom(room);

        int noOfNextUsersCards = cardRepo.getAllByUser(nextUser).size();

        int amountToDraw = request.size() * 4;
        int correctNoOdCards = noOfNextUsersCards + amountToDraw;

        while (noOfNextUsersCards < correctNoOdCards) {
          Random random = new Random();
          Optional<Card> cardOptional = cards.get(random.nextInt(cards.size()));
          if (cardOptional.isEmpty()) {
            throw new CustomErrorException("Filed fetching cards");
          }
          Card card = cardOptional.get();

          if (card.getUser() == null) {
            card.setUser(nextUser);
            card.setCardLocation(CardLocation.IN_HAND);
            cardRepo.save(card);
            log.info("Card was assigned to next user{}", card);
            noOfNextUsersCards++;
          }
        }

        Card cardSetColour = null;
        cardSetColour.setCardValue(null);
        cardSetColour.setCardColour(changeColour);
        cardSetColour.setCardEffect(null);
        cardSetColour.setCardLocation(null);
        cardSetColour.setInRoom(room);
        cardSetColour.setUser(user);
        List<Card> cardSetColourList = null;
        cardSetColourList.add(cardSetColour);
        room.setLastPlayed(cardSetColourList);
        roomRepo.save(room);
      } else if (setCardValue.equals(CardValue.SKIP)) {

        room.setUserOnTurn(room.getUsersInRoom().get(room.getUsersInRoom().indexOf(user) + 2));
        roomRepo.save(room);
      } else if (setCardValue.equals(CardValue.CHANGE_COLOUR)) {

        Card cardSetColour = null;
        cardSetColour.setCardValue(null);
        cardSetColour.setCardColour(changeColour);
        cardSetColour.setCardEffect(null);
        cardSetColour.setCardLocation(null);
        cardSetColour.setInRoom(room);
        cardSetColour.setUser(user);
        List<Card> cardSetColourList = null;
        cardSetColourList.add(cardSetColour);
        room.setLastPlayed(cardSetColourList);
        roomRepo.save(room);
      }

      return response;

    } catch (Exception e) {
      throw new CustomErrorException("Failed playing cards cards {}", e);
    }
  }

  public Card drawCard(Long id, String token) {

    Optional<Room> roomOptional = roomRepo.findById(id);
    Optional<User> userOptional = tokenRepo.findUserByToken(token);

    if (roomOptional.isEmpty()) {
      throw new IllegalArgumentException("Room not found");
    }

    Room room = roomOptional.get();

    if (userOptional.isEmpty()) {
      throw new IllegalArgumentException("User not found.");
    }

    User user = userOptional.get();

    List<Optional<Card>> cards = cardRepo.getAllByRoom(room);

    Random random = new Random();
    Optional<Card> cardOptional = cards.get(random.nextInt(cards.size()));

    if (cardOptional.isEmpty()) {
      throw new CustomErrorException("Filed fetching cards");
    }
    Card card = cardOptional.get();

    if (card.getUser() == null) {
      card.setUser(user);
      card.setCardLocation(CardLocation.IN_HAND);
      cardRepo.save(card);
      log.info("Card was assigned to user{}", card);
    }

    return card;
  }

  private Card userHasCard(PlayCardRequest cardRequest, User user) {
    List<Card> userCards;
    userCards = cardRepo.getAllByUser(user);
    boolean hasCard = false;
    boolean allCardsChecked = false;
    while (!allCardsChecked) {
      for (Card card : userCards) {
        if (!(card.getCardValue() == cardRequest.getCardValue())
            || !(card.getCardColour() == cardRequest.getCardColour())) {
          return card;
        }
        allCardsChecked = true;
      }
    }
    return null;
  }
}
