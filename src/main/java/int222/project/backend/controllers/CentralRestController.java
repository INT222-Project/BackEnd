package int222.project.backend.controllers;

import int222.project.backend.models.*;
import int222.project.backend.models.Package;
import int222.project.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8081"}, allowedHeaders = "*")
@RestController
public class CentralRestController {

}
