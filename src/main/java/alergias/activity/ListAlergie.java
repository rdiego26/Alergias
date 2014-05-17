package alergias.activity;


import alergias.persistence.DAOAllergy;
import info.diegoramos.alergiass.R;
import alergias.util.ToastManager;
import alergias.entity.Alergia;

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
public class ListAlergie extends Activity
{

    private DAOAllergy daoA;
    private String content[];

    private List<Alergia> listAllergy;
	private ListView listV;
	private int position;

	//Messages
    private String EMPTY_LIST_MSG;
    private String ERROR_MSG = null;
    private String SUCCESS_MSG = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listar_alergia);
		
		listV = (ListView) findViewById(R.id.listar_alergia_lista);
		
		//Simple tap in ListView item
		listV.setOnItemClickListener(new AdapterView.OnItemClickListener()  {

			@Override
			public void onItemClick(AdapterView<?> parent, View wv, int position, long id) {
				Alergia allergy = (Alergia) listAllergy.get(position);
    			Intent intent = new Intent(getApplicationContext(), DetalhesAlergia.class);
    			Bundle param = new Bundle();
    			
    			param.putSerializable("_object_alergia", allergy);
    			intent.putExtras(param);
    			startActivity(intent);
			}
		});			
		
		daoA = DAOAllergy.getInstance(this); //SINGLETON
		
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
		
		ListView lv = (ListView) findViewById(R.id.listar_alergia_lista);
		listAllergy = daoA.findAll();
		
		//Setando Mensagens
		EMPTY_LIST_MSG = this.getString(R.string.lbl_cabecalho_lst_vazia_alergia);
		
		
		StringBuilder sb = new StringBuilder();

		if(listAllergy.size() > 0)
		{
			for(Alergia A : listAllergy)
			{
				sb.append(A.getNome()  + "<>"); //TAG "<>" utilizada para quebra os nomes
			}
			content = sb.toString().split("<>");
			ArrayAdapter<String> addAlergia = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, content);
			lv.setAdapter(addAlergia);
		}
		else
		{
			ToastManager.show(getApplicationContext(), EMPTY_LIST_MSG, 0);
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
    		case R.id.menu_listar_alergia_cadastrar:
    			intencao = new Intent(this, RegisterAllergy.class);
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
    	position = info.position;
    	
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	Alergia alergia;
    	
    	switch (item.getItemId()) {
    	
    	case R.id.context_menu_register:
    			startActivity(new Intent(this, RegisterAllergy.class));
    		break;
    	
    	case R.id.context_menu_delete:
    			alergia = (Alergia) listAllergy.get(position);
    			ERROR_MSG =  getString(R.string.lbl_falha_exclusao_alergia);
    			SUCCESS_MSG = getString(R.string.lbl_sucesso_exclusao_alergia);
    			
    			if ( daoA.delete(alergia.getId_alergia()) == -1 ) {
    				ToastManager.show(getApplicationContext(), ERROR_MSG, 2);
    			}
    			else {
    				ToastManager.show(getApplicationContext(), SUCCESS_MSG, 0);
                    loadList();
    			}
    			
    			break;
    	case R.id.context_menu_update:
    			alergia = (Alergia) listAllergy.get(position);
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