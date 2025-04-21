package br.com.odontoprime.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Paciente implements Serializable {

	private static final long serialVersionUID = 8527527720637778339L;

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(nullable = false, unique = true)
	private String cpf;
	private String email;
	private String sexo;
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	private String nomeImagem;
	@Transient
	private String imagemRecortada;
	@Transient
	private byte[] byteImg;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	private String nomeUsuarioRegistrou;
	@Enumerated(EnumType.STRING)
	private StatusCadastro statusCadastro;
	@Transient
	private String imagemCropper;
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<Foto> fotos;
	@OneToMany(mappedBy = "paciente", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Consulta> consultas;
	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;
	private String celular;
	private String telefone;

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getImagemCropper() {
		return imagemCropper;
	}

	public void setImagemCropper(String imagemCropper) {
		this.imagemCropper = imagemCropper;
	}

	public StatusCadastro getStatusCadastro() {
		return statusCadastro;
	}

	public void setStatusCadastro(StatusCadastro statusCadastro) {
		this.statusCadastro = statusCadastro;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getNomeUsuarioRegistrou() {
		return nomeUsuarioRegistrou;
	}

	public void setNomeUsuarioRegistrou(String nomeUsuarioRegistrou) {
		this.nomeUsuarioRegistrou = nomeUsuarioRegistrou;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	public String getImagemRecortada() {
		return imagemRecortada;
	}

	public void setImagemRecortada(String imagemRecortada) {
		this.imagemRecortada = imagemRecortada;
	}

	public byte[] getByteImg() {
		return byteImg;
	}

	public void setByteImg(byte[] byteImg) {
		this.byteImg = byteImg;
	}

	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

	public Paciente() {
		consultas = new ArrayList<Consulta>();
		endereco = new Endereco();
		this.telefone = "N/A";
		this.celular = "N/A";
	}

	public Long getId() {
		return id;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean isNovo() {
		return getId() == null;
	}
}
