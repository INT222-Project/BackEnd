package int222.project.backend.controllers;

import int222.project.backend.models.Reservation;
import int222.project.backend.models.RoomType;
import int222.project.backend.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public RoomType getRoomType(@PathVariable int roomTypeId) {
        return roomTypeRepository.findById(roomTypeId).orElse(null);
    }

    @GetMapping("")
    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addNewRoomType(@RequestPart("addRoomType") RoomType roomType) {
        List<RoomType> roomTypeList = roomTypeRepository.findAll();
        int latestRoomType = -1;
        for (int i = 0; i < roomTypeList.size(); i++) {
            if (i + 1 == roomTypeList.size() - 1) break;
            int id = roomTypeList.get(i).getRoomTypeId();
            int nextId = roomTypeList.get(i + 1).getRoomTypeId();
            if (!((id + 1) == nextId)) {
                latestRoomType = id;
                break;
            }
        }
        if (latestRoomType == -1) latestRoomType = roomTypeList.get(roomTypeList.size() - 1).getRoomTypeId();
        int id = latestRoomType + 1;
        roomType.setRoomTypeId(id);
        this.roomTypeRepository.save(roomType);
    }

    @PutMapping(path = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void editRoomType(@RequestPart("editRoomType") RoomType roomType) {
        this.roomTypeRepository.saveAndFlush(roomType);
    }

    @DeleteMapping(path = "/delete/{roomTypeId}")
    public void deleteRoomType(@PathVariable int roomTypeId) {
        RoomType roomType = this.roomTypeRepository.findById(roomTypeId).orElse(null);
        if (roomType != null) this.roomTypeRepository.delete(roomType);
        else return;
    }
}
