package alergias.activity;

import info.diegoramos.alergiass.R;
import alergias.Utils.ToastManager;
import alergias.Utils.validacoes;
import alergias.entity.Categoria;
import alergias.persistence.DAOCategoria;
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
public class DetalhesCategoria extends Activity
{
	Categoria c;
	DAOCategoria daoC;
	
	//Componentes da tela
	EditText edtNome;

	//Mensagens
	String msg_duplicidade_categoria, msg_sucesso_gravacao, msg_falha_gravacao;
	String msg_sucesso_alteracao, msg_falha_alteracao;
    String msg_sucesso_exclusao, msg_falha_exclusao;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhes_categoria);
		
		edtNome = (EditText) findViewById(R.id.detalhes_categoria_txtNomeCategoria);
		
		loadContent();
		
	}

    //Colocando o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalhes_categoria, menu);

        return true;
    }


    //Tratando item selecionado no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        //Verificando qual item do menu foi selecionado
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
   * Responsável por obter um objeto Categoria serializado e setar os valores na tela
   */
    private void loadContent() {
		//obtain client via Extra
		Bundle extras = getIntent().getExtras();
		c = (Categoria) extras.getSerializable("_object_categoria");
		
		if ( c != null) {
			edtNome.setText(c.getNome());
		}    	
    }
    
    /**
     * Responsável por efetuar a atualização do objeto Categoria
     * @param vw ( ListarCategoria )
     */    
    public void update(View vw) {
    	daoC = DAOCategoria.getInstance(this);
    	
    	c.setNome(edtNome.getText().toString());
    	
		msg_duplicidade_categoria = getString(R.string.lbl_erro_duplicidade_categoria);
		msg_sucesso_gravacao = getString(R.string.lbl_sucesso_cadastro_categoria);
		msg_falha_gravacao = getString(R.string.lbl_falha_cadastro_categoria);
		msg_sucesso_alteracao = getString(R.string.lbl_sucesso_alteracao_categoria);
		msg_falha_alteracao = getString(R.string.lbl_falha_alteracao_categoria);
		
		//Validação dos campos
		validacoes validate = new validacoes();				
		boolean aux1;
		
		aux1 = validate.isNull("Categoria ", c.getNome(), 1, getApplicationContext());
		if(aux1 != true)
		{
			//Verifica duplicidade
			if(daoC.buscarNome(c) != null)
			{
				ToastManager.show(getApplicationContext(), msg_duplicidade_categoria, 2);
			}
			else
			{
				if(daoC.update(c) != -1) //Salva para o banco de dados o objeto povoado	
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
        daoC = DAOCategoria.getInstance(this);

        msg_falha_exclusao =  getString(R.string.lbl_falha_exclusao_categoria);
        msg_sucesso_exclusao = getString(R.string.lbl_sucesso_exclusao_categoria);

        if ( daoC.delete(c.getId_categoria(), this) == -1 ) {
            ToastManager.show(getApplicationContext(), msg_falha_exclusao, 2);
        }
        else {
            ToastManager.show(getApplicationContext(), msg_sucesso_exclusao, 0);
            finish();
        }
    }
    
}
