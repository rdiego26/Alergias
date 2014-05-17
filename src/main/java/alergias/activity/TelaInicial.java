package alergias.activity;

import info.diegoramos.alergiass.R;
import alergias.util.ToastManager;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class TelaInicial extends Activity {

	String msgFalhaMarket;
	
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        
        msgFalhaMarket = getString(R.string.lbl_falha_abertura_market);
    }
    
    /**
     * Tratamento dos botões na DashBoard
     * @param vw
     */
    public void onButtonClicker(View vw) {
       	//Verificando qual item do menu foi selecionado
    	switch (vw.getId())
    	{
		case R.id.main_btn_category:
				startActivity(new Intent(getApplicationContext(), ListarCategoria.class) );
				break;
			
    	case R.id.main_btn_allergie:
    			startActivity(new Intent(getApplicationContext(), ListarAlergia.class));
    			break;

    	case R.id.main_btn_rate:
    			//Try Google play
    			Intent intent = new Intent(Intent.ACTION_VIEW);
    		    intent.setData(Uri.parse("market://details?id=info.diegoramos.alergiass"));
    		    try {
    		    	startActivity(intent);
    		    } catch(Exception ex) {
    		    	ToastManager.show(getApplicationContext(), msgFalhaMarket, 2);
    		    }
    		    
				break;    			
    			
    	case R.id.main_btn_sugestion:
				startActivity(new Intent(getApplicationContext(), EnviarSugestao.class));
				break;
			
    	default: break;
    	}
    	
    }
    
}