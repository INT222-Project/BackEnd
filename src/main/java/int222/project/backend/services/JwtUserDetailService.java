package int222.project.backend.services;

import int222.project.backend.models.AuthenticationUser;
import int222.project.backend.models.Customer;
import int222.project.backend.models.Receptionist;
import int222.project.backend.repositories.CustomerRepository;
import int222.project.backend.repositories.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ReceptionistRepository receptionistRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String s){
        Customer customer = customerRepository.findCustomerByEmail(s).orElse(null);
        Receptionist receptionist = receptionistRepository.findReceptionistByEmail(s).orElse(null);
        if(customer != null){
            return new User(customer.getEmail(),bCryptPasswordEncoder.encode(customer.getPassword()),new ArrayList<>());
        }
        else if(receptionist != null){
            return new User(receptionist.getEmail(),bCryptPasswordEncoder.encode(receptionist.getPassword()),new ArrayList<>());
        }
        else{
            throw new UsernameNotFoundException("User not found with username: " + s);
        }
    }
}
