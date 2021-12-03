package int222.project.backend.controllers;

import int222.project.backend.exceptions.Error;
import int222.project.backend.exceptions.ImageHandlerException;
import int222.project.backend.models.Room;
import int222.project.backend.models.RoomType;
import int222.project.backend.repositories.RoomTypeRepository;
import int222.project.backend.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/roomTypes")
public class RoomTypeController {

    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Autowired
    UploadService uploadService;

    @PostMapping("/uploadImage/{roomtypeid}")
    public void uploadImage(@RequestParam("image-file") MultipartFile imageFile, @PathVariable int roomtypeid) throws IOException {
        uploadService.saveImage(imageFile, Integer.toString(roomtypeid), Room.class);
    }

    @GetMapping(path = "/showImage/{roomtypeid}")
    public ResponseEntity<?> showImage(@PathVariable int roomtypeid) {
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(uploadService.get(Integer.toString(roomtypeid), Room.class));
        } catch (ImageHandlerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(e.getMessage() + " and error code : " + e.getErrorCode(), e.getErrorCode().hashCode()));
        }
    }

    @DeleteMapping(path = "/deleteImage/{roomtypeid}")
    public ResponseEntity<?> deleteImage(@PathVariable int roomtypeid) {
        uploadService.deleteImage(Integer.toString(roomtypeid), Room.class);
        return ResponseEntity.ok().body("Successful delete image");
    }

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
    public ResponseEntity<?> addNewRoomType(@RequestPart("addRoomType") RoomType roomType, @RequestParam("image-file") MultipartFile file) {
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
        try {
            uploadImage(file, id);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error("Sorry, We could not save your image file.", 500));
        }
        roomType.setRoomTypeId(id);
        return ResponseEntity.ok(this.roomTypeRepository.save(roomType));
    }

    @PutMapping(path = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editRoomType(@RequestPart("editRoomType") RoomType roomType, @RequestParam(value = "image-file", required = false) MultipartFile file) {
        RoomType temp = this.roomTypeRepository.findById(roomType.getRoomTypeId()).orElse(null);
        if (temp != null) {
            temp = roomType;
            if (file != null) {
                try {
                    deleteImage(temp.getRoomTypeId());
                    uploadImage(file, temp.getRoomTypeId());
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error("Sorry, We could not save your image file.", 500));
                }
            }
        }
        return ResponseEntity.ok(this.roomTypeRepository.saveAndFlush(roomType));
    }

    @DeleteMapping(path = "/delete/{roomTypeId}")
    public void deleteRoomType(@PathVariable int roomTypeId) {
        RoomType roomType = this.roomTypeRepository.findById(roomTypeId).orElse(null);
        if (roomType != null) {
            deleteImage(roomTypeId);
            this.roomTypeRepository.delete(roomType);
        }
    }
}
