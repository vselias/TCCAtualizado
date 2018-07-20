package br.com.odontoprime.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import br.com.odontoprime.entidade.Entrada;
import br.com.odontoprime.entidade.Parcela;
import br.com.odontoprime.util.Transactional;

public class EntradaDAO extends GenericDAO<Entrada, Long>implements Serializable {

	@Inject
	private EntityManager entityManager;

	/**
	 * 
	 */
	private static final long serialVersionUID = -759469492710754415L;

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Entrada> buscarTodasEntradas() {
		Query query = entityManager.createQuery("select e from Entrada e");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Entrada> buscarEntradaPorData(Date data) {
		Query query = entityManager.createQuery("select e from Entrada e where e.dataLancamento = :data");
		query.setParameter("data", data);
		return query.getResultList();
	}

	@Transactional
	public Double buscarValorTotalEntrada() {
		Double total = null;
		try {
			total = (Double) entityManager.createQuery("select sum(p.valor) from Entrada e join e.parcelas p")
					.getSingleResult();
			if (total == null)
				total = new Double(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Transactional
	public Double buscarValorTotalEntradaFechamento() {
		Double total = null;
		try {
			total = (Double) entityManager
					.createQuery("select sum(p.valor) from Entrada e join e.parcelas p"
							+ " where date(p.dataPagamento) = CURRENT_DATE and p.estadoPagamento = 'PAGO'")
					.getSingleResult();
			if (total == null) {
				total = new Double(0);
			}
		} catch (Exception e) {
		}
		return total;
	}

	@Transactional
	public Double buscarValorTotalEntradaFechamento(Date dataMovimentacao) {
		Double total = null;
		try {
			total = (Double) entityManager
					.createQuery("select sum(p.valor) from Entrada e join e.parcelas p"
							+ " where date(p.dataPagamento) = :data and p.estadoPagamento = 'PAGO'")
					.setParameter("data", dataMovimentacao).getSingleResult();
			if (total == null) {
				total = new Double(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Transactional
	public Double buscarValorTotalUltimaEntrada(Date date) {
		Double total = null;
		try {
			total = (Double) entityManager.createQuery(
					"select sum(p.valor) from Entrada e join e.parcelas p where date(p.dataPagamento) = :data"
							+ " and p.estadoPagamento = 'Pago'")
					.setParameter("data", date).getSingleResult();
			if (total == null) {
				total = new Double(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}
	@Transactional
	public Entrada buscarEntradaComParcelas(Long entradaId) {
		try {
			return (Entrada) entityManager
					.createQuery("select e from Entrada e left join fetch e.parcelas where e.id = :id")
					.setParameter("id", entradaId).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
