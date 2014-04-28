package activity;

import info.diegoramos.alergias.R;
import info.diegoramos.alergias.Utils.ToastManager;
import info.diegoramos.alergias.Utils.validacoes;
import info.diegoramos.alergias.entity.Alergia;
import info.diegoramos.alergias.persistence.DAOAlergia;
import info.diegoramos.alergias.persistence.DAOCategoria;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class CadastrarAlergia extends Activity
{

	DAOAlergia DAOA;
	DAOCategoria DAOC;
	Alergia A;
	
	//Utilizados para trabalhar com o Spinner
	List<String> lista_nome_categoria = new ArrayList<String>();
	List<String> lista_id_categoria = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cad_alergia);
		
		//Trabalhando com Singleton
		DAOA = DAOAlergia.getInstance(this);
		DAOC = DAOCategoria.getInstance(this);
		
		
		//Preenchendo o Spinner
		lista_nome_categoria = DAOC.findAllNome();
		lista_id_categoria = DAOC.findAllId();
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista_nome_categoria);
		//ArrayAdapter<String> spinnerArrayAdapter = dataAdapter;
		
		final Spinner spiCategoria = (Spinner) findViewById(R.id.cad_alergia_sp_categoria);
		spiCategoria.setAdapter(dataAdapter);

		//Tratamento do bot�o Cadastrar
		Button btn = (Button) findViewById(R.id.cad_alergia_btn_cadastrar);
		btn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Alergia A = new Alergia();
				
				//Obtendo os valores dos campos
				EditText edtNome = (EditText) findViewById(R.id.cad_alergia_txt_nome_alergia);
				EditText edtObs =  (EditText) findViewById(R.id.cad_alergia_txt_obs_alergia);
				Integer id_categoria_tela = Integer.valueOf(lista_id_categoria.get((int) spiCategoria.getSelectedItemId() ));
				
				A.setNome(edtNome.getText().toString());
				A.setObs(edtObs.getText().toString());
				A.setId_categoria(id_categoria_tela);
				
				//Valida��o dos campos
				validacoes validate = new validacoes();				
				boolean aux1;
				
				aux1 = validate.isNull("Alergia ", A.getNome(), 1, getApplicationContext());
				
				//Mensagens de erro/sucesso
				String msg_duplicidade_alergia = getString(R.string.lbl_erro_duplicidade_alergia);
				String msg_sucesso_gravacao = getString(R.string.lbl_sucesso_cadastro_alergia);
				String msg_falha_gravacao = getString(R.string.lbl_falha_cadastro_alergia);
				if(aux1 != true)
				{
					//Verifica duplicidade
					if(DAOA.buscarNome(A) != null)
					{
						ToastManager.show(getApplicationContext(), msg_duplicidade_alergia, 1);
					}
					else
					{
						if(DAOA.save(A) != -1) //Salva para o banco de dados o objeto povoado	
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

		});
		
	}	
}
