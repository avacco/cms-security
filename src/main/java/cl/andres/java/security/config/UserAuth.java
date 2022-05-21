package cl.andres.java.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserAuth {

	// Crea usuarios con sus roles.
	@Bean
	public UserDetailsService usuarios() {
		UserBuilder userBuilder = User.withDefaultPasswordEncoder();
		UserDetails usuario1 = userBuilder
			.username("Andres")
			.password("andres12")
			.roles("ADMIN")
			.build()
		;
		
		UserDetails usuario2 = userBuilder
			.username("usuario")
			.password("123")
			.roles("USER")
			.build()
		;
		
		return new InMemoryUserDetailsManager(usuario1,usuario2);
		
	}
}
