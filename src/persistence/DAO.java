package persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import banco.Tabelas;

public class DAO extends SQLiteOpenHelper{

	//Caracteristicas do Banco de Dados
	private static final String DB_NAME = "alergia";
	//TABELAS
	static final String TBL_ALERGIA = "cad_alergia";
	static final String TBL_CATEGORIA = "cad_categoria";
	
	private static final Integer VERSION = 3;// a Aplicação se baseia neste atributo para
	
	
	//Criar a tabela SCRIPT
	private Tabelas t = new Tabelas();
	
	// db vem da classe SQLiteOpenHelper

	public DAO(Context context)
	{
		super(context, DB_NAME, null, VERSION);
	}
	
	//Métodos relacionados ao BD -- INICIO
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		ContentValues cv = new ContentValues();
		
		cv.put("nome", "Outros"); //Nome da categoria, para insert inicial
		
		db.execSQL(t.getCad_categoria()); //CREATE cad_categoria
		db.insert(TBL_CATEGORIA, null, cv);//INSERT INICIAL
		db.execSQL(t.getCad_alergia()); //CREATE cad_alergia
	}
	
	//Executado Quando alterado o atributo VERSION
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		if(oldVersion == 1 && newVersion == 2) // Nessa versão entrou a categoria
		{
			ContentValues cv = new ContentValues();
			
			cv.put("nome", "Outros"); //Nome da categoria, para insert inicial
			db.insert(TBL_CATEGORIA, null, cv);//INSERT INICIAL
			db.execSQL(t.getCad_categoria()); //CREATE cad_categoria
			//db.execSQL("INSERT INTO cad_categoria(nome) VALUES(\"Outros\")"); //INSERT INICIAL			
			
			//Adiciona nova coluna
			db.execSQL("ALTER TABLE cad_alergia ADD COLUMN id_categoria INTEGER");
			
			cv.clear();
			cv.put("id_categoria", 1);
			db.update(TBL_ALERGIA, cv, null, null); //Atualiza as alergias já cadastradas.			
			//db.execSQL("UPDATE cad_alergia SET id_categoria = 1");
			
			db.execSQL("ALTER TABLE cad_alergia ADD CONSTRAINT fk_cad_alergia_cad_categoria FOREIGN KEY(id_categoria) REFERENCES cad_categoria(id_categoria)");
		}
	}

	//Métodos relacionados ao BD -- FIM	
}
