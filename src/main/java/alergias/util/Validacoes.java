package alergias.util;

import info.diegoramos.alergiass.R;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail>
 * 
 */
public class Validacoes {

	private String msg_campo;
	private String msg_obrigatorio;
    private StringBuilder msg;
	
	public final boolean isNull(String nome_campo, Object objeto, int duracao, Context ctx)
	{
		if(objeto.toString().trim().equalsIgnoreCase("") || objeto.toString().trim().equalsIgnoreCase(null) || objeto.toString().trim().equals(null))
		{
            msg_campo = ctx.getString(R.string.lbl_campo);
            msg_obrigatorio = ctx.getString(R.string.lbl_obrigatorio);
            msg = new StringBuilder(msg_campo).append(" ")
                    .append(nome_campo).append(" ")
                    .append(msg_obrigatorio);


            ToastManager.show(ctx, msg.toString(), 2);
            /*
			if(duracao > 1)
			{
                Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
			}
			*/
				return true;
		}
		else
		{
			return false;
		}
	}

    public final boolean isNullWithAnimation(String nome_campo, Object objeto, View vw, Context ctx)
    {

        if(objeto.toString().trim().equalsIgnoreCase("") || objeto.toString().trim().equalsIgnoreCase(null) || objeto.toString().trim().equals(null))
        {
            Animation shake = AnimationUtils.loadAnimation(ctx, R.anim.shake);

            msg_campo = ctx.getString(R.string.lbl_campo);
            msg_obrigatorio = ctx.getString(R.string.lbl_obrigatorio);
            msg = new StringBuilder(msg_campo).append(" ")
                    .append(nome_campo).append(" ")
                    .append(msg_obrigatorio);


            ToastManager.show(ctx, msg.toString(), 2);
            vw.startAnimation(shake);

            return true;
        }
        else
        {
            return false;
        }
    }

}