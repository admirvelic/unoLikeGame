package com.vella.unoLikeGame.model.room;


import com.vella.unoLikeGame.model.card.Card;
import com.vella.unoLikeGame.model.card.CardEffect;
import com.vella.unoLikeGame.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private GameStatus gameStatus;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User host;

    private List<User> usersInRoom;

    private User userOnTurn;

    private User lastPlayedBy;

    private List<Card> lastPlayed;

    private RoomStatus roomStatus;

    private CardEffect effectOnUser;

    private User lastWinner;
}
