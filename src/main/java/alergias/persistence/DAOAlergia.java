package alergias.persistence;

import alergias.entity.Alergia;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class DAOAlergia {

	//Nome dos Campos da Tabela
	private static final String[] FIELD_ALERGIA = {"id_alergia", "nome", "obs", "id_categoria"};
	protected SQLiteDatabase db;
	private static DAOAlergia instance = new DAOAlergia(); // Padr�o Singleton	
	
	
	
	/**
	 * Método criado para trabalhar com Singleton
	 * @param context
	 * @return Instância para DAOAlergia
	 */
	public static DAOAlergia getInstance(Context context)
	{
		if(instance.db == null || !instance.db.isOpen())
		{
			instance.db = new DAO(context).getWritableDatabase(); //Permite a comunica��o com o banco
		}
		return instance;
	}
	
	/**
	 * Persiste uma Alergia
	 * @param alergia 
	 * @return -1 para erro
	 */
	public int save(Alergia alergia)
	{
		int retorno = -1; //Criado pois o INSERT sempre retorna o id, depois de criado.
		
		db.beginTransaction();
		
		try
		{
			ContentValues cv = new ContentValues();
			//Setando valores para os campos
			cv.put(FIELD_ALERGIA[1], alergia.getNome()); //nome
			cv.put(FIELD_ALERGIA[2], alergia.getObs()); //obs
			cv.put(FIELD_ALERGIA[3], alergia.getId_categoria()); //id_categoria

			
			//Grava efetivamente os dados
			retorno = (int) db.insert(DAO.TBL_ALERGIA, null, cv);
		
			db.setTransactionSuccessful();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Log.e("DAOAlergia->save()", e.getMessage());
		}
		finally
		{
			db.endTransaction();
		}
		
		return retorno;
	}
	
	/**
	 * AlergiaFactory, recebe um Cursor de uma consulta
	 * @param cursor
	 * @return Alergia
	 */
	private Alergia alergiaFactory(Cursor cursor)
	{
		Integer idAlergia = cursor.getInt(cursor.getColumnIndex(FIELD_ALERGIA[0]));
		String  nome     =  cursor.getString(cursor.getColumnIndex(FIELD_ALERGIA[1]));
		String  Obs       = cursor.getString(cursor.getColumnIndex(FIELD_ALERGIA[2]));
		Integer idCategoria  = cursor.getInt(cursor.getColumnIndex(FIELD_ALERGIA[3]));

		Alergia A = new Alergia(idAlergia, nome, Obs, idCategoria);//Cria objeto com os dados obtidos
				
		return A;
	}
	
	/**
	 * Retorna todas Alergias cadastradas ordernado pelo nome ASC
	 * @return List<Alergia>
	 */
	public List<Alergia> findAll()
	{
		List<Alergia> lista = new ArrayList<Alergia>();
		
		Cursor c = db.query(DAO.TBL_ALERGIA, null, null, null, null, null, "nome ASC");
		
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			Alergia A = alergiaFactory(c);
			
			c.moveToNext();
			lista.add(A);
		}
		c.close();
		return lista;
	}
	
	/**
	 * Busca Alergia pelo id
	 * @param id
	 * @return Alergia
	 */
	public Alergia buscar(int id)
	{
		
		Cursor c = db.query(DAO.TBL_ALERGIA, null, FIELD_ALERGIA[0] + " = " + id, null, null, null, null);
		if(c.getCount() > 0)
		{
			c.moveToFirst();
			Alergia A = alergiaFactory(c);
			c.close();
			return A;
		}
		c.close();
		return null;
	}
	
	/**
	 * Busca Alergias por Category
	 * @param idCategoria
	 * @return List<Alergia>
	 */
	public List<Alergia> buscarPorCategoria(int idCategoria)
	{
		List<Alergia> lista = new ArrayList<Alergia>();
		
		Cursor c = db.query(DAO.TBL_ALERGIA, null, FIELD_ALERGIA[3] + " = " + idCategoria, null, null, null, null);
		// null para colunas, trar� todas!
		
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			Alergia A = alergiaFactory(c);
			
			c.moveToNext();
			lista.add(A);
		}
		c.close();
		return lista;
	}
	
	/**
	 * Busca uma Alergia por nome
	 * @param alergia
	 * @return Alergia
	 */
	public Alergia buscarNome(Alergia alergia)
	{
		
		Cursor c = db.query(DAO.TBL_ALERGIA, null, FIELD_ALERGIA[1] + " = '" + alergia.getNome() + "'", null, null, null, null);

		if(c.getCount() > 0)
		{
			c.moveToFirst();
			Alergia d = alergiaFactory(c);
			c.close();
			return d;
		}
		c.close();
		return null;
	}

	/**
	 * Verifica se existe outra Alergia com o mesmo nome da Alergia recebida.
	 * @param alergia
	 * @return Alergia
	 */
	public Alergia buscarNomeDuplicado(Alergia alergia)
	{
		
		Cursor c = db.query(DAO.TBL_ALERGIA, null, FIELD_ALERGIA[1] + " = '" + alergia.getNome() + "' AND " + FIELD_ALERGIA[0] + " <> " + alergia.getId_alergia(), null, null, null, null);

		if(c.getCount() > 0)
		{
			c.moveToFirst();
			Alergia d = alergiaFactory(c);
			c.close();
			return d;
		}
		c.close();
		return null;
	}	
	
	/**
	 * Deleta uma Alergia, recebendo o id
	 * @param idAlergia
	 * @return int, -1 para falha
	 */
	public int delete(int idAlergia)
	{
		
		Integer retorno = 0;
		
		try
		{
			
			db.beginTransaction();
			db.delete(DAO.TBL_ALERGIA, FIELD_ALERGIA[0] + " = ?", new String[]{String.valueOf(idAlergia)});
			db.setTransactionSuccessful();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			retorno = -1;
		}
		finally
		{
			db.endTransaction();
		}
		return retorno;
	}
	
	/**
	 * Atualiza os dados de uma Alergia
	 * @param alergia
	 * @return int, -1 para falha
	 */
	public int update(Alergia alergia)
	{
		
		int retorno = 0;
		
		try
		{
			db.beginTransaction();
			//Montando os dados
			ContentValues cv = new ContentValues();
			cv.put(FIELD_ALERGIA[0], alergia.getId_alergia());
			cv.put(FIELD_ALERGIA[1], alergia.getNome());
			cv.put(FIELD_ALERGIA[2], alergia.getObs());
			cv.put(FIELD_ALERGIA[3], alergia.getId_categoria());
			
			db.update(DAO.TBL_ALERGIA, cv, FIELD_ALERGIA[0] + " = ?", new String[]{alergia.getId_alergia().toString()});
			db.setTransactionSuccessful();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			retorno = -1;
		}
		finally
		{
			db.endTransaction();
		}
		
		return retorno;
	}
}