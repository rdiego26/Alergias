package alergias.activity;

import alergias.persistence.DAOAllergy;
import alergias.persistence.DAOCategory;
import alergias.util.Validation;
import info.diegoramos.alergiass.R;
import alergias.util.ToastManager;
import alergias.entity.Alergia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class RegisterAllergy extends Activity
{

	private DAOAllergy DAOA;
    private DAOCategory DAOC;
    private Alergia A;
	
	//Utilizados para trabalhar com o Spinner
    private List<String> lista_nome_categoria = new ArrayList<String>();
    private List<String> lista_id_categoria = new ArrayList<String>();

    private  Spinner spiCategoria;
    private EditText edtNome;
    private EditText edtObs;

    private String DUPLICATE_ALLERGY_MSG;
    private String SUCCESS_REGISTER_MSG;
    private String FAIL_REGISTER_MSG;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cad_alergia);

        //Singleton
        DAOA = DAOAllergy.getInstance(this);
        DAOC = DAOCategory.getInstance(this);

        lista_nome_categoria = DAOC.findAllNome();
        lista_id_categoria = DAOC.findAllId();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista_nome_categoria);
        spiCategoria = (Spinner) findViewById(R.id.cad_alergia_sp_categoria);
        spiCategoria.setAdapter(dataAdapter);

	}

    public void register(View vw) {

        Alergia allergy = new Alergia();

        //Obtain values from screen
        edtNome = (EditText) findViewById(R.id.cad_alergia_txt_nome_alergia);
        edtObs =  (EditText) findViewById(R.id.cad_alergia_txt_obs_alergia);
        Integer id_categoria_tela = Integer.valueOf(lista_id_categoria.get((int) spiCategoria.getSelectedItemId() ));

        allergy.setNome(edtNome.getText().toString());
        allergy.setObs(edtObs.getText().toString());
        allergy.setId_categoria(id_categoria_tela);

        //Validate
        Validation validate = new Validation();
        boolean aux1;

        aux1 = validate.isNullWithAnimation(getString(R.string.place_holder_alergia), allergy.getNome(), edtNome, getApplicationContext());

        //Messages
        DUPLICATE_ALLERGY_MSG = getString(R.string.lbl_erro_duplicidade_alergia);
        SUCCESS_REGISTER_MSG = getString(R.string.lbl_sucesso_cadastro_alergia);
        FAIL_REGISTER_MSG = getString(R.string.lbl_falha_cadastro_alergia);

        if(aux1 != true)
        {
            //Check duplicity
            if(DAOA.buscarNome(allergy) != null)
            {
                ToastManager.show(getApplicationContext(), DUPLICATE_ALLERGY_MSG, 1);
            }
            else
            {
                if(DAOA.save(allergy) != -1) //Save object
                {
                    ToastManager.show(getApplicationContext(), SUCCESS_REGISTER_MSG, 0);
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
