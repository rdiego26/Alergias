package info.diegoramos;

import info.diegoramos.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import Utils.*;
import persistence.DAO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewDebug.ExportedProperty;

public class TelaInicial extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        setContentView(R.layout.principal);
        
    }
    
    /*
	private boolean gravarArquivo(arquivo a, Context ctx)
	{
        try
        {
	        	FileOutputStream fos = null;
	            try
	            {
	            		//File f = new File(Environment.getExternalStorageDirectory()+"/"+a.getNome());
	            		//File f = new File(Environment.getDataDirectory()+"/"+a.getNome());
	            		
	            		//FileOutputStream out = new FileOutputStream(f);
	            		FileOutputStream out = this.openFileOutput(a.getNome(), ctx.MODE_APPEND);
		                //out.write(a.getConteudo());
		                out.write("\n".getBytes());
		                out.flush();
		                out.close();
		         }
	            catch (FileNotFoundException e)
	            {
		                e.printStackTrace();
		                return false;
		        }
	            catch (IOException e)
	            {
		                e.printStackTrace();
		                return false;
		        }
	            finally
	            {
	            	
		        }
       }
        catch (Exception e)
        {
        	System.out.println(e.toString());
        	return false;
		}

		return true;
	}
    */
    
    //Colocando o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu_principal, menu);
   	
    	return true;
    }
    
    
    
    //Tratando item selecionado no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	Intent intencao;
    	
    	//Verificando qual item do menu foi selecionado
    	switch (item.getItemId())
    	{
		case R.menu_principal.menu_categoria:
			intencao = new Intent(getApplicationContext(), ListarCategoria.class);
			startActivity(intencao);
			return true;
    	    	
    	case R.menu_principal.menu_alergia:
    			intencao = new Intent(getApplicationContext(), ListarAlergia.class);
    			startActivity(intencao);
    			return true;
    			
    	case R.menu_principal.menu_sugestao:
			intencao = new Intent(getApplicationContext(), EnviarSugestao.class);
			startActivity(intencao);
			return true;
    			
    	
    		default:
    			return false;
    			
    	}
    	
    }
}