package int222.project.backend.controllers;

import int222.project.backend.config.JwtTokenUtil;
import int222.project.backend.models.*;
import int222.project.backend.repositories.CustomerRepository;
import int222.project.backend.repositories.ReceptionistRepository;
import int222.project.backend.services.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
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
            return ResponseEntity.ok(null);
        }
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
