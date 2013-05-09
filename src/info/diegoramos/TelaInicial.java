package info.diegoramos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 *
 */
public class TelaInicial extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
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
    			
    	case R.id.main_btn_sugestion:
				startActivity(new Intent(getApplicationContext(), EnviarSugestao.class));
				break;
			
    	default: break;
    	}
    	
    	
    }
    
    
}