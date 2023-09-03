package com.vella.unoLikeGame.model.user;

import java.util.List;
import java.util.Optional;

import com.vella.unoLikeGame.model.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  @Query(value = """
  SELECT u FROM User u INNER JOIN Token t\s
  on u.id = t.user.id\s
  WHERE t.token = :token
  """)
  Optional<User> findUserByToken(String token);

  List<Optional<User>> getUsersByRoom(Room room);
}
