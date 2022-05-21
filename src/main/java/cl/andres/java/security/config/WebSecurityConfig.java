package cl.andres.java.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorize -> authorize
					.mvcMatchers("/", "/nosotros", "/contacto", "/ingreso").permitAll() //permite acceso sin autenticacion
					.mvcMatchers("/admin/index", "/admin/reporte").hasRole("ADMIN")
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
