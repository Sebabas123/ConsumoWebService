package ipn.fernandodanielcastillobarron.a5im7.consumirwebservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//menu : actividad intermedia que abre la calculadora y las divisas
public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    //metodo para dirigirse a la calculadora
    public void calculadora(View o){
        Intent intento = new Intent(this,Calculadora.class);
        startActivity(intento);
    }

    //metodo para dirigirse a las divisas
    public void convertidorDivisas(View o){
        Intent intento = new Intent(this,Divisas.class);
        startActivity(intento);
    }
}
