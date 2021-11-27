package int222.project.backend.services;

import int222.project.backend.models.AuthenticationUser;
import int222.project.backend.models.Customer;
import int222.project.backend.models.Receptionist;
import int222.project.backend.models.Role;
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
import java.util.List;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ReceptionistRepository receptionistRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthenticationUser loadUserByUsername(String s) throws UsernameNotFoundException{
        Customer customer = customerRepository.findCustomerByEmail(s).orElse(null);
        Receptionist receptionist = receptionistRepository.findReceptionistByEmail(s).orElse(null);
        if(customer != null){
            Role role = new Role("customer");
            List<Role> roles= new ArrayList<>();
            roles.add(role);
            return new AuthenticationUser(customer.getEmail(),bCryptPasswordEncoder.encode(customer.getPassword()),roles);
        }
        else if(receptionist != null){
            Role role = new Role("receptionist");
            List<Role> roles= new ArrayList<>();
            roles.add(role);
            return new AuthenticationUser(receptionist.getEmail(),bCryptPasswordEncoder.encode(receptionist.getPassword()),roles);
        }
        else if(s.equals("admin")){
            Role role = new Role("admin");
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            return new AuthenticationUser(s,bCryptPasswordEncoder.encode("admin"),roles);
        }
        else{
            throw new UsernameNotFoundException("User not found with username: " + s);
        }
    }
}
