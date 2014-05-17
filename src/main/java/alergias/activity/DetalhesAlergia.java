package alergias.activity;

import alergias.entity.Category;
import alergias.persistence.DAOAllergy;
import alergias.util.Validation;
import info.diegoramos.alergiass.R;
import alergias.util.ToastManager;
import alergias.components.CategoriaSpinnerAdapter;
import alergias.entity.Alergia;
import alergias.persistence.DAOCategory;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class DetalhesAlergia extends Activity{

	DAOAllergy daoA;
	DAOCategory daoC;
	Alergia a;
	
	//Componentes da tela
	EditText edtNome, edtObs;
	Spinner spiCategoria;
	
	
	//Mensagens
	String msg_duplicidade_alergia, msg_sucesso_gravacao, msg_falha_gravacao;
	String msg_falha_exclusao, msg_sucesso_exclusao;
	String msg_sucesso_alteracao, msg_falha_alteracao;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhes_alergia);
	    
		//Setando Mensagens
		msg_duplicidade_alergia = getString(R.string.lbl_erro_duplicidade_alergia);
		msg_sucesso_gravacao = getString(R.string.lbl_sucesso_cadastro_alergia);
		msg_falha_gravacao = getString(R.string.lbl_falha_cadastro_alergia);	
		msg_sucesso_alteracao = getString(R.string.lbl_sucesso_alteracao_alergia);
		msg_falha_alteracao = getString(R.string.lbl_falha_alteracao_alergia);
	    
	    
		//Componentes
		spiCategoria = (Spinner)findViewById(R.id.detalhes_alergia_sp_categoria);
		edtNome = (EditText)findViewById(R.id.detalhes_alergia_txtNomeAlergia);
		edtObs = (EditText)findViewById(R.id.detalhes_alergia_txtObsAlergia);
	    
		loadCategoriaSpinner();
		loadContent();
		
	}

    //Colocando o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalhes_alergia, menu);

        return true;
    }

    //Tratando item selecionado no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        //Verificando qual item do menu foi selecionado
        switch (item.getItemId())
        {
            case R.id.menu_detalhes_alergia_alterar:
                update(null);
                return true;
            case R.id.menu_detalhes_alergia_voltar:
                finish();
                return true;
            case R.id.menu_detalhes_alergia_excluir:
                delete();
                return true;
            default:
                return false;
        }

    }


    /**
     * Responsável por obter um objeto Category serializado e setar os valores na tela
     */    
    private void loadContent() {
		//obtain client via Extra
		Bundle extras = getIntent().getExtras();
		a = (Alergia) extras.getSerializable("_object_alergia");
		
		if ( a != null) {
			daoC = DAOCategory.getInstance(this);
			
			setCategoriaSpinner( daoC.getById(a.getId_categoria()));
			edtNome.setText(a.getNome());
			edtObs.setText(a.getObs());
		}     	
    }
    
    /**
     * Responsável por preencher o Spinner de Category
     */
    private void loadCategoriaSpinner() {
    	spiCategoria.setAdapter(CategoriaSpinnerAdapter.getAdapter(this));
    }
    /**
     * Recebe um objeto Category e seta este no Spinner Category
     * @param cat
     */
    
	private void setCategoriaSpinner(Category cat) {
		for (int i = 0; i < spiCategoria.getCount(); i++) {  
            if (spiCategoria.getItemAtPosition(i).toString()
            		.replace("{nomeCategoria=", "").replace("}", "")
            			.equals(cat.getNome())) {  
            	spiCategoria.setSelection(i);  
            }  
        }  
	}

	/**
	 * Responsável por obter o objeto selecionado no Spinner Category
	 */
	private Category getCategoriaSpinner() {
		Category category = daoC.getByName( spiCategoria.getSelectedItem().toString().replace("{nomeCategoria=", "").replace("}", "") );
		
		return category;
	}

	
	/**
    * Responsável por efetuar a atualização do objeto Alergia
    * @param vw ( ListAlergie )
    */    	
	public void update(View vw) {
		daoA = DAOAllergy.getInstance(this);
		
		a.setId_categoria( daoC.getByName(getCategoriaSpinner().getNome()).getId_categoria() );
		a.setNome(edtNome.getText().toString());
		a.setObs(edtObs.getText().toString());
		
		
		Validation validate = new Validation();
		boolean aux1;
		
		aux1 = validate.isNull("Alergia ", a.getNome(), 1, getApplicationContext());		
		
		
		String msg_duplicidade_alergia = getString(R.string.lbl_erro_duplicidade_alergia);
		String msg_sucesso_gravacao = getString(R.string.lbl_sucesso_alteracao_alergia);
		String msg_falha_gravacao = getString(R.string.lbl_falha_alteracao_alergia);
		
		if(aux1 != true)
		{
			//Verifica duplicidade
			if(daoA.buscarNomeDuplicado(a) != null)
			{
				ToastManager.show(getApplicationContext(), msg_duplicidade_alergia, 1);
			}
			else
			{
				if(daoA.update(a) != -1) //Salva para o banco de dados o objeto povoado	
				{
					ToastManager.show(getApplicationContext(), msg_sucesso_gravacao, 0);
					finish(); //sae da tela						
				}
				else
				{
					ToastManager.show(getApplicationContext(), msg_falha_gravacao, 1);
				}
			}
		}
	}

    private void delete() {
        daoA = DAOAllergy.getInstance(this);

        msg_falha_exclusao =  getString(R.string.lbl_falha_exclusao_alergia);
        msg_sucesso_exclusao = getString(R.string.lbl_sucesso_exclusao_alergia);

        if ( daoA.delete(a.getId_alergia()) == -1 ) {
            ToastManager.show(getApplicationContext(), msg_falha_exclusao, 2);
        }
        else {
            ToastManager.show(getApplicationContext(), msg_sucesso_exclusao, 0);
            finish();
        }
    }
}
