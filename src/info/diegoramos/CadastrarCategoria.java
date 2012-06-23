package info.diegoramos;

import persistence.DAOCategoria;
import Utils.validacoes;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import entity.Categoria;
import info.diegoramos.R;

public class CadastrarCategoria extends Activity
{

	
	DAOCategoria DAOC;
	Categoria C;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cad_categoria);
		
		//Trabalhando com Singleton
		DAOC = DAOCategoria.getInstance(this);

		//Tratamento do botão Cadastrar
		Button btn = (Button) findViewById(R.cad_categoria.btn_cadastrar);
		btn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Categoria C = new Categoria();
				
				//Obtendo os valores dos campos
				EditText edtNome = (EditText) findViewById(R.cad_categoria.txt_nome_categoria);
			
				C.setNome(edtNome.getText().toString());
				
				//Validação dos campos
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
						Toast.makeText(getApplicationContext(), msg_duplicidade_categoria, Toast.LENGTH_SHORT).show();
					}
					else
					{
						if(DAOC.save(C) != -1) //Salva para o banco de dados o objeto povoado	
						{
							//Exibindo mensagem de sucesso
							Toast.makeText(getApplicationContext(), msg_sucesso_gravacao, Toast.LENGTH_SHORT).show();
							finish(); //sae da tela						
						}
						else
						{
							//Exibindo mensagem de falha
							Toast.makeText(getApplicationContext(), msg_falha_gravacao, Toast.LENGTH_SHORT).show();							
						}
					}
				}
			}

		});
		
	}		
	
}
