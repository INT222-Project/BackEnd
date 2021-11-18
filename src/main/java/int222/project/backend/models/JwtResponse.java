package int222.project.backend.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

public class JwtResponse<T> implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private T object;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public JwtResponse(String jwttoken, T object) {
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
}
