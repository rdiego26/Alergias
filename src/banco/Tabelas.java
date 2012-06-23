package banco;

public class Tabelas {

	//TABELA cad_alergia
	private static final String TBL_cad_alergia = "cad_alergia";
	//CAMPOS cad_alergia	
	private static final String FIELD_id_alergia = "id_alergia INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT";
	private static final String FIELD_nome_alergia = "nome CHAR(40) NOT NULL CONSTRAINT [nome_alergia] UNIQUE";
	private static final String FIELD_obs_alergia = "obs CHAR(200)";
	private static final String FIELD_id_categoria_fk = "id_categoria INTEGER NOT NULL CONSTRAINT fk_cad_alergia_cad_categoria REFERENCES cad_categoria(id_categoria)";
	
	
	private String cad_alergia = "CREATE TABLE " +
			TBL_cad_alergia + " (" +
			FIELD_id_alergia + " , " + 
			FIELD_nome_alergia + " , " +
			FIELD_obs_alergia + ", " +
			FIELD_id_categoria_fk + ")";

	//TABELA cad_categoria
	private static final String TBL_cad_categoria = "cad_categoria";
	//CAMPOS cad_categoria	
	private static final String FIELD_id_categoria = "id_categoria INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT";
	private static final String FIELD_nome_categoria = "nome CHAR(30) NOT NULL CONSTRAINT [nome_categoria] UNIQUE";
	
	private String cad_categoria = "CREATE TABLE " +
			TBL_cad_categoria + " (" +
			FIELD_id_categoria + " , " + 
			FIELD_nome_categoria + ") ";	

	//GETTERS AND SETTERS -- INICIO
	public static String getTblCadCategoria() {
		return TBL_cad_categoria;
	}

	public static String getFieldIdCategoria() {
		return FIELD_id_categoria;
	}

	public static String getFieldNomeCategoria() {
		return FIELD_nome_categoria;
	}

	public String getCad_alergia() {
		return cad_alergia;
	}

	public void setCad_alergia(String cad_alergia) {
		this.cad_alergia = cad_alergia;
	}

	public static String getTblCadAlergia() {
		return TBL_cad_alergia;
	}

	public static String getFieldIdAlergia() {
		return FIELD_id_alergia;
	}

	public static String getFieldNomeAlergia() {
		return FIELD_nome_alergia;
	}

	public static String getFieldObsAlergia() {
		return FIELD_obs_alergia;
	}
	//GETTERS AND SETTERS -- FIM
	
	public String getCad_categoria() {
		return cad_categoria;
	}

	public void setCad_categoria(String cad_categoria) {
		this.cad_categoria = cad_categoria;
	}

	//CONSTRUTORES
	public Tabelas(String cad_alergia, String cad_categoria) {
		super();
		this.cad_alergia = cad_alergia;
		this.cad_categoria = cad_categoria;
	}


	public Tabelas() {
		super();
	}


	
	
	
	
}
