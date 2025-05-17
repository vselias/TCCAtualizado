package br.com.odontoprime.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.SelectEvent;

import br.com.odontoprime.entidade.Orcamento;
import br.com.odontoprime.entidade.Paciente;
import br.com.odontoprime.service.OrcamentoService;
import br.com.odontoprime.service.PacienteService;
import br.com.odontoprime.util.MensagemUtil;

@Named
@ViewScoped
public class OrcamentoMB implements Serializable {

	private static final long serialVersionUID = -8420195583729378369L;
	private Orcamento orcamento;
	@Inject
	private PacienteService pacienteService;
	@Inject
	private OrcamentoService orcamentoService;
	private List<Paciente> pacientes;
	private List<Orcamento> orcamentos;
	private List<Orcamento> orcamentosFiltrados;

	public List<Orcamento> getOrcamentosFiltrados() {
		return orcamentosFiltrados;
	}

	public void setOrcamentosFiltrados(List<Orcamento> orcamentosFiltrados) {
		this.orcamentosFiltrados = orcamentosFiltrados;
	}

	public List<Orcamento> getOrcamentos() {

		return orcamentos;
	}

	public void setOrcamentos(List<Orcamento> orcamentos) {
		this.orcamentos = orcamentos;
	}

	public List<Paciente> getPacientes() {

		return pacientes;
	}

	public List<Paciente> pesquisarPacientesAutoComplete(String nome) {
		List<Paciente> pacientesPesquisa = new ArrayList<>();

		if (pacientes == null)
			carregarPacientes();

		pacientes.forEach(p -> {

			if (p.getNome().toLowerCase().startsWith(nome.toLowerCase())) {
				pacientesPesquisa.add(p);
			}
		});

		return pacientesPesquisa;
	}

	@SuppressWarnings("unused")
	public void pacienteSelecionado(SelectEvent event) {
		Paciente paciente = (Paciente) event.getObject();
		System.out.println("Metodo de seleção chamado: " + paciente.getNome());
		if (paciente != null) {
			MensagemUtil.enviarMensagem("Paciente selecionado: " + paciente.getNome(), FacesMessage.SEVERITY_INFO);
		}
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public Orcamento getOrcamento() {

		if (orcamento == null) {
			orcamento = new Orcamento();
		}
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	@PostConstruct
	public void init() {
		orcamento = new Orcamento();
		carregarPacientes();
		carregarOrcamentos();
	}

	public void carregarPacientes() {
		this.pacientes = pacienteService.buscarTodos();
	}

	public void salvar() {
		orcamentoService.salvar(orcamento);
	}

	public void gerarPDF(Object document) {
		orcamentoService.gerarPDF(document);
	}

	public void remover() {
		orcamentoService.remover(orcamento);
		carregarOrcamentos();
	}

	public void carregarOrcamentos() {
		this.orcamentos = orcamentoService.buscarTodos();
	}

	public void limparCampos() {
		init();
		MensagemUtil.enviarMensagem("Campos limpos!", FacesMessage.SEVERITY_INFO);
	}
}
