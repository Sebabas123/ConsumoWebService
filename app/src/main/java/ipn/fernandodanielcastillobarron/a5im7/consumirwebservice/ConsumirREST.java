package ipn.fernandodanielcastillobarron.a5im7.consumirwebservice;

import android.os.AsyncTask;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by Bear on 13/04/17.
 */

// Clase que consume un web service REST
// Y mete el resultado en un TextView
// previamente asignado en el constructor
public class ConsumirREST extends AsyncTask<String,Void,String>{
    private TextView contenedor;

    //Constructor de la clase
    //@param contenedor : view donde se guarda el resultado del web service
    public ConsumirREST(TextView contenedor){
        this.contenedor = contenedor;
    }

    //metodo sobreescrito que consume el web service
    //para ello se usa una libreria especial creada por kevinsawicki, entre otros
    //ver dependencia en build.gradle
    @Override
    protected String doInBackground(String... params) {
        return HttpRequest.get(params[0]).accept("text/plain").body();
    }
    @Override
    protected void onPostExecute(String resultado){
        contenedor.setText(resultado);
    }
}
