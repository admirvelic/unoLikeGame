package com.vella.unoLikeGame.security.token;

import java.util.List;
import java.util.Optional;

import com.vella.unoLikeGame.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = """
      SELECT t FROM Token t INNER JOIN User u\s
      ON t.user.id = u.id\s
      WHERE u.id = :id AND (t.expired = FALSE OR t.revoked = FALSE)\s
      """)
  List<Token> findAllValidTokenByUser(Integer id);

  Optional<Token> findByToken(String token);

  Optional<User> findUserByToken(String token);
}
