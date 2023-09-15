package com.vella.unoLikeGame.model.card;

import com.vella.unoLikeGame.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeeCardsResponse {

  private List<Card> lastPlayed;

  private User lastPlayedBy;

  private List<Card> CardsInHand;

  private User userOnTurn;

  private Boolean canPlay;

  public SeeCardsResponse setLastPlayed(List<Card> lastPlayed) {
    this.lastPlayed = lastPlayed;
    return this;
  }

  public SeeCardsResponse setLastPlayedBy(User lastPlayedBy) {
    this.lastPlayedBy = lastPlayedBy;
    return this;
  }

  public SeeCardsResponse setCardsInHand(List<Card> cardsInHand) {
    CardsInHand = cardsInHand;
    return this;
  }

  public SeeCardsResponse setUserOnTurn(User userOnTurn) {
    this.userOnTurn = userOnTurn;
    return this;
  }

  public SeeCardsResponse setCanPlay(Boolean canPlay) {
    this.canPlay = canPlay;
    return this;
  }
}
