package br.com.odontoprime.service;

import java.io.File;
import java.io.FileNotFoundException;

import javax.faces.application.FacesMessage;
import javax.imageio.stream.FileImageOutputStream;

import br.com.odontoprime.util.MensagemUtil;

public interface ImagemServiceInt {

	default boolean criarArquivo(String caminho, byte[] dados, String nomeImagem) {

		FileImageOutputStream fileImageOutputStream;
		if (dados != null) {

			try {
				File diretorio = new File(caminho);
				if (!diretorio.exists()) {
					diretorio.mkdirs();
				}
				fileImageOutputStream = new FileImageOutputStream(new File(caminho, nomeImagem));
				fileImageOutputStream.write(dados, 0, dados.length);
				fileImageOutputStream.close();
				System.out.println("[criarArquivo] - Arquivo criado com sucesso.");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[criarArquivo] - Erro ao criar arquivo.");
			}
		}
		return false;
	}

}
