package int222.project.backend.controllers;

import int222.project.backend.models.RoomType;
import int222.project.backend.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/roomTypes")
public class RoomTypeController {

    @Autowired
    RoomTypeRepository roomTypeRepository;
    // Room Type
    @GetMapping("/{roomTypeId}")
    public RoomType getRoomType (@PathVariable int roomTypeId){ return roomTypeRepository.findById(roomTypeId).orElse(null); }

    @GetMapping("")
    public List<RoomType> getAllRoomTypes(){ return roomTypeRepository.findAll(); }
}
