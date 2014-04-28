package alergias.entity;

import java.io.Serializable;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class Alergia implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id_alergia;
	private String nome;
	private String Obs;
	private Integer id_categoria;	
	

	//GETTERS AND SETTERS
	public Integer getId_alergia() {
		return id_alergia;
	}
	public void setId_alergia(Integer id_alergia) {
		this.id_alergia = id_alergia;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getObs() {
		return Obs;
	}
	public void setObs(String obs) {
		Obs = obs;
	}
	public Integer getId_categoria() {
		return id_categoria;
	}
	public void setId_categoria(Integer id_categoria) {
		this.id_categoria = id_categoria;
	}
	
	//CONSTRUCTORS
	public Alergia(Integer id_alergia, String nome, String obs,
			Integer id_categoria) {
		super();
		this.id_alergia = id_alergia;
		this.nome = nome;
		Obs = obs;
		this.id_categoria = id_categoria;
	}
	public Alergia() {
		super();
	}
	
	@Override
	public String toString() {
		return "Alergia [id_alergia=" + id_alergia + ", nome=" + nome
				+ ", Obs=" + Obs + ", id_categoria=" + id_categoria + "]";
	}
	

	
	
	
	
}
