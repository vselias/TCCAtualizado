package br.com.odontoprime.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Parcela implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -341415666946160292L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double valor;
	@Temporal(TemporalType.DATE)
	private Date dataVencimento;
	private int numParcela = 0;
	@Enumerated(EnumType.STRING)
	private EstadoPagamento estadoPagamento;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPagamento;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRegistro;
	
	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public EstadoPagamento getEstadoPagamento() {
		return estadoPagamento;
	}

	public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
		this.estadoPagamento = estadoPagamento;
	}

	public int getNumParcela() {
		return numParcela;
	}

	public void setNumParcela(int numParcela) {
		this.numParcela = numParcela;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Parcela() {
		numParcela = 0;
	}

	@Override
	public String toString() {
		return "Parcela [id=" + id + ", valor=" + valor + ", dataVencimento=" + dataVencimento + ", numParcela="
				+ numParcela + ", estadoPagamento=" + estadoPagamento + ", dataPagamento=" + dataPagamento
				+ ", dataRegistro=" + dataRegistro + "]";
	}

}
