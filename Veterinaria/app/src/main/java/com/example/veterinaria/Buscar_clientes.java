package com.example.veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Buscar_clientes extends AppCompatActivity {
    EditText etCodDni, etNombreB, etApellidoB, etNombreMascotaB, etRazaB, etColorB, etGeneroB;
    Button btConsultar;

    final String URL = "http://192.168.1.36/Wveterinaria/controllers/veterinaria.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_clientes);

        loadUI();

        btConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarCliente();
            }
        });
    }

    private void buscarCliente(){
        String codDni = etCodDni.getText().toString().trim();

        if(codDni.isEmpty()){
            Toast.makeText(getApplicationContext(), "Escriba su numero de DNI por favor", Toast.LENGTH_SHORT).show();

        }else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);

                        if(jsonObject.has("nombres") && jsonObject.has("apellidos") && jsonObject.has("nombreraza") &&
                                jsonObject.has("nombre") && jsonObject.has("color") && jsonObject.has("nombres")){

                            //OBTENIENDO LOS DATOS DEL JSON
                            String nombres = jsonObject.getString("nombres");
                            String apellidos = jsonObject.getString("apellidos");
                            String nombreraza = jsonObject.getString("nombreraza");
                            String nombre = jsonObject.getString("nombre");
                            String color = jsonObject.getString("color");
                            String genero = jsonObject.getString("genero");

                            //LLENAMOS LOS EDITTEXT CON LOS DATOS OBTENIDOS
                            etNombreB.setText(nombres);
                            etApellidoB.setText(apellidos);
                            etNombreMascotaB.setText(nombre);
                            etRazaB.setText(nombreraza);
                            etColorB.setText(color);
                            etGeneroB.setText(genero);
                        }else {
                            Toast.makeText(getApplicationContext(), "No hay datos", Toast.LENGTH_SHORT).show();
                        }


                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error", error.toString());
                        }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("op","buscarCliente");
                    parametros.put("dni", codDni);
                    return parametros;
                }

            };

            Volley.newRequestQueue(this).add(stringRequest);

        }

    }
    private void loadUI(){
        etCodDni = findViewById(R.id.etCodDni);
        etNombreB = findViewById(R.id.etNombreB);
        etApellidoB = findViewById(R.id.etApellidoB);
        etNombreMascotaB = findViewById(R.id.etNombreMascotaB);
        etRazaB = findViewById(R.id.etRazaB);
        etColorB = findViewById(R.id.etColorB);
        etGeneroB = findViewById(R.id.etGeneroB);
        btConsultar = findViewById(R.id.btConsultar);

    }
}