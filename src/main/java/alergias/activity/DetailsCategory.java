package alergias.activity;

import alergias.entity.Category;
import alergias.persistence.DAOCategory;
import alergias.util.Validation;
import info.diegoramos.alergiass.R;
import alergias.util.ToastManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class DetailsCategory extends Activity
{
	private Category category;
    private DAOCategory daoC;
	
	//Components
    private EditText edtName;

	//Messages
    private String DUPLICATE_CATEGORY_MSG;
    private String SUCCESS_UPDATE_MSG, FAIL_UPDATE_MSG;
    private String SUCCESS_DELETE_MSG, FAIL_DELETE_MSG;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhes_categoria);
		
		edtName = (EditText) findViewById(R.id.detalhes_categoria_txtNomeCategoria);
		
		loadContent();
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalhes_categoria, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.menu_detalhes_categoria_alterar:
                update(null);
                return true;
            case R.id.menu_detalhes_categoria_voltar:
                finish();
                return true;
            case R.id.menu_detalhes_categoria_excluir:
                delete();
                return true;
            default:
                return false;
        }

    }

   /**
   * Responsible for obtaining a serialized Category object and set the values ​​on the screen
   */
    private void loadContent() {
		//obtain category via Extra
		Bundle extras = getIntent().getExtras();
		category = (Category) extras.getSerializable("_object_categoria");
		
		if ( category != null) {
			edtName.setText(category.getNome());
		}    	
    }
    
    /**
     * Validate and Update Category
     * @param vw ( ListCategory )
     */    
    public void update(View vw) {
    	daoC = DAOCategory.getInstance(this);
    	
    	category.setNome(edtName.getText().toString());
    	
		DUPLICATE_CATEGORY_MSG = getString(R.string.lbl_erro_duplicidade_categoria);
		SUCCESS_UPDATE_MSG = getString(R.string.lbl_sucesso_alteracao_categoria);
		FAIL_UPDATE_MSG = getString(R.string.lbl_falha_alteracao_categoria);
		
		//Validação dos campos
		Validation validate = new Validation();
		boolean aux1;

        aux1 = validate.isNullWithAnimation( getString(R.string.place_holder_nome_categoria), category.getNome(), edtName, getApplicationContext());
		if(aux1 != true)
		{
			//Check duplicity
			if(daoC.buscarNome(category) != null)
			{
				ToastManager.show(getApplicationContext(), DUPLICATE_CATEGORY_MSG, 2);
			}
			else
			{
				if(daoC.update(category) != -1) //Save object
				{
					ToastManager.show(getApplicationContext(), SUCCESS_UPDATE_MSG, 0);
					finish();
				}
				else
				{
					ToastManager.show(getApplicationContext(), FAIL_UPDATE_MSG, 1);
				}
			}		
		}
    }

    /**
     * Delete Category
     */
    private void delete() {
        daoC = DAOCategory.getInstance(this);

        FAIL_DELETE_MSG =  getString(R.string.lbl_falha_exclusao_categoria);
        SUCCESS_DELETE_MSG = getString(R.string.lbl_sucesso_exclusao_categoria);

        if ( daoC.delete(category.getId_categoria(), this) == -1 ) {
            ToastManager.show(getApplicationContext(), FAIL_DELETE_MSG, 2);
        }
        else {
            ToastManager.show(getApplicationContext(), SUCCESS_DELETE_MSG, 0);
            finish();
        }
    }
    
}
