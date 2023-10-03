package com.example.veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView etlinkRegistrar;
    Button btIniciarSesion;
    EditText etDni, etClave;
    String dni , clave;

    int idCliente;

    final String URL = "http://192.168.1.36/Wveterinaria/controllers/usuario.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadUI();
        etlinkRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarRegistro();
            }
        });

        btIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresar();

            }
        });
    }


    private void ingresar(){

        dni = etDni.getText().toString().trim();
        clave = etClave.getText().toString().trim();

        Uri.Builder URLFULL = Uri.parse(URL).buildUpon();
        URLFULL.appendQueryParameter("op","login");
        URLFULL.appendQueryParameter("dni", dni);
        URLFULL.appendQueryParameter("claveacceso", clave);

        String URLUpdate = URLFULL.build().toString();

        if(etDni.getText().toString().equals("")){

            Toast.makeText(getApplicationContext(), "Ingrese su dni", Toast.LENGTH_SHORT).show();
        }
        else if (etClave.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Ingrese su clave", Toast.LENGTH_SHORT).show();

        } else {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLUpdate, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                 if(response != null){
                     try {

                         boolean sesion = response.getBoolean("sesion");
                         String mensaje = response.getString("mensaje");

                         if(sesion){

                             Toast.makeText(getApplicationContext(), "Inicio correctamente", Toast.LENGTH_SHORT).show();
                             idCliente = response.getInt("idcliente");
                             mostrarInicio(idCliente);

                         }else {
                             Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                         }


                     }catch(JSONException e){
                         e.printStackTrace();
                         Log.d("Error", e.toString());

                     }
                 }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(), "Error de red"+ error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            Volley.newRequestQueue(this).add(jsonObjectRequest);

        }
    }


    private void mostrarInicio(int idCliente){
        Intent intent = new Intent(getApplicationContext(), Principal.class );
        intent.putExtra("idcliente", idCliente);
        startActivity(intent);
    }

    private void mostrarRegistro(){
        Intent intent = new Intent(getApplicationContext(), Registro_clientes.class );
        startActivity(intent);
    }

    private void loadUI(){
        etlinkRegistrar = findViewById(R.id.etlinkRegistrar);
        etDni = findViewById(R.id.etDni);
        etClave = findViewById(R.id.etClave);
        btIniciarSesion = findViewById(R.id.btIniciarSesion);
    }
}