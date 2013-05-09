package info.diegoramos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class EnviarSugestao extends Activity
{
	

	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
	
	//Componentes
	//private Button btnEnviar;
	private EditText txtMensagem;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviar_sugestao);
    }
	
	public void trataBotaoSugestao(View vw) {
		String assunto = new String();
		String destinatario;
		
		assunto = getString(R.string.lbl_sugestao) + " - " + getString(R.string.app_name);	
		destinatario  = getString(R.string.email_autor);
		
		//Mapeia o objeto que contem o texto
		txtMensagem = (EditText) findViewById(R.enviar_sugestao.txtMensagemSugestao);
		
		//Configurando o  objeto para envio de email
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, assunto);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{destinatario});
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, txtMensagem.getText().toString());
		startActivity(Intent.createChooser(emailIntent, assunto));
		finish();		
	}
	
}
