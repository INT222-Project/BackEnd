package int222.project.backend.controllers;

import int222.project.backend.config.JwtTokenUtil;
import int222.project.backend.exceptions.Error;
import int222.project.backend.models.*;
import int222.project.backend.repositories.CustomerRepository;
import int222.project.backend.repositories.ReceptionistRepository;
import int222.project.backend.services.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"}, allowedHeaders = "*")
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtUserDetailService jwtUserDetailService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ReceptionistRepository receptionistRepository;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest){
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final AuthenticationUser authenticationUser = jwtUserDetailService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(authenticationUser);
        System.out.println("username : " + authenticationUser.getUsername() + "password : " + authenticationUser.getPassword());
        Customer customer = customerRepository.findCustomerByEmail(authenticationUser.getUsername()).orElse(null);
        Receptionist receptionist = receptionistRepository.findReceptionistByEmail(authenticationUser.getUsername()).orElse(null);
        if(customer != null){
            return ResponseEntity.ok(new JwtResponse<Customer>(token, customer, authenticationUser.getAuthorities()));
        }
        else if(receptionist != null){
            return ResponseEntity.ok(new JwtResponse<Receptionist>(token, receptionist,authenticationUser.getAuthorities()));
        }
        else if(authenticationUser.getAuthorities().stream().findFirst().get().getAuthority() == "admin"){
            Admin admin = new Admin(authenticationUser.getUsername(),authenticationUser.getPassword());
            return ResponseEntity.ok(new JwtResponse<Admin>(token, admin,authenticationUser.getAuthorities()));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error("Unable to generate token !",HttpStatus.BAD_REQUEST.value()));
        }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping(path = "/createUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createUser(@RequestPart("newUser") Customer newCustomer){
        List<Customer> customerList = customerRepository.findAll();
        String latestId = null;
        for(int i = 0 ; i < customerList.size() ; i++){
            if(i+1 == customerList.size()-1) break;
            String id = customerList.get(i).getCustomerId().substring(1);
            String nextId = customerList.get(i+1).getCustomerId().substring(1);
            if(Integer.parseInt(id)+1 != Integer.parseInt(nextId)){
                latestId = id;
            }
        }
        if(latestId == null) latestId = customerList.get(customerList.size()-1).getCustomerId().substring(1);
        int tempId = Integer.parseInt(latestId) + 1;
        if(tempId < 10) latestId = "c00" + tempId;
        else if (tempId < 100) latestId = "c0" + tempId;
        else latestId = "c" + tempId;
        newCustomer.setCustomerId(latestId);
        try {
            return ResponseEntity.ok(this.customerRepository.save(newCustomer));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Error("Sorry, We could not create user." + e.getMessage(),HttpStatus.NOT_ACCEPTABLE.value()));
        }
    }

    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<?> getAllUsers(){
        List<JwtResponse<?>> responses = new ArrayList<>();
        for(Customer temp : customerRepository.findAll()){
            AuthenticationUser authenticationUser = jwtUserDetailService.loadUserByUsername(temp.getEmail());
            responses.add(new JwtResponse<Customer>(null,temp,authenticationUser.getAuthorities()));
        }
        for(Receptionist temp: receptionistRepository.findAll()){
            AuthenticationUser authenticationUser = jwtUserDetailService.loadUserByUsername(temp.getEmail());
            responses.add(new JwtResponse<Receptionist>(null,temp,authenticationUser.getAuthorities()));
        }
        return ResponseEntity.ok(responses);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
