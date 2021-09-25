package int222.project.backend.controllers;

import int222.project.backend.models.Room;
import int222.project.backend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"}, allowedHeaders = "*")
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

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addRoom(@RequestPart("newRoom") Room room){
        int latestRoomId = 0;
        List<Room> getAllRoom = roomRepository.findAll();
        for(int i = 0 ; i < getAllRoom.size() ; i++){
            if(i+1 == getAllRoom.size() - 1) break;
            int id = getAllRoom.get(i).getRoomId();
            int nextId = getAllRoom.get(i+1).getRoomId();
            if((id+1) != nextId) {
                latestRoomId = id;
                break;
            }
        }
        if(latestRoomId == 0) latestRoomId = getAllRoom.get(getAllRoom.size()-1).getRoomId();
        room.setRoomId(latestRoomId+1);
        System.out.println(room.toString());
        this.roomRepository.save(room);
    }

    @PutMapping(path = "/edit/{roomId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void editRoom(@PathVariable int roomId , @RequestPart("editRoom") Room room){
        Room temp = roomRepository.findById(roomId).orElse(null);
        if(temp != null){
            temp = room;
            roomRepository.save(temp);
        }
    }

    @DeleteMapping(path = "/delete/{roomId}")
    public void deleteRoom(@PathVariable int roomId){
        Room temp = roomRepository.findById(roomId).orElse(null);
        if(temp != null){
            roomRepository.delete(temp);
        }
    }

}
