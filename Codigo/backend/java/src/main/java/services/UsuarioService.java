package services;

import java.util.List;
import java.util.UUID;

import dao.DAO;
import dao.UsuarioDao;
import models.Usuario;

public class UsuarioService {
	private final UsuarioDao usuarioDao;

	public UsuarioService(DAO dao) {
		this.usuarioDao = dao.getJdbiContext().onDemand(UsuarioDao.class);
		createTableIfNotExists();
	}

	public void createTableIfNotExists() {
		usuarioDao.createTable();
	}

	public void addUsuario(Usuario usuario) throws Exception {
		try {
			if (usuario.isValid()) {
				usuarioDao.insert(usuario.getId(), usuario.getName(), usuario.getCpf(), usuario.getEmail(),
						usuario.getSalary(),
						usuario.getCellNumber(), usuario.getPassword(), usuario.getExpenses(), usuario.getRegDate());
			} else {
				throw new IllegalArgumentException("Dados do usuário inválidos");
			}
		} catch (Exception e) {
			throw new Exception("Erro ao cadastrar usuário: " + e);
		}
	}

	public Usuario getUsuarioById(UUID id) {
		return usuarioDao.findById(id);
	}

	public List<Usuario> getAllUsuarios() {
		return usuarioDao.listUsuarios();
	}

	public void updateUsuario(UUID id, Usuario usuario) {
		if (usuario.isValid()) {
			Usuario existingUsuario = usuarioDao.findById(id);
			if (existingUsuario != null) {
				usuarioDao.update(id, usuario.getName(), usuario.getCpf(), usuario.getEmail(), usuario.getSalary(),
						usuario.getCellNumber(), usuario.getPassword(), usuario.getExpenses(), usuario.getRegDate());
			} else {
				throw new IllegalArgumentException("Usuário não encontrado");
			}
		} else {
			throw new IllegalArgumentException("Dados do usuário inválidos");
		}
	}

	public void deleteUsuario(UUID id) {
		usuarioDao.delete(id);
	}

}
