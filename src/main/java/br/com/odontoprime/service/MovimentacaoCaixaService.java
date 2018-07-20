package br.com.odontoprime.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;

import br.com.odontoprime.dao.MovimentacaoCaixaDAO;
import br.com.odontoprime.entidade.StatusCadastro;
import br.com.odontoprime.entidade.MovimentacaoCaixa;
import br.com.odontoprime.util.MensagemUtil;

@SuppressWarnings("serial")
public class MovimentacaoCaixaService implements Serializable {
	@Inject
	private MovimentacaoCaixaDAO movimentacaoCaixaDAO;

	public void salvar(MovimentacaoCaixa movimentacaoCaixa) {
		try {

			if (movimentacaoCaixa.getData() == null) {
				movimentacaoCaixa.setData(new Date());
			}

			if (isMovimentacaoNotNull(movimentacaoCaixa)) {
				salvarMovimentacao(movimentacaoCaixa);
			}
		} catch (PersistenceException e) {
			Throwable t = e.getCause();
			while ((t != null) && !(t instanceof ConstraintViolationException)) {
				t = t.getCause();
			}
			if (t instanceof ConstraintViolationException) {
				MensagemUtil.enviarMensagem("Já possui FECHAMENTO nessa data. Você pode atualiza-la",
						FacesMessage.SEVERITY_WARN);
			}
			movimentacaoCaixa.setId(null);

		} catch (Exception ex) {

			ex.printStackTrace();
			MensagemUtil.enviarMensagem(
					"Erro ao cadastrar abertura! Saia do sistema e tente novamente, caso persistir o erro contate o administrador. ERRO: ",
					FacesMessage.SEVERITY_ERROR);

			movimentacaoCaixa.setId(null);
		}

	}

	public List<MovimentacaoCaixa> buscarTodos() {
		return movimentacaoCaixaDAO.buscarTodos(MovimentacaoCaixa.class);
	}

	public boolean movimentacaoContainsUsuario(MovimentacaoCaixa movimentacaoCaixa) {
		return isMovimentacaoNotNull(movimentacaoCaixa) && movimentacaoCaixa.getUsuario() != null
				&& movimentacaoCaixa.getUsuario().getId() != null && movimentacaoCaixa.getUsuario().getId() > 0;
	}

	public boolean isNovaMovimentacaoCaixa(MovimentacaoCaixa movimentacaoCaixa) {
		return movimentacaoCaixa != null && movimentacaoCaixa.getId() == null;
	}

	public boolean isMovimentacaoEditavel(MovimentacaoCaixa movimentacaoCaixa) {
		return isMovimentacaoNotNull(movimentacaoCaixa) && movimentacaoCaixa.getId() != null
				&& movimentacaoCaixa.getId() > 0;
	}

	public boolean isMovimentacaoNotNull(MovimentacaoCaixa movimentacaoCaixa) {
		return movimentacaoCaixa != null;
	}

	public Double buscarValorInicialFechamento(Date dataMovimentacao) {
		if (dataMovimentacao == null)
			dataMovimentacao = new Date();
		return movimentacaoCaixaDAO.buscarValorInicialFechamento(dataMovimentacao);
	}

	public MovimentacaoCaixa buscarMovimentacaoCadastrada() {
		return movimentacaoCaixaDAO.existeFechamento(new Date());
	}

	public void salvarMovimentacao(MovimentacaoCaixa movimentacaoCaixa) {
		MovimentacaoCaixa caixa = null;

		if (isMovimentacaoEditavel(movimentacaoCaixa)) {
			caixa = movimentacaoCaixa;
			movimentacaoCaixa.setStatusCadastro(StatusCadastro.ATUALIZACAO);
			movimentacaoCaixa.setValorTotal((caixa.getValorInicial() + caixa.getEntrada()) - caixa.getSaida());
			movimentacaoCaixaDAO.salvar(movimentacaoCaixa);
			MensagemUtil.enviarMensagem("Fechamento atualizado com sucesso!", FacesMessage.SEVERITY_INFO);
		} else {
			caixa = movimentacaoCaixa;
			movimentacaoCaixa.setStatusCadastro(StatusCadastro.CADASTRO);
			movimentacaoCaixa.setValorTotal((caixa.getValorInicial() + caixa.getEntrada()) - caixa.getSaida());
			movimentacaoCaixaDAO.salvar(movimentacaoCaixa);
			MensagemUtil.enviarMensagem("Fechamento cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
		}
	}

	public MovimentacaoCaixa buscarMovimentacaoAbertura() {
		return movimentacaoCaixaDAO.buscarMovimentacaoAbertura();
	}
	
	
	public boolean existeFechamentoPorData(Date dataFechamento){
		return movimentacaoCaixaDAO.existeFechamentoPorData(dataFechamento);
	}

}
