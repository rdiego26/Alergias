package alergias.activity;

import alergias.entity.Category;
import alergias.persistence.DAOCategory;
import alergias.util.Validation;
import info.diegoramos.alergiass.R;
import alergias.util.ToastManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class RegisterCategory extends Activity
{

	private DAOCategory DAOC;
	private Category category;
    private String DUPLICATE_CATEGORY_MSG;
    private String SUCCESS_REGISTER_CATEGORY;
    private String FAIL_REGISTER_MSG;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cad_categoria);
	}

    /**
     * Validate and Register Category
     * @param vw
     */
    public void register(View vw) {

        DUPLICATE_CATEGORY_MSG = getString(R.string.lbl_erro_duplicidade_categoria);
        SUCCESS_REGISTER_CATEGORY = getString(R.string.lbl_sucesso_cadastro_categoria);
        FAIL_REGISTER_MSG = getString(R.string.lbl_falha_cadastro_categoria);

        //Singleton
        DAOC = DAOCategory.getInstance(this);

        //Instance of Category
        category = new Category();
        EditText edtNome = (EditText) findViewById(R.id.cad_categoria_txt_nome_categoria);
        category.setNome(edtNome.getText().toString());

        //Validate
        Validation validate = new Validation();
        boolean aux1;
        aux1 = validate.isNullWithAnimation( getString(R.string.place_holder_nome_categoria), category.getNome(), edtNome, getApplicationContext());

        if(aux1 != true)
        {
            //Check duplicity
            if(DAOC.buscarNome(category) != null)
            {
                ToastManager.show(getApplicationContext(), DUPLICATE_CATEGORY_MSG, 2);
            }
            else
            {
                if(DAOC.save(category) != -1) //Save object
                {
                    ToastManager.show(getApplicationContext(), SUCCESS_REGISTER_CATEGORY, 0);
                    finish();
                }
                else
                {
                    ToastManager.show(getApplicationContext(), FAIL_REGISTER_MSG, 1);
                }
            }
        }

    }
	
}
