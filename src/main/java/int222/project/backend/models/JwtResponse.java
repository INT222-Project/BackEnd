package int222.project.backend.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class JwtResponse<T> implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private T object;
    private Collection<? extends GrantedAuthority> role;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public JwtResponse(String jwttoken, T object, Collection<? extends GrantedAuthority> role) {
        this.jwttoken = jwttoken;
        if(object.getClass() == Customer.class){
            String encodePassword = passwordEncoder.encode(((Customer) object).getPassword());
            ((Customer) object).setPassword(encodePassword);
            this.object = object;
        }
        else if(object.getClass() == Receptionist.class){
            String encodePassword = passwordEncoder.encode(((Receptionist) object).getPassword());
            ((Receptionist) object).setPassword(encodePassword);
            this.object = object;
        }
        this.role = role;
    }

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public String getToken() {
        return this.jwttoken;
    }

    public T getAuthenticationUser() {
        return object;
    }

    public Collection<? extends GrantedAuthority> getRole() {
        return role;
    }
}
