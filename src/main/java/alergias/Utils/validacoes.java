package alergias.Utils;

import info.diegoramos.alergiass.R;
import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 * 
 */
public class validacoes {

	final static String msg_campo = getString(R.string.lbl_campo);
	final static String msg_obrigatorio = getString(R.string.lbl_obrigatorio);
	
	public final boolean isNull(String nome_campo, Object objeto, int duracao, Context ctx)
	{
		if(objeto.toString().trim().equalsIgnoreCase("") || objeto.toString().trim().equalsIgnoreCase(null) || objeto.toString().trim().equals(null))
		{
			if(duracao > 1)
			{
				Toast.makeText(ctx, msg_campo + " " + nome_campo + " " + msg_obrigatorio, Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(ctx, msg_campo + " " + nome_campo + " " + msg_obrigatorio, Toast.LENGTH_SHORT).show();
			}
				return true;
		}
		else
		{
			return false;
		}
	}

	private static String getString(int lblSucessoCadastroAlergia) {
		return null;
	}
}
