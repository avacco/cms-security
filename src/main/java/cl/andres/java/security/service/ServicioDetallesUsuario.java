package cl.andres.java.security.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.andres.java.security.model.Usuario;

@Service
public class ServicioDetallesUsuario implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;
	
	// Transforma un objeto Usuario a uno UserDetails para el entendimiento de Spring Security
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOp = usuarioService.buscarPorEmail(username);
		if(usuarioOp.isPresent()) {
			return construirObjetoUserDetails(usuarioOp.get());
			
		}else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
	}

	// Convierte en array los roles separados por coma. Por ejemplo, los roles son "ADMIN,USER", split generara un array {ADMIN, USER}
	private UserDetails construirObjetoUserDetails(Usuario usuario) {
		User.UserBuilder builder = User.builder();
		Collection<GrantedAuthority> roles = construirRolesDeUsuario(usuario);
		builder
			.username(usuario.getEmail())
			.password(usuario.getPassword())
			.authorities(roles)
		;
		return builder.build();
	}

	
	// Convierte los roles en String a objetos SimpleGrantedAuthority para el entendimiento de Spring Security
	private Collection<GrantedAuthority> construirRolesDeUsuario(Usuario usuario) {
		HashSet<GrantedAuthority> roles = new HashSet<>();
		for(String rol : usuario.getRoles().split(",")) {
			roles.add(new SimpleGrantedAuthority(rol));
		}
		return roles;
	}

}
