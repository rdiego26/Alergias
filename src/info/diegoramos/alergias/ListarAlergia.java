package info.diegoramos.alergias;


import info.diegoramos.alergias.R;
import info.diegoramos.alergias.Utils.ToastManager;
import info.diegoramos.alergias.entity.Alergia;
import info.diegoramos.alergias.persistence.DAOAlergia;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class ListarAlergia extends Activity
{

	DAOAlergia daoA;
	String conteudo[];
	List<Alergia> lista;
	private ListView listV;
	private int posicao;	

	//Mensagens
	String msg_lista_alergia_vazia;
	String msgError = null;
	String msgSucess = null;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listar_alergia);
		
		listV = (ListView) findViewById(R.listar_alergia.lista);
		
		//Simple tap in ListView item
		listV.setOnItemClickListener(new AdapterView.OnItemClickListener()  {

			@Override
			public void onItemClick(AdapterView<?> parent, View wv, int position,
					long id) {
				Alergia a = (Alergia) lista.get(position);
    			Intent intent = new Intent(getApplicationContext(), DetalhesAlergia.class);
    			Bundle param = new Bundle();
    			
    			param.putSerializable("_object_alergia", a);
    			intent.putExtras(param);
    			startActivity(intent);
			}
		});			
		
		
		
		daoA = DAOAlergia.getInstance(this); // trabalhando com SINGLETON
		
		registerForContextMenu(listV);
		loadList();
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		loadList();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		loadList();
	}
	
	@Override
	protected void onRestart()
	{
		super.onRestart();
		loadList();
	}
	
	/**
	 * Preenche a ListView
	 */
	public void loadList()
	{
		
		ListView lv = (ListView) findViewById(R.listar_alergia.lista);
		lista = daoA.findAll();
		
		//Setando Mensagens
		msg_lista_alergia_vazia = this.getString(R.string.lbl_cabecalho_lst_vazia_alergia);		
		
		
		StringBuilder sb = new StringBuilder();

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
			conteudo = sb.toString().split("<>");
			ArrayAdapter<String> addAlergia = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, conteudo);
			lv.setAdapter(addAlergia);
			ToastManager.show(getApplicationContext(), msg_lista_alergia_vazia, 0);

		}
	
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
    
    //ContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View vw, ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, vw, menuInfo);
    	getMenuInflater().inflate(R.menu.context_menu, menu);
    	
    	/* Position selected on ContextMenu */
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
    	posicao = info.position;
    	
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	Alergia alergia;
    	
    	switch (item.getItemId()) {
    	
    	case R.id.context_menu_register:
    			startActivity(new Intent(this, CadastrarAlergia.class));
    		break;
    	
    	case R.id.context_menu_delete:
    			alergia = (Alergia) lista.get(posicao);
    			msgError = this.getString(R.string.lbl_falha_exclusao_alergia);
    			msgSucess = this.getString(R.string.lbl_sucesso_exclusao_alergia);
    			
    			if ( daoA.delete(alergia.getId_alergia()) == -1 ) {
    				ToastManager.show(getApplicationContext(), msgError, 2);
    			}
    			else {
    				ToastManager.show(getApplicationContext(), msgSucess, 0);
    				loadList();
    			}
    			
    			break;
    	case R.id.context_menu_update:
    			alergia = (Alergia) lista.get(posicao);
    			Intent intent = new Intent(getApplicationContext(), DetalhesAlergia.class);
    			Bundle param = new Bundle();
    			
    			param.putSerializable("_object_alergia", alergia);
    			intent.putExtras(param);
    			startActivity(intent);    			
    			
    			break;
    			
    	default:
				break;
		}
    	
    	return true;
    }        
    
    
    
}
