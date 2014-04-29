package alergias.Utils;

import info.diegoramos.alergiass.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Wellington Cabral
 * @see http://wcabralti.blogspot.com.br/2013/01/personalizando-toast-no-android.html
 *
 */
public class ToastManager {

	public static final int INFORMATION = 0;
	public static final int WARNING     = 1;
	public static final int ERROR       = 2;
	
	public static void show(Context context, String text, int toastType) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast_layout, null);
		
		TextView tv = (TextView) layout.findViewById(R.id.tvTexto);
		tv.setText(text);
		
		LinearLayout llRoot = (LinearLayout) layout.findViewById(R.id.llRoot);
		
		Drawable img;
		int bg;
		
		switch (toastType) {
		case WARNING:
			img = context.getResources().getDrawable(R.drawable.warning);
			bg  = R.drawable.toast_background_yellow; 
			break;
		case ERROR:
			img = context.getResources().getDrawable(R.drawable.error);
			bg  = R.drawable.toast_background_red;
			break;
		default:
			img = context.getResources().getDrawable(R.drawable.information);
			bg  = R.drawable.toast_background_blue;
			break;
		}
 		tv.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
 		llRoot.setBackgroundResource(bg);
		
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);		
		toast.show();		
	}
	
}
