package br.com.odontoprime.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Orcamento implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8994923412327695500L;

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Paciente paciente;

	private String tipoConsulta;

	private Double valor;

	private Date data;

	@OneToOne(cascade = { CascadeType.MERGE })
	private Usuario usuario;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@Enumerated(EnumType.STRING)
	private StatusCadastro statusCadastro;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public StatusCadastro getStatusCadastro() {
		return statusCadastro;
	}

	public void setStatusCadastro(StatusCadastro statusCadastro) {
		this.statusCadastro = statusCadastro;
	}

	public Long getId() {
		return id;
	}

	public Orcamento() {
		super();
		this.paciente = new Paciente();
	}

	public Orcamento(Long id, Paciente paciente, String tipoConsulta, Double valor, Date data) {
		super();
		this.id = id;
		this.paciente = paciente;
		this.tipoConsulta = tipoConsulta;
		this.valor = valor;
		this.data = data;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getTipoConsulta() {
		return tipoConsulta;
	}

	public void setTipoConsulta(String tipoConsulta) {
		this.tipoConsulta = tipoConsulta;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return paciente.getNome();
	}

}
