package alergias.activity;

import info.diegoramos.alergias.R;
import alergias.Utils.ToastManager;
import alergias.entity.Categoria;
import alergias.persistence.DAOCategoria;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class ListarCategoria extends Activity
{

	DAOCategoria DAOC;
	String conteudo[];
	List<Categoria> lista;
	private ListView listV;
	private int posicao;

	//Mensagens
	String msg_lista_categoria_vazia;
	String msgError = null;
	String msgSucess = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listar_categoria);
		
		listV = (ListView) findViewById(R.id.listar_categoria_lista);
		
		//Simple tap in ListView item
		listV.setOnItemClickListener(new AdapterView.OnItemClickListener()  {

			@Override
			public void onItemClick(AdapterView<?> parent, View wv, int position,
					long id) {
				Categoria c = (Categoria) lista.get(position);
    			Intent intent = new Intent(getApplicationContext(), DetalhesCategoria.class);
    			Bundle param = new Bundle();
    			
    			param.putSerializable("_object_categoria", c);
    			intent.putExtras(param);
    			startActivity(intent);
			}
		});		
		
		DAOC = DAOCategoria.getInstance(this); // trabalhando com SINGLETON
		
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
		
		ListView lv = (ListView) findViewById(R.id.listar_categoria_lista);
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
    	Categoria categoria;
    	
    	switch (item.getItemId()) {
    	
    	case R.id.context_menu_register:
    			startActivity(new Intent(this, CadastrarCategoria.class));
    		break;
    	
    	case R.id.context_menu_delete:
    			categoria = (Categoria) lista.get(posicao);
    			msgError = this.getString(R.string.lbl_falha_exclusao_categoria);
    			msgSucess = this.getString(R.string.lbl_sucesso_exclusao_categoria);
    			
    			if ( DAOC.delete(categoria.getId_categoria(), getApplicationContext()) == -1 ) {
    				ToastManager.show(getApplicationContext(), msgError, 2);
    			}
    			else {
    				ToastManager.show(getApplicationContext(), msgSucess, 0);
    				loadList();
    			}
    			
    			break;
    	case R.id.context_menu_update:
    			categoria = (Categoria) lista.get(posicao);
    			Intent intent = new Intent(getApplicationContext(), DetalhesCategoria.class);
    			Bundle param = new Bundle();
    			
    			param.putSerializable("_object_categoria", categoria);
    			intent.putExtras(param);
    			startActivity(intent);    			
    			
    			break;
    			
    	default:
				break;
		}
    	
    	return true;
    }    
	
	
	
}
