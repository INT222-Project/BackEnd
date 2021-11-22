package int222.project.backend.controllers;

import int222.project.backend.models.Customer;
import int222.project.backend.models.Receptionist;
import int222.project.backend.repositories.ReceptionistRepository;
import int222.project.backend.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/receptionists")
public class ReceptionistController {
    @Autowired
    ReceptionistRepository receptionistRepository;
    @Autowired
    UploadService uploadService;
    // Customer
    @PostMapping("/uploadImage/{repId}")
    public void uploadImage(@RequestParam("image-file") MultipartFile imageFile,@PathVariable String repId){
        uploadService.saveImage(imageFile,repId, Receptionist.class);
    }
    @GetMapping(path = "/showImage/{repId}")
    public ResponseEntity<byte[]> showImage(@PathVariable String repId){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(uploadService.get(repId,Receptionist.class));
    }
    @DeleteMapping(path="/deleteImage/{repId}")
    public ResponseEntity<?> deleteImage(@PathVariable String repId){
        uploadService.deleteImage(repId, Receptionist.class);
        return ResponseEntity.ok().body("Successful delete image");
    }
    // Receptionist
    @GetMapping("/{repId}")
    public Receptionist getReceptionist (@PathVariable String repId){ return receptionistRepository.findById(repId).orElse(null); }

    @GetMapping("")
    public List<Receptionist> getAllReceptionists(){ return receptionistRepository.findAll(); }

    @PutMapping(path = "/edit/{repId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void editReceptionist(@RequestParam(value = "image-file",required = false) MultipartFile imageFile,@RequestPart("editReceptionist") Receptionist receptionist, @PathVariable String repId){
        Receptionist temp = this.receptionistRepository.findById(repId).orElse(null);
        if(temp != null){
            temp = receptionist;
        }
        if(imageFile != null){
            uploadImage(imageFile,repId);
        }
        this.receptionistRepository.saveAndFlush(temp);
    }
}
