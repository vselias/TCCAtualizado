/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.odontoprime.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;

import br.com.odontoprime.util.Transactional;

/**
 *
 * @author Elias
 * @param <T>
 * @param <PK>
 */

public abstract class GenericDAO<T, PK> implements Serializable {

	private static final long serialVersionUID = 5223292658070091151L;

	@Inject
	private EntityManager entityManager;

	@Transactional
	public T buscarPorId(Long id, Class<T> classe) {
		return (T) entityManager.find(classe, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<T> buscarTodos(Class<T> clazz) {
		return entityManager.createQuery("FROM " + clazz.getName())
				.getResultList();
	}

	public T recuperarReferencia(Class<T> clazz, Long id) {
		return entityManager.getReference(clazz, id);
	}

	@Transactional
	public T salvar(T entidade) throws ConstraintViolationException {

		return entityManager.merge(entidade);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public T atualizar(T entidade) {
		return entityManager.merge(entidade);
	}

	@Transactional
	public void remover(T entidade) {
		try {
			entidade = entityManager.merge(entidade);
			entityManager.remove(entidade);
			entityManager.flush();
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public T getReference(Class<T> clazz, Serializable id) {
		return (T) entityManager.getReference(clazz, id);
	}

}
