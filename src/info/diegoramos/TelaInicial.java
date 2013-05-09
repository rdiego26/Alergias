package info.diegoramos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TelaInicial extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        
    }
    
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