package alergias.activity;

import alergias.entity.Category;
import alergias.persistence.DAOCategory;
import alergias.util.Validacoes;
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
	private Category C;
    //Mensagens de erro/sucesso
    private final String DUPLICATE_CATEGORY_MSG = getString(R.string.lbl_erro_duplicidade_categoria);
    private final String SUCCESS_REGISTER_CATEGORY = getString(R.string.lbl_sucesso_cadastro_categoria);
    private final String FAIL_REGISTER_MSG = getString(R.string.lbl_falha_cadastro_categoria);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cad_categoria);
	}

    public void register(View vw) {

        //Trabalhando com Singleton
        DAOC = DAOCategory.getInstance(this);

        C = new Category();

        //Obtendo os valores dos campos
        EditText edtNome = (EditText) findViewById(R.id.cad_categoria_txt_nome_categoria);
        C.setNome(edtNome.getText().toString());

        //Validação dos campos
        Validacoes validate = new Validacoes();
        boolean aux1;
        aux1 = validate.isNullWithAnimation( getString(R.string.place_holder_nome_categoria), C.getNome(), edtNome, getApplicationContext());

        if(aux1 != true)
        {
            //Verifica duplicidade
            if(DAOC.buscarNome(C) != null)
            {
                ToastManager.show(getApplicationContext(), DUPLICATE_CATEGORY_MSG, 2);
            }
            else
            {
                if(DAOC.save(C) != -1) //Salva para o banco de dados o objeto povoado
                {
                    ToastManager.show(getApplicationContext(), SUCCESS_REGISTER_CATEGORY, 0);
                    finish(); //sae da tela
                }
                else
                {
                    ToastManager.show(getApplicationContext(), FAIL_REGISTER_MSG, 1);
                }
            }
        }

    }
	
}
