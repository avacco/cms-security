package cl.andres.java.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Esta sera la clase que utilizara Spring Security para codificar contraseñas.
@Component
public class PasswordEncryptionConfig {

	@Bean
	public PasswordEncoder encriptadorPassword() {
		return new BCryptPasswordEncoder();
	}
}
