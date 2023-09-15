package com.vella.unoLikeGame.controller;


import com.vella.unoLikeGame.exception.CustomErrorException;
import com.vella.unoLikeGame.model.card.Card;
import com.vella.unoLikeGame.model.card.PlayCardRequest;
import com.vella.unoLikeGame.model.card.SeeCardsResponse;
import com.vella.unoLikeGame.model.user.SeeUsersResponse;
import com.vella.unoLikeGame.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/uno-like/v1/play")
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameService gameService;

    @GetMapping("/{id}/seeCards")
    public SeeCardsResponse seeCards (@PathVariable Long id, @RequestHeader(name="Authorization") String token) throws CustomErrorException, IOException{
       return gameService.seeCards(id, token);
    }

    @PostMapping("/{id}/throw")
    public List<Card> playCards  (@RequestBody List<PlayCardRequest> requests, @PathVariable Long id, @RequestHeader(name="Authorization") String token) throws CustomErrorException, IOException{
        return gameService.playCards(requests,id,token);
    }

    @GetMapping("/{id}/draw")
    public Card drawCard(@PathVariable Long id, @RequestHeader(name="Authorization") String token) throws CustomErrorException, IOException{
        return gameService.drawCard(id, token);
    }

    @PostMapping("/{id}/challenge")
    public String CallNoUno(@RequestBody Long calledUserId, @PathVariable Long id, @RequestHeader(name="Authorization") String token) throws CustomErrorException, IOException{
        return gameService.CallNoUno(id,token,calledUserId);
    }

    @GetMapping("/{id}/players")
    public List<SeeUsersResponse> seeUsers (@PathVariable Long id, @RequestHeader(name="Authorization") String token) throws CustomErrorException, IOException{
        return gameService.seeUsers(id, token);
    }
}
