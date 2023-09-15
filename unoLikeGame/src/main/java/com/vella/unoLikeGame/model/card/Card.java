package com.vella.unoLikeGame.model.card;

import com.vella.unoLikeGame.model.room.Room;
import com.vella.unoLikeGame.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card")
public class Card {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  private CardValue cardValue;

  @Enumerated(EnumType.STRING)
  private CardColour cardColour;

  @Enumerated(EnumType.STRING)
  private CardEffect cardEffect;

  @Enumerated(EnumType.STRING)
  private CardLocation cardLocation;

  @ManyToOne
  @JoinColumn(name = "roomId")
  private Room inRoom;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;
}
