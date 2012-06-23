package Utils;

import info.diegoramos.R;
import android.content.Context;
import android.widget.Toast;

public class validacoes {

	final static String msg_campo = getString(R.string.lbl_campo);
	final static String msg_obrigatorio = getString(R.string.lbl_obrigatorio);
	
	public final static boolean isNull(String nome_campo, Object objeto, int duracao, Context ctx)
	{
		if(objeto.toString().trim().equalsIgnoreCase("") || objeto.toString().trim().equalsIgnoreCase(null) || objeto.toString().trim().equals(null))
		{
			if(duracao > 1)
			{
				//Toast.makeText(ctx, "Campo " + nome_campo + " é obrigatório!", Toast.LENGTH_LONG).show();
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
		// TODO Auto-generated method stub
		return null;
	}
}
