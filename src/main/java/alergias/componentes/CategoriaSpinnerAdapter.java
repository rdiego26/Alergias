package alergias.componentes;

import info.diegoramos.alergiass.R;
import alergias.entity.Category;
import alergias.persistence.DAOCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.widget.SimpleAdapter;

/**
 * 
 * @author Diego Ramos <rdiego26@gmail.com>
 *
 */
public class CategoriaSpinnerAdapter {

	/**
	 * Constroe um adapter para um Spinner de Categorias
	 * @param ctx ( Context )
	 * @return SimpleAdapter, utilizado em um Spinner com setAdapter
	 */
	public static final SimpleAdapter getAdapter(Context ctx) {
		DAOCategory daoC = DAOCategory.getInstance(ctx);
		List<Category> list = daoC.findAll();
		
		ArrayList<HashMap<String, String>> alist = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        
        for(int i=0; i < list.size(); i++){
 
            map = new HashMap<String,String>();
            map.put("nomeCategoria", list.get(i).getNome());
             
            alist.add(map);
        }        
        
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                ctx, 
                alist,
                R.layout.categoria_spinner_item, 
                new String[] {
                    "nomeCategoria"
                }, 
                new int[] {
                    R.id.SpinnerCategoriaItem_name
                }
            );
        
            return simpleAdapter;        
	}	
	
	
}
