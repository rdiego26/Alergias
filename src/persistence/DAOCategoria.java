package persistence;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import entity.Alergia;
import entity.Categoria;

public class DAOCategoria {

	//Nome dos Campos da Tabela
	private static final String[] FIELD_CATEGORIA = {"id_categoria", "nome"};
	protected SQLiteDatabase db;
	private static DAOCategoria instance = new DAOCategoria(); // Padrão Singleton	
	
	//Métodos
	public static DAOCategoria getInstance(Context ctx)
	{
		if(instance.db == null || !instance.db.isOpen())
		{
			instance.db = new DAO(ctx).getWritableDatabase(); //Permite a comunicação com o banco
		}
		return instance;
	}
	
	
	public long save(Categoria C)
	{
		long retorno = -1; //Criado pois o INSERT sempre retorna o id, depois de criado.
		
		db.beginTransaction();
		
		try
		{
			ContentValues cv = new ContentValues();
			//Setando valores para os campos
			
			//cv.put(FIELD_CATEGORIA[0], C.getId_categoria()); //id_categoria
			cv.put(FIELD_CATEGORIA[1], C.getNome()); //nome
			
			//Grava efetivamente os dados
			retorno = db.insert(DAO.TBL_CATEGORIA, null, cv);
		
			db.setTransactionSuccessful();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//Log.e("DAOCategoria", e.getMessage());
		}
		finally
		{
			db.endTransaction();
		}
		
		return retorno;
	}	
	
	private Categoria carregarCategoria(Cursor c)
	{
		Integer idCategoria = c.getInt(c.getColumnIndex(FIELD_CATEGORIA[0]));
		String  nome     =  c.getString(c.getColumnIndex(FIELD_CATEGORIA[1]));

		Categoria C = new Categoria(idCategoria, nome);//Cria objeto com os dados obtidos
				
		return C;
	}
	
	public List<Categoria> findAll()
	{
		List<Categoria> lista = new ArrayList<Categoria>();
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, null, null, null, null, "nome ASC");
		// null para colunas, trará todas!
		
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			Categoria C = carregarCategoria(c);
			
			c.moveToNext();
			lista.add(C);
		}
		c.close();
		return lista;
	}
	
	public List<String> findAllNome()
	{
		List<String> lista = new ArrayList<String>();
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, null, null, null, null, "nome ASC");
		// null para colunas, trará todas!
		
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			Categoria C = carregarCategoria(c);
			
			c.moveToNext();
			lista.add(C.getNome());
		}
		c.close();
		return lista;
	}	

	public List<String> findAllId()
	{
		List<String> lista = new ArrayList<String>();
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, null, null, null, null, "nome ASC");
		// null para colunas, trará todas!
		
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			Categoria C = carregarCategoria(c);
			
			c.moveToNext();
			lista.add(C.getId_categoria().toString());
		}
		c.close();
		return lista;
	}	
	
	
	public Categoria buscar(int id)
	{
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, FIELD_CATEGORIA[0] + " = " + id, null, null, null, null);
		if(c.getCount() > 0)
		{
			c.moveToFirst();
			Categoria C = carregarCategoria(c);
			c.close();
			return C;
		}
		c.close();
		return null;
	}	
	
	public Categoria buscarNome(Categoria C)
	{
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, FIELD_CATEGORIA[1] + " = '" + C.getNome() + "'", null, null, null, null);

		if(c.getCount() > 0)
		{
			c.moveToFirst();
			Categoria ca = carregarCategoria(c);
			c.close();
			return ca;
		}
		c.close();
		return null;
	}	
	
	public Integer delete(int id, Context ctx)
	{
		
		Integer retorno = 0;
		
		//Valida a consistência do banco
		DAOAlergia DAOA;
		DAOA = DAOAlergia.getInstance(ctx);
		
		List<Alergia> lista;
		
		lista = DAOA.buscarPorCategoria(id);
		//Encontrou alguma Alergia que utilize a Categoria?
		if(lista.size() > 0) //Aqui encontrou
		{
			retorno = -1;
		}
		else
		{
			try
			{
				
				db.beginTransaction();
				db.delete(DAO.TBL_CATEGORIA, FIELD_CATEGORIA[0] + " = ?", new String[]{String.valueOf(id)});
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
			
		}

		return retorno;
	}	
	
	public int update(Categoria ca)
	{
		
		int retorno = 0;
		
		try
		{
			db.beginTransaction();
			//Montando os dados
			ContentValues cv = new ContentValues();
			cv.put(FIELD_CATEGORIA[0], ca.getId_categoria());
			cv.put(FIELD_CATEGORIA[1], ca.getNome());

			
			db.update(DAO.TBL_CATEGORIA, cv, FIELD_CATEGORIA[0] + " = ?", new String[]{ca.getId_categoria().toString()});
			db.setTransactionSuccessful();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			//Log.i("DAOCategoria->Update", e.getMessage());
			retorno = -1;
		}
		finally
		{
			db.endTransaction();
		}
		
		return retorno;
	}	
	
	
	
}
