package alergias.activity;

import info.diegoramos.alergiass.R;
import alergias.Utils.ToastManager;
import alergias.Utils.validacoes;
import alergias.entity.Categoria;
import alergias.persistence.DAOCategoria;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class CadastrarCategoria extends Activity
{

	
	DAOCategoria DAOC;
	Categoria C;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cad_categoria);
	}

    public void cadastrar(View vw) {

        //Trabalhando com Singleton
        DAOC = DAOCategoria.getInstance(this);

        Categoria C = new Categoria();

        //Obtendo os valores dos campos
        EditText edtNome = (EditText) findViewById(R.id.cad_categoria_txt_nome_categoria);
        C.setNome(edtNome.getText().toString());

        //Valida��o dos campos
        validacoes validate = new validacoes();
        boolean aux1;

        aux1 = validate.isNull("Categoria ", C.getNome(), 1, getApplicationContext());

        //Mensagens de erro/sucesso
        String msg_duplicidade_categoria = getString(R.string.lbl_erro_duplicidade_categoria);
        String msg_sucesso_gravacao = getString(R.string.lbl_sucesso_cadastro_categoria);
        String msg_falha_gravacao = getString(R.string.lbl_falha_cadastro_categoria);

        if(aux1 != true)
        {
            //Verifica duplicidade
            if(DAOC.buscarNome(C) != null)
            {
                ToastManager.show(getApplicationContext(), msg_duplicidade_categoria, 2);
            }
            else
            {
                if(DAOC.save(C) != -1) //Salva para o banco de dados o objeto povoado
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
	
}
