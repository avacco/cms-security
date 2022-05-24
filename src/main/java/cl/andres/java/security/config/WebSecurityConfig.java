package cl.andres.java.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import cl.andres.java.security.service.ServicioDetallesUsuario;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	ServicioDetallesUsuario serviceDetalleUsuario;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(serviceDetalleUsuario)
			.passwordEncoder(passwordEncoder)
		;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorize -> authorize
					.mvcMatchers("/", "/nosotros", "/contacto", "/ingreso", "/admin/instalar", "admin/index").permitAll() //permite acceso sin autenticacion
					.mvcMatchers("/admin/index", "/admin/reporte").hasAuthority("ADMIN")
					.anyRequest().authenticated() // requiere autenticacion
			)
			.formLogin(form -> form
				.loginPage("/ingreso")
				.defaultSuccessUrl("/",true) // con true fuerza la redireccion a la direccion indicada.
				.permitAll()
			)
			.logout(logout -> logout
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
			)
		;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web
			.ignoring()
			.mvcMatchers("/img/**", "/css/**", "/js/**")
		;
	}
	
}
