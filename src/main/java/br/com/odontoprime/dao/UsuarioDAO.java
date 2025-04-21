package br.com.odontoprime.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.odontoprime.entidade.Usuario;
import br.com.odontoprime.util.Transactional;

public class UsuarioDAO extends GenericDAO<Usuario, Long> {

	@Inject
	private EntityManager entityManager;

	private static final long serialVersionUID = 7620333730534615763L;

	@Transactional
	public Usuario buscarUsuarioBD(Usuario usuario) {
		try {

			Query query = entityManager
					.createQuery("select u from Usuario u where u.login = :login and u.senha = :senha");
			query.setParameter("login", usuario.getLogin());
			query.setParameter("senha", usuario.getSenha());
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public boolean buscarUsuarioPorLogin(String login) {
		try {

			Query query = entityManager.createQuery("select u from Usuario u where u.login = :login");
			query.setParameter("login", login);
			if (query.getResultList() != null && query.getResultList().size() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Usuario recuperarSenha(Usuario usuario) {
		try {
			
			
			
			Query query = entityManager
					.createQuery("select u from Usuario u where u.email = :email and u.cpf = :cpf");
			query.setParameter("email", usuario.getEmail());
			query.setParameter("cpf", usuario.getCpf());
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
