package alergias.persistence;

import alergias.entity.Alergia;
import alergias.entity.Category;

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
public class DAOCategory {

	//Nome dos Campos da Tabela
	private static final String[] FIELD_CATEGORIA = {"id_categoria", "nome"};
	protected SQLiteDatabase db;
	private static DAOCategory instance = new DAOCategory(); /** Singleton **/
	
	/**
	 * Método criado para trabalhar com Singleton
	 * @param context
	 * @return Instância de DAOCategory
	 */
	public static DAOCategory getInstance(Context context)
	{
		if(instance.db == null || !instance.db.isOpen())
		{
			instance.db = new DAO(context).getWritableDatabase(); //Permite a comunica��o com o banco
		}
		return instance;
	}
	
	/**
	 * Persiste uma Category
	 * @param category
	 * @return int, -1 para falhas
	 */
	public int save(Category category)
	{
		int retorno = -1; //Criado pois o INSERT sempre retorna o id, depois de criado.
		
		db.beginTransaction();
		
		try
		{
			ContentValues cv = new ContentValues();
			//Setando valores para os campos
			
			//cv.put(FIELD_CATEGORIA[0], C.getId_categoria()); //id_categoria
			cv.put(FIELD_CATEGORIA[1], category.getNome()); //nome
			
			//Grava efetivamente os dados
			retorno = (int) db.insert(DAO.TBL_CATEGORIA, null, cv);
		
			db.setTransactionSuccessful();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Log.e("DAOCategory->save()", e.getMessage());
		}
		finally
		{
			db.endTransaction();
		}
		
		return retorno;
	}	
	
	/**
	 * Constroe uma Category através de Cursor ( consulta )
	 * @param cursor
	 * @return Category
	 */
	private Category categoriaFactory(Cursor cursor)
	{
		Integer idCategoria = cursor.getInt(cursor.getColumnIndex(FIELD_CATEGORIA[0]));
		String  nome     =  cursor.getString(cursor.getColumnIndex(FIELD_CATEGORIA[1]));

		Category C = new Category(idCategoria, nome);//Cria objeto com os dados obtidos
				
		return C;
	}
	
	/**
	 * Retorna todas Categorias cadastradas, ordenadas pelo nome
	 * @return List<Category>
	 */
	public List<Category> findAll()
	{
		List<Category> lista = new ArrayList<Category>();
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, null, null, null, null, "nome ASC");
		
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			Category C = categoriaFactory(c);
			
			c.moveToNext();
			lista.add(C);
		}
		c.close();
		return lista;
	}

	/**
	 * Retorna o nome de todas Categorias, ordenado pelo nome 
	 * @return List<String>
	 */
	public List<String> findAllNome()
	{
		List<String> lista = new ArrayList<String>();
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, null, null, null, null, "nome ASC");
		
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			Category C = categoriaFactory(c);
			
			c.moveToNext();
			lista.add(C.getNome());
		}
		c.close();
		return lista;
	}	

	/**
	 * Retorna todos ids das Categorias, ordenados pelo nome
	 * @return List<String>
	 */
	public List<String> findAllId()
	{
		List<String> lista = new ArrayList<String>();
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, null, null, null, null, "nome ASC");
		// null para colunas, trar� todas!
		
		c.moveToFirst();
		while(!c.isAfterLast())
		{
			Category C = categoriaFactory(c);
			
			c.moveToNext();
			lista.add(C.getId_categoria().toString());
		}
		c.close();
		return lista;
	}	
	
	/**
	 * Busca uma Category pelo id
	 * @param idCategoria
	 * @return Category
	 */
	public Category buscar(int idCategoria)
	{
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, FIELD_CATEGORIA[0] + " = " + idCategoria, null, null, null, null);
		if(c.getCount() > 0)
		{
			c.moveToFirst();
			Category C = categoriaFactory(c);
			c.close();
			return C;
		}
		c.close();
		return null;
	}	
	
	/**
	 * Busca Category pelo nome
	 * @param category
	 * @return Category
	 */
	public Category buscarNome(Category category)
	{
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, FIELD_CATEGORIA[1] + " = '" + category.getNome() + "'", null, null, null, null);

		if(c.getCount() > 0)
		{
			c.moveToFirst();
			Category ca = categoriaFactory(c);
			c.close();
			return ca;
		}
		c.close();
		return null;
	}	
	
	/**
	 * Deleta uma Category
	 * @param idCategoria
	 * @param context
	 * @return -1 para falhas
	 */
	public int delete(int idCategoria, Context context)
	{
		
		int retorno = 0;
		
		//Valida a consist�ncia do banco
		DAOAllergy DAOA;
		DAOA = DAOAllergy.getInstance(context);
		
		List<Alergia> lista;
		
		lista = DAOA.buscarPorCategoria(idCategoria);
		//Encontrou alguma Alergia que utilize a Category?
		if(lista.size() > 0) //Aqui encontrou
		{
			retorno = -1;
		}
		else
		{
			try
			{
				
				db.beginTransaction();
				db.delete(DAO.TBL_CATEGORIA, FIELD_CATEGORIA[0] + " = ?", new String[]{String.valueOf(idCategoria)});
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
	
	/**
	 * Atualiza uma Category
	 * @param category
	 * @return -1 para falhas
	 */
	public int update(Category category)
	{
		
		int retorno = 0;
		
		try
		{
			db.beginTransaction();
			//Montando os dados
			ContentValues cv = new ContentValues();
			cv.put(FIELD_CATEGORIA[0], category.getId_categoria());
			cv.put(FIELD_CATEGORIA[1], category.getNome());

			
			db.update(DAO.TBL_CATEGORIA, cv, FIELD_CATEGORIA[0] + " = ?", new String[]{category.getId_categoria().toString()});
			db.setTransactionSuccessful();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.i("DAOCategory->Update", e.getMessage());
			retorno = -1;
		}
		finally
		{
			db.endTransaction();
		}
		
		return retorno;
	}	
	
	/**
	 * Busca Category pelo id
	 * @param idCategoria
	 * @return Category
	 */
	public Category getById(int idCategoria) {
		Category ct = null;
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, FIELD_CATEGORIA[0] + " = " + String.valueOf(idCategoria), null, null, null, "nome ASC LIMIT 1");
	
		if(c.moveToFirst()){

			ct = categoriaFactory(c);
			c.close();
			
			return ct;			
		}
			c.close();
		
		return ct;
	}	
	
	/**
	 * Busca Category pelo nome
	 * @param name
	 * @return Category
	 */
	public Category getByName(String name) {
		Category category = null;
		
		Cursor c = db.query(DAO.TBL_CATEGORIA, null, FIELD_CATEGORIA[1] + " = '" + name + "' ", null, null, null, "nome ASC LIMIT 1");
	
		if(c.moveToFirst()){

			category = categoriaFactory(c);
			c.close();
			
			return category;
		}
			c.close();
		
		return category;
	}	
	
	
}
