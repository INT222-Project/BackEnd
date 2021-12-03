package int222.project.backend.controllers;

import int222.project.backend.models.Package;
import int222.project.backend.models.Reservation;
import int222.project.backend.models.ReservationDetail;
import int222.project.backend.models.RoomType;
import int222.project.backend.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/packages")
public class PackageController {
    @Autowired
    PackageRepository packageRepository;

    // Package
    @GetMapping("/{packageId}")
    public Package getPackage(@PathVariable String packageId) {
        return packageRepository.findById(packageId).orElse(null);
    }

    @GetMapping("")
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addNewPackage(@RequestPart("addPackage") Package newPackage) {
        List<Package> packageList = packageRepository.findAll();
        String latestId = null;
        for (int i = 0; i < packageList.size(); i++) {
            if (i + 1 == packageList.size() - 1) break;
            String id = packageList.get(i).getPackageId().substring(1);
            String nextId = packageList.get(i + 1).getPackageId().substring(1);
            if (Integer.parseInt(id) + 1 != Integer.parseInt(nextId)) {
                latestId = id;
            }
        }
        if (latestId == null) latestId = packageList.get(packageList.size() - 1).getPackageId().substring(1);
        int tempId = Integer.parseInt(latestId) + 1;
        if (tempId < 10) latestId = "p000" + tempId;
        else if (tempId < 100) latestId = "p00" + tempId;
        else if (tempId < 1000) latestId = "p0" + tempId;
        else latestId = "p" + tempId;
        newPackage.setPackageId(latestId);
        this.packageRepository.save(newPackage);
    }

    @PutMapping(path = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void editPackage(@RequestPart("editRoomType") Package editPackage) {
        this.packageRepository.saveAndFlush(editPackage);
    }

    @DeleteMapping(path = "/delete/{packageId}")
    public void deletePackage(@PathVariable String packageId) {
        Package findPackage = this.packageRepository.findById(packageId).orElse(null);
        if (findPackage != null) this.packageRepository.delete(findPackage);
        else return;
    }

}
