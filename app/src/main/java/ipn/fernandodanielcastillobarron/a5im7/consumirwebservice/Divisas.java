package ipn.fernandodanielcastillobarron.a5im7.consumirwebservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//Actividad que convierte un valor dado (en MXN)
//a una divisa seleccionada
public class Divisas extends AppCompatActivity {

    EditText valor;
    Spinner moneda;
    TextView resultado;
    String divisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divisas);

        valor = (EditText)findViewById(R.id.valor);
        moneda = (Spinner)findViewById(R.id.moneda);
        resultado = (TextView)findViewById(R.id.resultado);
        divisa = "MXN";
        moneda.setSelection(0);
        moneda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                divisa = moneda.getSelectedItem().toString().substring(0,3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                divisa = "MXN";
            }
        });

        valor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcular();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //Metodo que llama el web service REST
    //y agrega el resultado para mostrar en pantalla
    //NOTA: modificar la direccion ipv4 con la de la maquina
    //corrient el web service
    public void calcular(){
        String url = "http://192.168.0.17:8080/WebServices/webresources/RestService/contertirDivisa/moneda=" +divisa+ "&cantidad=" + (valor.getText().toString().isEmpty() ? "0.0" : valor.getText().toString());
        ConsumirREST restwebservice = new ConsumirREST(resultado);
        restwebservice.execute(url);
    }
}
