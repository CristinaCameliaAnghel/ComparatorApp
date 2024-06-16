package etti.comparator.Configurations;

import etti.comparator.services.CustomSuccessHandler;
import etti.comparator.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/admin-page").hasAuthority("ADMIN")
                        .requestMatchers("/user-page", "/profile").hasAuthority("USER")
                        .requestMatchers("/provider-page", "/provider/requests", "/provider/clients-feedback").hasAuthority("PROVIDER")
                        .requestMatchers("/admin/providers", "/admin/users").hasAuthority("ADMIN") // Restriction for ProvidersList page
                        .requestMatchers("/registration","/ServicesComparator","/compare-utilities", "/registrationasprovider", "/css/**", "/index", "/CreateProduct", "/EditProduct",
                                "/utility-list","/utilities","/forgot-password", "/products", "services", "/service-list","/serviceOffers/CreateServiceOffers", "/serviceOffers/EditServiceOffers", "/products/create", "/products/delete", "/products/edit", "/compare-services", "/utilitati", "/images/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customSuccessHandler)
                        .permitAll())
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
