package int222.project.backend.controllers;

import int222.project.backend.models.Package;
import int222.project.backend.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api/packages")
public class PackageController {
    @Autowired
    PackageRepository packageRepository;
    // Package
    @GetMapping("/{packageId}")
    public Package getPackage(@PathVariable String packageId){ return packageRepository.findById(packageId).orElse(null); }

    @GetMapping("")
    public List<Package> getAllPackages(){ return packageRepository.findAll(); }

}
