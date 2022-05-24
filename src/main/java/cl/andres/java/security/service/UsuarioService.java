package cl.andres.java.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.andres.java.security.model.Usuario;
import cl.andres.java.security.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public long cantidadUsuarios() {
		return usuarioRepository.count();
	}
	
	public Optional<Usuario> buscarPorEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}
	
	// Crea el usuario con el password codificado
	public Usuario crearUsuario(Usuario usuario) {
		String passwordCodificado = encoder.encode(usuario.getPassword());
		usuario.setPassword(passwordCodificado);
		return usuarioRepository.saveAndFlush(usuario);
	}
	
}
