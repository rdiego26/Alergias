package alergias.activity;

import alergias.entity.Category;
import info.diegoramos.alergiass.R;
import alergias.util.ToastManager;
import alergias.persistence.DAOCategory;

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

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class ListCategory extends Activity
{

	private DAOCategory DAOC;
    private String content[];

    private List<Category> listCategory;
    private ListView listView;
	private int position;

	//Messages
    private String EMPTY_LIST_MSG;
    private String ERROR_MSG = null;
    private String SUCCESS_MSG = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_category);
		
		listView = (ListView) findViewById(R.id.listar_categoria_lista);
		
		//Simple tap in ListView item
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View wv, int position, long id) {
                Category category = (Category) listCategory.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailsCategory.class);
                Bundle param = new Bundle();

                param.putSerializable("_object_categoria", category);
                intent.putExtras(param);
                startActivity(intent);
            }
        });
		
		DAOC = DAOCategory.getInstance(this); // SINGLETON
		
		registerForContextMenu(listView);
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
	 * populate ListView
	 */
	public void loadList()
	{
		
		ListView lv = (ListView) findViewById(R.id.listar_categoria_lista);
		listCategory = DAOC.findAll();

		EMPTY_LIST_MSG = this.getString(R.string.lbl_cabecalho_lst_vazia_categoria);
		
		StringBuilder sb = new StringBuilder();

		
		if(listCategory.size() > 0)
		{
			for(Category C : listCategory)
			{
				sb.append(C.getNome()  + "<>"); //TAG "<>" utilizada para quebra os nomes
			}
			content = sb.toString().split("<>");
			ArrayAdapter<String> addCategoria = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, content);
			
			lv.setAdapter(addCategoria);
		}
		else
		{
            ToastManager.show(getApplicationContext(), EMPTY_LIST_MSG, 0);
		}
	
	}	

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
    	Intent intent;
    	
    	//Verificando qual item do menu foi selecionado
    	switch (item.getItemId())
    	{
    		case R.id.menu_listar_categoria_cadastrar:
    			intent = new Intent(this, RegisterCategory.class);
    			startActivity(intent);
    			return true;
    	
    		default:
    			return false;
    			
    	}
    	
    }
    
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
    	Category category;
    	
    	switch (item.getItemId()) {
    	
    	case R.id.context_menu_register:
    			startActivity(new Intent(this, RegisterCategory.class));
    		break;
    	
    	case R.id.context_menu_delete:
    			category = (Category) listCategory.get(position);
    			ERROR_MSG = this.getString(R.string.lbl_falha_exclusao_categoria);
    			SUCCESS_MSG = this.getString(R.string.lbl_sucesso_exclusao_categoria);
    			
    			if ( DAOC.delete(category.getId_categoria(), getApplicationContext()) == -1 ) {
    				ToastManager.show(getApplicationContext(), ERROR_MSG, 2);
    			}
    			else {
    				ToastManager.show(getApplicationContext(), SUCCESS_MSG, 0);
    				loadList();
    			}
    			
    			break;
    	case R.id.context_menu_update:
    			category = (Category) listCategory.get(position);
    			Intent intent = new Intent(getApplicationContext(), DetailsCategory.class);
    			Bundle param = new Bundle();
    			
    			param.putSerializable("_object_categoria", category);
    			intent.putExtras(param);
    			startActivity(intent);    			
    			
    			break;
    			
    	default:
				break;
		}
    	
    	return true;
    }    
	
	
	
}
