package com.vella.unoLikeGame.model.card;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;

@Builder
@AllArgsConstructor
@Entity
public class PlayCardRequest {

  @NonNull private CardValue cardValue;

  @NonNull private CardColour cardColour;

  private CardColour changeColour;

  public CardValue getCardValue() {
    return cardValue;
  }

  public CardColour getCardColour() {
    return cardColour;
  }

  public CardColour getChangeColour() {
    return changeColour;
  }
}
