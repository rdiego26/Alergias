package info.diegoramos;

import info.diegoramos.R;
import java.util.ArrayList;
import java.util.List;

import persistence.DAOAlergia;
import persistence.DAOCategoria;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import entity.Alergia;

public class DetalhesAlergia extends Activity{

	DAOAlergia DAOA;
	DAOCategoria DAOC;
	Alergia A;
	List<String> lista_nome_categoria = new ArrayList<String>();
	List<String> lista_id_categoria = new ArrayList<String>();
	
	//Criadao para acelerar o delete, update
	String id_alergia;
	
	
	//Componentes da tela
	EditText edtNome, edtObs;
	Spinner spiCategoria;
	ArrayAdapter myAdap;
	
	
	//Mensagens
	String msg_duplicidade_alergia;
	String msg_sucesso_gravacao;
	String msg_falha_gravacao;	
	String msg_sucesso_alteracao;
	String msg_falha_alteracao;
	String msg_sucesso_exclusao;	
	String msg_falha_exclusao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhes_alergia);

		//Singleton
	    DAOA = DAOA.getInstance(this);
	    DAOC = DAOC.getInstance(this);
	    
		//Setando Mensagens
		msg_duplicidade_alergia = getString(R.string.lbl_erro_duplicidade_alergia);
		msg_sucesso_gravacao = getString(R.string.lbl_sucesso_cadastro_alergia);
		msg_falha_gravacao = getString(R.string.lbl_falha_cadastro_alergia);	
		msg_sucesso_alteracao = getString(R.string.lbl_sucesso_alteracao_alergia);
		msg_falha_alteracao = getString(R.string.lbl_falha_alteracao_alergia);
		msg_sucesso_exclusao = getString(R.string.lbl_sucesso_exclusao_alergia);	
		msg_falha_exclusao = getString(R.string.lbl_falha_exclusao_alergia);
	    
	    
	    
		//Preenchendo o Spinner
	    lista_nome_categoria = DAOC.findAllNome();
	    lista_id_categoria = DAOC.findAllId();
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista_nome_categoria);
		ArrayAdapter<String> spinnerArrayAdapter = dataAdapter;
		final Spinner spiCategoria = (Spinner) findViewById(R.detalhes_alergia.sp_categoria);
		spiCategoria.setAdapter(dataAdapter);

	    //Pegando o parâmetro recebido pelo ListarAlergia
		Bundle extras = getIntent().getExtras();
		id_alergia = (String)extras.get("id_alergia");
		
		//Faz a busca e preenche os dados na tela, e obtem os dados para preencher a lista de Tipos de Despesa
		A = DAOA.buscar(new Integer(id_alergia));
		
		//Preenchendo os dados na tela
		Integer id_categoria = 0;
		
		//Procurando a posição na lista que contem a id_categoria do objeto
		for(int i=0; i < lista_id_categoria.size();i++)
		{
			if(lista_id_categoria.get(i).equalsIgnoreCase(String.valueOf(A.getId_categoria())) )
			{
				id_categoria = i;
			}
		}

		
		myAdap = (ArrayAdapter) spiCategoria.getAdapter(); 
		
		//Preenche os campos com os valores obtidos na consulta
		if(A != null)
		{
			edtNome	     	= (EditText) findViewById(R.detalhes_alergia.txtNomeAlergia);
			edtObs			= (EditText) findViewById(R.detalhes_alergia.txtObsAlergia);
			
			edtNome.setText(A.getNome());
			edtObs.setText(A.getObs());
			
			//Preenchendo o Spinner e setando o valor que está no banco
			spiCategoria.setAdapter(dataAdapter);
			spiCategoria.setSelection(id_categoria); //Indice começa em 0

		}
	}

	protected void excluirAlergia(Alergia A)
    {
		
		if(DAOA.delete(A.getId_alergia()) == -1)
    	{
    		Toast.makeText(getApplicationContext(), msg_falha_exclusao, Toast.LENGTH_LONG).show();
    	}
    	else
    	{
    		Toast.makeText(getApplicationContext(), msg_sucesso_exclusao, Toast.LENGTH_LONG).show();
    	}
    }

    
    protected void alterarAlergia(Alergia A)
    {
		//Verifica duplicidade
		if(DAOA.buscarNomeDuplicado(A) != null)
		{
			Toast.makeText(getApplicationContext(), msg_duplicidade_alergia, Toast.LENGTH_SHORT).show();
		}
		else
		{
	    	if(DAOA.update(A) == -1)
	    	{
	    		Toast.makeText(getApplicationContext(), msg_falha_alteracao, Toast.LENGTH_LONG).show();
	    	}
	    	else
	    	{
	    		Toast.makeText(getApplicationContext(), msg_sucesso_alteracao, Toast.LENGTH_LONG).show();
	    	}
		}
    } 
    
    
    //Colocando o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu_detalhes_alergia, menu);
   	
    	return true;
    }
	 
    //Tratando item selecionado no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	Intent intencao;
		intencao = new Intent(this, ListarAlergia.class);
		
    	//Verificando qual item do menu foi selecionado
    	switch (item.getItemId())
    	{
    		case R.menu_detalhes_alergia.excluir:
    			excluirAlergia(A);
    			startActivity(intencao);
    			finish();
    			return true;
    	
    		case R.menu_detalhes_alergia.alterar:
    			//Seta o objeto com os dados da tela
    			final Spinner spiCategoria = (Spinner) findViewById(R.detalhes_alergia.sp_categoria);
    		    lista_nome_categoria = DAOC.findAllNome();
    		    lista_id_categoria = DAOC.findAllId();
    			Integer id_categoria_selecionada;
    			
    			id_categoria_selecionada = Integer.valueOf(lista_id_categoria.get(spiCategoria.getSelectedItemPosition()));
    			
    			A.setId_categoria(id_categoria_selecionada);
    			A.setNome(edtNome.getText().toString());
    			A.setObs(edtObs.getText().toString());
    			alterarAlergia(A);
    			finish();
    			return true;
    			
    		case R.menu_detalhes_alergia.voltar:
    			finish();
    			return true;
    			
    		default:
    			return false;
    			
    	}
    	
    }
}
