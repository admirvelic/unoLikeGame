package com.vella.unoLikeGame.repository;

import com.vella.unoLikeGame.model.room.GameStatus;
import com.vella.unoLikeGame.model.room.Room;
import com.vella.unoLikeGame.model.room.RoomStatus;
import com.vella.unoLikeGame.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepo extends JpaRepository<Room, Long> {

    @Override
    Optional<Room> findById(Long id);

    List<Room> getAllByRoomStatus(RoomStatus roomStatus);

    User getUserOnTurn(Room room);
}
