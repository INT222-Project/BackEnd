package int222.project.backend.controllers;

import int222.project.backend.models.Room;
import int222.project.backend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;
    // Room
    @GetMapping("/{roomId}")
    public Room getRoom (@PathVariable int roomId){ return roomRepository.findById(roomId).orElse(null); }

    @GetMapping("")
    public List<Room> getAllRooms(){ return roomRepository.findAll(); }

    @PostMapping(path = "/add")
    public void addRoom(@RequestBody Room room){
        System.out.println(room.toString());
        this.roomRepository.save(room);
    }

}
