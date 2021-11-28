package int222.project.backend.controllers;

import int222.project.backend.models.PackageDetail;
import int222.project.backend.repositories.PackageDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/packageDetails")
public class PackageDetailController {
    @Autowired
    PackageDetailRepository packageDetailRepository;

    // Package Detail
    @GetMapping("/{packageDetailId}")
    public PackageDetail getPackageDetail(@PathVariable String packageDetailId) {
        return packageDetailRepository.findById(packageDetailId).orElse(null);
    }

    @GetMapping("")
    public List<PackageDetail> getAllPackageDetails() {
        return packageDetailRepository.findAll();
    }

}
