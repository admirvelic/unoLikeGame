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
    @GeneratedValue
    private Integer id  ;

    @Enumerated
    private CardValue cardValue;

    @Enumerated
    private CardColour  cardColour;

    @Enumerated
    private CardEffect cardEffect;

    @Enumerated
    private CardLocation cardLocation;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room inRoom;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
