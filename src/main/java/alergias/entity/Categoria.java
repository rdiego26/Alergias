package alergias.entity;

import java.io.Serializable;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class Categoria implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id_categoria;
	private String nome;
	
	
	//GETTERS AND SETTERS
	public Integer getId_categoria() {
		return id_categoria;
	}
	public void setId_categoria(Integer id_categoria) {
		this.id_categoria = id_categoria;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	@Override
	public String toString() {
		return "Categoria [id_categoria=" + id_categoria + ", nome=" + nome
				+ "]";
	}
	
	//CONSTRUTORES
	public Categoria(Integer id_categoria, String nome) {
		super();
		this.id_categoria = id_categoria;
		this.nome = nome;
	}
	public Categoria() {
		super();
	}
	
	
	
}
