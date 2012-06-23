package persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import entity.Alergia;

public class DAOAlergia {

	//Nome dos Campos da Tabela
	private static final String[] FIELD_ALERGIA = {"id_alergia", "nome", "obs", "id_categoria"};
	protected SQLiteDatabase db;
	private static DAOAlergia instance = new DAOAlergia(); // Padrão Singleton	
	
	
	
	//Métodos
	public static DAOAlergia getInstance(Context ctx)
	{
		if(instance.db == null || !instance.db.isOpen())
		{
			instance.db = new DAO(ctx).getWritableDatabase(); //Permite a comunicação com o banco
		}
		return instance;
	}
	
	
	public long save(Alergia A)
	{
		long retorno = -1; //Criado pois o INSERT sempre retorna o id, depois de criado.
		
		db.beginTransaction();
		
		try
		{
			ContentValues cv = new ContentValues();
			//Setando valores para os campos
			
			//cv.put(FIELD_ALERGIA[0], A.getId_alergia()); //id_alergia
			cv.put(FIELD_ALERGIA[1], A.getNome()); //nome
			cv.put(FIELD_ALERGIA[2], A.getObs()); //obs
			cv.put(FIELD_ALERGIA[3], A.getId_categoria()); //id_categoria

			
			//Grava efetivamente os dados
			retorno = db.insert(DAO.TBL_ALERGIA, null, cv);
		
			db.setTransactionSuccessful();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//Log.e("DAOAlergia", e.getMessage());
		}
		finally
		{
			db.endTransaction();
		}
		
		return retorno;
	}
	
	private Alergia carregarAlergia(Cursor c)
	{
		Integer idAlergia = c.getInt(c.getColumnIndex(FIELD_ALERGIA[0]));
		String  nome     =  c.getString(c.getColumnIndex(FIELD_ALERGIA[1]));
		String  Obs       = c.getString(c.getColumnIndex(FIELD_ALERGIA[2]));
		Integer idCategoria  = c.getInt(c.getColumnIndex(FIELD_ALERGIA[3]));

		Alergia A = new Alergia(idAlergia, nome, Obs, idCategoria);//Cria objeto com os dados obtidos
				
		return A;
	}
	
	public List<Alergia> findAll()
	{
		List<Alergia> lista = new ArrayList<Alergia>();
		
		Cursor c = db.query(DAO.TBL_ALERGIA, null, null, null, null, null, "nome ASC");
		// null para colunas, trará todas!
		
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			Alergia A = carregarAlergia(c);
			
			c.moveToNext();
			lista.add(A);
		}
		c.close();
		return lista;
	}
	
	public Alergia buscar(int id)
	{
		
		Cursor c = db.query(DAO.TBL_ALERGIA, null, FIELD_ALERGIA[0] + " = " + id, null, null, null, null);
		if(c.getCount() > 0)
		{
			c.moveToFirst();
			Alergia A = carregarAlergia(c);
			c.close();
			return A;
		}
		c.close();
		return null;
	}
	
	public List<Alergia> buscarPorCategoria(int id)
	{
		List<Alergia> lista = new ArrayList<Alergia>();
		
		Cursor c = db.query(DAO.TBL_ALERGIA, null, FIELD_ALERGIA[3] + " = " + id, null, null, null, null);
		// null para colunas, trará todas!
		
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			Alergia A = carregarAlergia(c);
			
			c.moveToNext();
			lista.add(A);
		}
		c.close();
		return lista;
	}
	
	
	
	
	public Alergia buscarNome(Alergia A)
	{
		
		Cursor c = db.query(DAO.TBL_ALERGIA, null, FIELD_ALERGIA[1] + " = '" + A.getNome() + "'", null, null, null, null);

		if(c.getCount() > 0)
		{
			c.moveToFirst();
			Alergia d = carregarAlergia(c);
			c.close();
			return d;
		}
		c.close();
		return null;
	}

	public Alergia buscarNomeDuplicado(Alergia A)
	{
		
		Cursor c = db.query(DAO.TBL_ALERGIA, null, FIELD_ALERGIA[1] + " = '" + A.getNome() + "' AND " + FIELD_ALERGIA[0] + " <> " + A.getId_alergia(), null, null, null, null);

		if(c.getCount() > 0)
		{
			c.moveToFirst();
			Alergia d = carregarAlergia(c);
			c.close();
			return d;
		}
		c.close();
		return null;
	}	
	
	
	public Integer delete(int id)
	{
		
		Integer retorno = 0;
		
		try
		{
			
			db.beginTransaction();
			db.delete(DAO.TBL_ALERGIA, FIELD_ALERGIA[0] + " = ?", new String[]{String.valueOf(id)});
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
	
	public int update(Alergia A)
	{
		
		int retorno = 0;
		
		try
		{
			db.beginTransaction();
			//Montando os dados
			ContentValues cv = new ContentValues();
			cv.put(FIELD_ALERGIA[0], A.getId_alergia());
			cv.put(FIELD_ALERGIA[1], A.getNome());
			cv.put(FIELD_ALERGIA[2], A.getObs());
			cv.put(FIELD_ALERGIA[3], A.getId_categoria());
			
			db.update(DAO.TBL_ALERGIA, cv, FIELD_ALERGIA[0] + " = ?", new String[]{A.getId_alergia().toString()});
			db.setTransactionSuccessful();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			//Log.i("DAOALergia->Update", e.getMessage());
			retorno = -1;
		}
		finally
		{
			db.endTransaction();
		}
		
		return retorno;
	}
	
}
