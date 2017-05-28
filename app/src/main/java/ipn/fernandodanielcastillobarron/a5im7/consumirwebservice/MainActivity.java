package ipn.fernandodanielcastillobarron.a5im7.consumirwebservice;
/*
AUTOR: Fernando Daniel Castillo Barron (C) Abril 2017
VERSIÓN: 1.0

DESCRIPCIÓN: Programa que ejemplifica el consumo de web services SOAP y RESTful

OBSERVACIONES:
El programa usa un login, tiene una calculadora y convierte divisas

COMPILACIÓN: se compila en tiempo de ejecucion.
EJECUCIÓN: se ejecuta desde el IDE de Android Studio con las teclas shift + F10.  (En Windows) - ./programa (En Linux)
*/

//LIBRERIAS
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView usr;
    TextView psw;
    String respuestaSOAP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView olvidaste = (TextView)findViewById(R.id.olvidaste);
        olvidaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Usuario: DanielCastillo\nContraseña: GT56YH",Toast.LENGTH_LONG).show();
            }
        });
        usr = (TextView)findViewById(R.id.usr);
        psw = (TextView)findViewById(R.id.psw);
        respuestaSOAP = "";
    }

    // Metodo invocado por el boton Login (Ingresar)
    // Para validar el usuario y contraseña ingresados con el web service
    // NOTA: ingresar direccion ipv4 en WSDL para que funcione
    public void ingresar(View o){
        final String NAMESPACE = "http://ws/";
        final String METHOD_NAME = "login";
        final String SOAP_ACTION = "http://ws/SOAPWebService/loginRequest";
        final String WSDL = "http://192.168.0.17:8080/WebServices/SOAPWebService";
        Map<String,String> parametros = new HashMap();
        parametros.put("usuario",usr.getText().toString());
        parametros.put("contraseña",psw.getText().toString());
        TextView contenedor = new TextView(this);
        contenedor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().startsWith("ERROR")){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Ocurrió un error")
                            .setMessage(s.toString())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    Boolean resultado = new Boolean(s.toString());
                    if(resultado){
                        Intent intento = new Intent(MainActivity.this,Menu.class);
                        MainActivity.this.startActivity(intento);
                    }else{
                        Toast.makeText(MainActivity.this,"Usuario o Contraseña incorrectos",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ConsumirSOAP soapws = new ConsumirSOAP(contenedor,parametros);
        soapws.execute(NAMESPACE,METHOD_NAME,WSDL,SOAP_ACTION);
    }
}
