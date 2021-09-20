package int222.project.backend.controllers;

import int222.project.backend.models.PackageDetail;
import int222.project.backend.repositories.PackageDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/packageDetails")
public class PackageDetailController {
    @Autowired
    PackageDetailRepository packageDetailRepository;
    // Package Detail
    @GetMapping("/{packageDetailId}")
    public PackageDetail getPackageDetail (@PathVariable String packageDetailId){ return packageDetailRepository.findById(packageDetailId).orElse(null); }

    @GetMapping("")
    public List<PackageDetail> getAllPackageDetails(){ return packageDetailRepository.findAll(); }

}
