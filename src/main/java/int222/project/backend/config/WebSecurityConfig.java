package int222.project.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableWebSecurity @EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity.cors().and().csrf().disable();
                // dont authenticate this particular request
        //authenticate by anyone
        httpSecurity.authorizeRequests().antMatchers("/api/auth/authenticate").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET,"/api/packages").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET,"/api/paymentMethods").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET,"/api/rooms/**").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET,"/api/roomTypes/**").permitAll();
//        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET,"/api/rooms/roomRequirement/**").permitAll();
//        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET,"/api/rooms/getRemainingRoom/**").permitAll();
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.POST,"api/reservation/add").hasAnyAuthority("customer");
        // authenticate by staff
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET,"/api/reservations/**").hasAuthority("receptionist");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/reservations/**").hasAuthority("receptionist");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.GET,"/api/reservationDetails/**").hasAnyAuthority("receptionist");
        httpSecurity.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/reservationDetails/**").hasAnyAuthority("receptionist");
        // authenticate by admin
        httpSecurity.authorizeRequests().antMatchers("/api/**").hasAnyAuthority("admin");
        // all other requests need to be authenticated
        httpSecurity.authorizeRequests().anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

