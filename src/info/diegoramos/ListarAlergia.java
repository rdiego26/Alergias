package info.diegoramos;


import info.diegoramos.R;
import java.util.List;

import persistence.DAOAlergia;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import entity.Alergia;


public class ListarAlergia extends Activity
{

	DAOAlergia DAOA;
	String conteudo[];
	List<Alergia> lista;

	//Mensagens
	String msg_lista_alergia_vazia;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listar_alergia);
		
		DAOA = DAOAlergia.getInstance(this); // trabalhando com SINGLETON
		//adidionarLista(getApplicationContext());
		adidionarLista();
	}
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//adidionarLista(getApplicationContext());
		adidionarLista();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		//adidionarLista(getApplicationContext());
		adidionarLista();
	}
	
	@Override
	protected void onRestart()
	{
		// TODO Auto-generated method stub
		super.onRestart();
		//adidionarLista(getApplicationContext());
		adidionarLista();
	}
	
	//Recebe contexto para conseguir mostrar a mensagem de lista vazia com êxito
	public void adidionarLista()
	{
		
		ListView lv = (ListView) findViewById(R.listar_alergia.lista);
		lista = DAOA.findAll();
		
		//Setando Mensagens
		msg_lista_alergia_vazia = this.getString(R.string.lbl_cabecalho_lst_vazia_alergia);		
		
		
		StringBuilder sb = new StringBuilder();

		//Log.i("ListarAlergia, quantidade cadastrada: ", String.valueOf(lista.size()));
		
		if(lista.size() > 0)
		{
			for(Alergia A : lista)
			{
				sb.append(A.getNome()  + "<>"); //TAG "<>" utilizada para quebra os nomes
			}
			conteudo = sb.toString().split("<>");
			ArrayAdapter<String> addAlergia = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, conteudo);
			lv.setAdapter(addAlergia);
		}
		else
		{
			Toast.makeText(getApplicationContext(), msg_lista_alergia_vazia, Toast.LENGTH_SHORT).show();

		}
		
		//Tratando o clique normal no item da lista
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id)
			{
				
				Alergia A = lista.get(position);

				
				Intent intencao = new Intent(v.getContext(), DetalhesAlergia.class);
				Bundle param = new Bundle();
				param.putString("id_alergia", A.getId_alergia().toString());
				
				intencao.putExtras(param);
				startActivity(intencao);
				
			}
		});
	
	}

    //Colocando o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu_listar_alergia, menu);
   	
    	return true;
    }
	

    //Tratando item selecionado no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	// TODO Auto-generated method stub
    	Intent intencao;
    	
    	//Verificando qual item do menu foi selecionado
    	switch (item.getItemId())
    	{
    		case R.menu_listar_alergia.cadastrar:
    			intencao = new Intent(this, CadastrarAlergia.class);
    			startActivity(intencao);
    			return true;
    	
    		default:
    			return false;
    			
    	}
    	
    }
    
    
    
}
