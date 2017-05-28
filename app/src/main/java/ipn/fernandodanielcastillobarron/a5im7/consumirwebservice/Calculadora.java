package ipn.fernandodanielcastillobarron.a5im7.consumirwebservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class Calculadora extends AppCompatActivity {

    private Double numero1;
    private Double numero2;
    private String operacion;

    private TextView numero1Vista;
    private TextView numero2Vista;
    private TextView operacionVista;

    final String NAMESPACE = "http://ws/";
    final String METHOD_NAME = "calculadora";
    final String SOAP_ACTION = "http://ws/SOAPWebService/calculadoraRequest";
    final String WSDL = "http://192.168.0.17:8080/WebServices/SOAPWebService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        numero1Vista = (TextView)findViewById(R.id.numero1Vista);
        numero2Vista = (TextView)findViewById(R.id.numero2Vista);
        operacionVista = (TextView)findViewById(R.id.operacionVista);

        numero1 = new Double(0.0);
        numero2 = new Double(0.0);
        operacion = "";
    }

    public void calcular(View o){
        String boton = ((Button)o).getText().toString();
        if(boton.equals("DEL")){
            String contenido = numero1Vista.getText().toString();
            if(contenido.length() == 1){
                contenido = "0";
            }else{
                contenido = contenido.substring(0,contenido.length()-2);
            }
            numero1 = new Double(contenido);
            numero1Vista.setText(contenido);
        }else if(boton.equals("CE")){
            numero1 = 0.0;
            numero2 = 0.0;
            operacion = "";
            numero1Vista.setText("0");
            numero2Vista.setText("0");
            operacionVista.setText("");
        }else if(boton.equals("C")){
            numero1 = 0.0;
            numero1Vista.setText("0");
        }else if(boton.equals("+/-")){
            String contenido = numero1Vista.getText().toString();
            if(!contenido.equals("0")){
                contenido = "-" + contenido;
            }
            numero1 = new Double(contenido);
            numero1Vista.setText(contenido);
        }else if(boton.equals(".")){
            String contenido = numero1Vista.getText().toString();
            if(!contenido.contains(".")){
                contenido += ".";
            }
            numero1 = new Double(contenido+"0");
            numero1Vista.setText(contenido);
        }else if(boton.equals("+") || boton.equals("-") || boton.equals("*") || boton.equals("/")){
            if(numero2 == 0.0){
                numero2 = new Double(numero1);
                numero1 = 0.0;
                operacion = boton;
                numero2Vista.setText(numero2.toString());
                operacionVista.setText(operacion);
            }else{
                operacion = boton;
                Map<String,String> parametros = new HashMap();
                parametros.put("numero1",numero1.toString());
                parametros.put("numero2",numero2.toString());
                parametros.put("operacion",operacion);
                TextView contenedor = new TextView(this);
                contenedor.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        numero2 = new Double(s.toString());
                        numero1 = 0.0;
                        numero1Vista.setText("0");
                        numero2Vista.setText(numero2.toString());
                        operacionVista.setText(operacion);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                ConsumirSOAP soapwebservice = new ConsumirSOAP(contenedor,parametros);
                soapwebservice.execute(NAMESPACE,METHOD_NAME,WSDL,SOAP_ACTION);
            }
        }else if(boton.equals("=")){
            Map<String,String> parametros = new HashMap();
            parametros.put("numero1",numero1.toString());
            parametros.put("numero2",numero2.toString());
            parametros.put("operacion",operacion);
            TextView contenedor = new TextView(this);
            contenedor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    numero2 = 0.0;
                    numero1 = 0.0;
                    numero1Vista.setText(s.toString());
                    numero2Vista.setText("0");
                    operacionVista.setText("=");
                    operacion = "=";
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            ConsumirSOAP soapwebservice = new ConsumirSOAP(contenedor,parametros);
            soapwebservice.execute(NAMESPACE,METHOD_NAME,WSDL,SOAP_ACTION);
        }else{
            if(operacion.equals("=")){
                numero1Vista.setText(boton);
                numero1 = new Double(boton);
                operacionVista.setText("");
                operacion = "";
            }
            else if(!numero1.toString().equals(new Double(numero1Vista.getText().toString()).toString()) || numero1Vista.getText().toString().equals("0")){
                numero1Vista.setText(boton);
                numero1 = new Double(boton);
                operacionVista.setText("");
            }else{
                String contenido = numero1Vista.getText().toString();
                contenido += boton;
                numero1 = new Double(contenido);
                numero1Vista.setText(contenido);
            }
        }
    }
}
