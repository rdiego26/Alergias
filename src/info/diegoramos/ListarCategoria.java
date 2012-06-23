package info.diegoramos;

import info.diegoramos.R;
import java.util.List;

import persistence.DAOCategoria;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import entity.Categoria;

public class ListarCategoria extends Activity
{

	DAOCategoria DAOC;
	String conteudo[];
	List<Categoria> lista;	

	//Mensagens
	String msg_lista_categoria_vazia;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listar_categoria);
		
		DAOC = DAOCategoria.getInstance(this); // trabalhando com SINGLETON
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
		
		ListView lv = (ListView) findViewById(R.listar_categoria.lista);
		lista = DAOC.findAll();
		
		
		//Setando Mensagens
		msg_lista_categoria_vazia = this.getString(R.string.lbl_cabecalho_lst_vazia_categoria);
		
		StringBuilder sb = new StringBuilder();

		
		if(lista.size() > 0)
		{
			for(Categoria C : lista)
			{
				sb.append(C.getNome()  + "<>"); //TAG "<>" utilizada para quebra os nomes
			}
			conteudo = sb.toString().split("<>");
			ArrayAdapter<String> addCategoria = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, conteudo);
			
			lv.setAdapter(addCategoria);
		}
		else
		{
			Toast.makeText(getApplicationContext(), msg_lista_categoria_vazia, Toast.LENGTH_SHORT).show();

		}
		
		//Tratando o clique normal no item da lista
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id)
			{
				
				Categoria C = lista.get(position);

				
				Intent intencao = new Intent(v.getContext(), DetalhesCategoria.class);
				Bundle param = new Bundle();
				param.putString("id_categoria", C.getId_categoria().toString());
				
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
    	inflater.inflate(R.menu.menu_listar_categoria, menu);
   	
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
    		case R.menu_listar_categoria.cadastrar:
    			intencao = new Intent(this, CadastrarCategoria.class);
    			startActivity(intencao);
    			return true;
    	
    		default:
    			return false;
    			
    	}
    	
    }	
	
	
	
}
