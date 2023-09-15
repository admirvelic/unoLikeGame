package com.vella.unoLikeGame.model.card;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;

@Builder
@AllArgsConstructor
public class PlayCardRequest {

  @NonNull private CardValue cardValue;

  @NonNull private CardColour cardColour;

  private CardColour changeColour;

  private Boolean callUno;

  public Boolean getCallUno() {
    return callUno;
  }

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
