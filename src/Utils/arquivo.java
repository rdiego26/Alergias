package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

public class arquivo {

	private String nome;
	private Byte conteudo;
	
	
	//GETTERS AND SETTERS
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Byte getConteudo() {
		return conteudo;
	}
	public void setConteudo(Byte conteudo) {
		this.conteudo = conteudo;
	}

	//CONSTRUCTORS
	public arquivo(String nome, Byte conteudo) {
		super();
		this.nome = nome;
		this.conteudo = conteudo;
	}
	public arquivo() {
		super();
	}
	

	@Override
	public String toString() {
		return "arquivo [nome=" + nome + ", conteudo=" + conteudo + "]";
	}
	
	
}
