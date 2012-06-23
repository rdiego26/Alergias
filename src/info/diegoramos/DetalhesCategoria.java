package info.diegoramos;

import info.diegoramos.R;
import persistence.DAOCategoria;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import entity.Alergia;
import entity.Categoria;

public class DetalhesCategoria extends Activity
{
	DAOCategoria DAOC;
	Categoria C;
	
	//Criadao para acelerar o delete, update
	String id_categoria;
	
	//Componentes da tela
	EditText edtNome;

	//Mensagens
	String msg_duplicidade_categoria;
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
		setContentView(R.layout.detalhes_categoria);
	
		msg_duplicidade_categoria = this.getString(R.string.lbl_erro_duplicidade_categoria);
		msg_sucesso_gravacao = this.getString(R.string.lbl_sucesso_cadastro_categoria);
		msg_falha_gravacao = this.getString(R.string.lbl_falha_cadastro_categoria);	
		msg_sucesso_alteracao = this.getString(R.string.lbl_sucesso_alteracao_categoria);
		msg_falha_alteracao = this.getString(R.string.lbl_falha_alteracao_categoria);
		msg_sucesso_exclusao = this.getString(R.string.lbl_sucesso_exclusao_categoria);	
		msg_falha_exclusao = this.getString(R.string.lbl_falha_exclusao_categoria);		
		
	    DAOC = DAOC.getInstance(this);
	    
	    //Pegando o parâmetro recebido pelo ListarAlergia
		Bundle extras = getIntent().getExtras();
		id_categoria = (String)extras.get("id_categoria");
		
		//Faz a busca e preenche os dados na tela, e obtem os dados para preencher a lista de Tipos de Despesa
		C = DAOC.buscar(new Integer(id_categoria));
		//final Integer id_categoria = C.getId_categoria(); //Utilizado para otimizar a atualização
		
		
		//Preenche os campos com os valores obtidos na consulta
		if(C != null)
		{
			edtNome	     	= (EditText) findViewById(R.detalhes_categoria.txtNomeCategoria);
			
			edtNome.setText(C.getNome());
		}
	}	
	
	protected void excluirCategoria(Categoria C)
    {
    	if(DAOC.delete(C.getId_categoria(), this) == -1)
    	{
    		Toast.makeText(getApplicationContext(), msg_falha_exclusao, Toast.LENGTH_LONG).show();
    	}
    	else
    	{
    		Toast.makeText(getApplicationContext(), msg_sucesso_exclusao, Toast.LENGTH_LONG).show();
    	}
    	
    }
	
    protected void alterarCategoria(Categoria C)
    {
    	
		//Verifica duplicidade
		if(DAOC.buscarNome(C) != null)
		{
			Toast.makeText(getApplicationContext(), msg_duplicidade_categoria, Toast.LENGTH_SHORT).show();			
		}
		else
		{
			if(DAOC.update(C) == -1)
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
    	inflater.inflate(R.menu.menu_detalhes_categoria, menu);
   	
    	return true;
    }    

    
    //Tratando item selecionado no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	// TODO Auto-generated method stub
    	Intent intencao;
		intencao = new Intent(this, ListarCategoria.class);
		Context ctx;
		ctx = getApplicationContext();
		
    	//Verificando qual item do menu foi selecionado
    	switch (item.getItemId())
    	{
    		case R.menu_detalhes_categoria.excluir:
    			excluirCategoria(C);
    			startActivity(intencao);
    			finish();
    			return true;
    	
    		case R.menu_detalhes_categoria.alterar:
    			//Seta o objeto com os dados da tela
    			C.setNome(edtNome.getText().toString());
    			alterarCategoria(C);
    			finish();
    			return true;
    			
    		case R.menu_detalhes_categoria.voltar:
    			finish();
    			return true;
    			
    		default:
    			return false;
    			
    	}
    	
    }
    
}
