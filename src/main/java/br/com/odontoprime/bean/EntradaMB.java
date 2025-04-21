package br.com.odontoprime.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import br.com.odontoprime.entidade.Consulta;
import br.com.odontoprime.entidade.Entrada;
import br.com.odontoprime.entidade.EstadoPagamento;
import br.com.odontoprime.entidade.Parcela;
import br.com.odontoprime.service.ConsultaService;
import br.com.odontoprime.service.EntradaService;
import br.com.odontoprime.service.ParcelaService;
import br.com.odontoprime.util.MensagemUtil;

@Named
@ViewScoped
public class EntradaMB implements Serializable {
 //Testando o git no eclipse
	private static final long serialVersionUID = 6383242975512655099L;
	@Inject
	private EntradaService entradaService;
	private Date dataPesquisa;
	private List<Entrada> movimentacoes;
	private List<Consulta> consultas;
	private Entrada entrada;
	private EstadoPagamento estadoPagamento;
	private Parcela parcela;
	@Inject
	private ParcelaService parcelaService;

	@Inject
	private ConsultaService consultaService;

	private Consulta consulta;

	public Parcela getParcela() {
		return parcela;
	}

	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}

	public EstadoPagamento getEstadoPagamento() {
		estadoPagamento = EstadoPagamento.PAGO;
		return estadoPagamento;
	}

	public Entrada getEntrada() {
		return entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	@PostConstruct
	public void initi() {
		consultas = consultaService.buscarTodos();
		consulta = new Consulta();
		parcela = new Parcela();
		// recuperarDadosPagamento();
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public List<Entrada> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<Entrada> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public Date getDataPesquisa() {
		return dataPesquisa;
	}

	public void setDataPesquisa(Date dataPesquisa) {
		this.dataPesquisa = dataPesquisa;
	}

	public void buscarEntradaPorData() {
		movimentacoes = entradaService.buscarEntradaPorData(dataPesquisa);
	}

	public String selecionarDados(Entrada entrada) {
		entrada = entradaService.buscarEntradaComParcelas(entrada.getId());

		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("entrada", entrada);
		FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.put("consulta", consulta);

		return "PagamentoParcela?faces-redirect=true";
	}

	public void recuperarDadosPagamento() {
		entrada = (Entrada) FacesContext.getCurrentInstance()
				.getExternalContext().getFlash().get("entrada");
		consulta = (Consulta) FacesContext.getCurrentInstance()
				.getExternalContext().getFlash().get("consulta");

		if (entrada == null) {
			entrada = new Entrada();
		} else {

			entrada = entradaService.buscarEntradaComParcelas(entrada.getId());
		}

		if (consulta == null) {
			consulta = new Consulta();
		}
	}

	public void efetuarPagamentoParcela() {
		parcelaService.efetuarPagamentoParcela(parcela, entrada);
	}

	public void mensagemCancelamentoPagamentoParcela() {
		MensagemUtil.enviarMensagem("Pagamento cancelado!",
				FacesMessage.SEVERITY_INFO);
	}

	public void cancelarPagamentoParcela() {
		parcelaService.cancelarPagamento(parcela);
	}

	public void limparDataPagamento() {
		parcelaService.limparDataPagamentoParcela(parcela);
	}

	public void buscarConsultaComParcela(ComponentSystemEvent event) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			this.consulta = consultaService
					.buscarConsultaComParcela(this.consulta.getEntrada()
							.getId());
			this.entrada = consulta.getEntrada();
		}
	}
}
