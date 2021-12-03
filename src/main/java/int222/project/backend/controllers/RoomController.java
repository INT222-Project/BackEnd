package int222.project.backend.controllers;

import int222.project.backend.exceptions.Error;
import int222.project.backend.exceptions.ImageHandlerException;
import int222.project.backend.models.Room;
import int222.project.backend.repositories.RoomRepository;
import int222.project.backend.services.RemainingRoomObject;
import int222.project.backend.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${jdbc.driver.name}")
    String driverName;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Autowired
    RoomRepository roomRepository;

    @GetMapping("/roomRequirement/{roomTypeId}")
    public List<Room> getRoomRequireRoomType(@PathVariable int roomTypeId) {
        return roomRepository.findAllRoomType(roomTypeId);
    }

    @GetMapping("/getRemainingRoom/{checkIn}/{checkOut}")
    public List<RemainingRoomObject> getRemainingRoom(@PathVariable String checkIn, @PathVariable String checkOut) {
        List<RemainingRoomObject> remainingRoomObjects = new ArrayList<>();
        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            String query
                    = "select t1.roomtypeid, t1.bedtype,t2.amount_room - t1.reserved_room as remaining_room from (select r.roomtypeid , r.bedtype , count(*) as reserved_room from reservationdetail rd join room r on rd.roomid = r.roomid where rd.status != 'undone' and rd.status != 'check-out' and rd.checkindate >= '" + checkIn + "' and rd.checkoutdate <= '" + checkOut + "' group by r.roomtypeid, r.bedtype) t1 join (select r.roomtypeid , r.bedtype , count(*) as amount_room from room r where r.status != 'mock-up' group by r.roomtypeid, r.bedtype) t2 on t1.roomtypeid = t2.roomtypeid where t1.bedtype = t2.bedtype order by roomtypeid asc;";
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int roomtypeid = rs.getInt("roomtypeid");
                String bedtype = rs.getString("bedtype");
                int count = rs.getInt("remaining_room");
                System.out.printf("%s, %s, %s \n", roomtypeid, bedtype, count);
                RemainingRoomObject temp = new RemainingRoomObject(roomtypeid, bedtype, count);
                remainingRoomObjects.add(temp);
            }
            statement.close();
        } catch (Exception e) {
            System.err.println("Got an exception !");
            System.err.println(e.getMessage());
        }
        return remainingRoomObjects;
    }

    @GetMapping("/{roomId}")
    public Room getRoom(@PathVariable int roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    @GetMapping("")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms() {
        return roomRepository.findAvailableRooms();
    }

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addRoom(@RequestPart("newRoom") Room room) {
        int latestRoomId = 0;
        List<Room> getAllRoom = roomRepository.findAll();
        for (int i = 0; i < getAllRoom.size(); i++) {
            if (i + 1 == getAllRoom.size() - 1) break;
            int id = getAllRoom.get(i).getRoomId();
            int nextId = getAllRoom.get(i + 1).getRoomId();
            if ((id + 1) != nextId) {
                latestRoomId = id;
                break;
            }
        }
        if (latestRoomId == 0) latestRoomId = getAllRoom.get(getAllRoom.size() - 1).getRoomId();
        room.setRoomId(latestRoomId + 1);
        System.out.println(room.toString());
        return ResponseEntity.ok(this.roomRepository.save(room));
    }

    @PutMapping(path = "/edit/{roomId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editRoom(@PathVariable int roomId, @RequestPart("editRoom") Room room, @RequestParam(value = "image-file", required = false) MultipartFile file) {
        Room temp = roomRepository.findById(roomId).orElse(null);
        if (temp != null) {
            temp = room;
            return ResponseEntity.ok(roomRepository.saveAndFlush(temp));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error("Cannot edit room." + roomId,400));
    }

    @DeleteMapping(path = "/delete/{roomId}")
    public void deleteRoom(@PathVariable int roomId) {
        Room temp = roomRepository.findById(roomId).orElse(null);
        if (temp != null) {
            roomRepository.delete(temp);
        }
    }

}
