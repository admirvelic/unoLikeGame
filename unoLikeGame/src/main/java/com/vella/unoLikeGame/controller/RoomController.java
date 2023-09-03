package com.vella.unoLikeGame.controller;


import com.vella.unoLikeGame.exception.CustomErrorException;
import com.vella.unoLikeGame.model.room.CreateRoomRequest;
import com.vella.unoLikeGame.model.room.Room;
import com.vella.unoLikeGame.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/uno-like/v1/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create-room")
    public Room createRoom( @RequestHeader(name="Authorization") String token, @RequestBody CreateRoomRequest createRoomRequest) throws CustomErrorException, IOException{
            return roomService.createRoom(token, createRoomRequest);

    }

    @PostMapping("/start-game/{id}")
    public Room startGame(@PathVariable Long id, @RequestHeader(name="Authorization") String token) throws CustomErrorException, IOException{
            return roomService.startGame(id, token);

    }

    @PostMapping("/stop-game/{id}")
    public Room stopGame (@PathVariable Long id, @RequestHeader(name="Authorization") String token) throws CustomErrorException, IOException{
        return roomService.stopGame(id, token);
    }

    @PostMapping("/delete-room/{id}")
    public void deleteRoom (@PathVariable Long id, @RequestHeader(name="Authorization") String token) throws CustomErrorException, IOException{
        roomService.deleteRoom(id, token);
    }

    @GetMapping("/rooms")
    public List<Room> seeAvailableRooms() throws CustomErrorException {
        return roomService.availableRooms();
    }

    @PostMapping("/join/{id}")
    public Room joinRoom (@PathVariable Long id, @RequestHeader(name="Authorization") String token) throws CustomErrorException, IOException {
        return roomService.joinRoom(id, token);
    }

    @PostMapping("/leave")
    public Room leaveRoom (@RequestHeader(name="Authorization") String token) throws CustomErrorException{
        return roomService.leaveRoom(token);
    }
}

