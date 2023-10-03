package com.example.veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Registro_mascota extends AppCompatActivity {

    EditText etNombreMascota, etFotografia , etColor;

    String nombreMascota, fotografia, color;

    Spinner spinnerRaza;
    Button btRegistrar;
    RadioButton rbMacho, rbHembra;
    Boolean macho, hembra;
    String genero;

    int idCliente;
    int idRaza;

    final String URL = "http://192.168.1.36/Wveterinaria/controllers/veterinaria.php";

    Map<String, Integer> razasMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);


        // Obtener el ID del cliente del Intent
        Intent intent = getIntent();
        idCliente = intent.getIntExtra("idcliente", -1);


        loadUI();
        razasMap.put("Seleccione", 0);
        razasMap.put("Pastor alemán", 1);
        razasMap.put("Pitbull", 2);

        // Crea un adaptador simple para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(razasMap.keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerRaza.setAdapter(adapter);

        // Selección del Spinner
        spinnerRaza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRaza = (String) parent.getItemAtPosition(position);
                idRaza = razasMap.get(selectedRaza);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCajas();
            }
        });
    }

    private  void validarCajas(){
        nombreMascota = etNombreMascota.getText().toString().trim();
        fotografia = etFotografia.getText().toString().trim();
        color = etColor.getText().toString().trim();
        macho = rbMacho.isChecked();
        hembra = rbHembra.isChecked();

        //Asignar el valor al rb seleccionado
        if(macho){
            genero = "Macho";
        }else if(hembra) {
            genero = "Hembra";
        }

        if(nombreMascota.isEmpty()){
            etNombreMascota.setError("Complete el campo");
        } else if (fotografia.isEmpty()) {
            etFotografia.setError("Complete el campo");

        } else if (color.isEmpty()) {
            etColor.setError("Complete el campo");

        }else {
            mostrarDialog();
        }




    }


    private void mostrarDialog(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Registro mascotas");
        dialogo.setMessage("¿Está seguro de registrarse?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrarMascota();

            }
        });

        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialogo.show();
    }


    private void registrarMascota(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equalsIgnoreCase("")) {
                    resetUI();
                    Toast.makeText(getApplicationContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("op", "addMascotas");
                parametros.put("idcliente", String.valueOf(idCliente));
                parametros.put("idraza", String.valueOf(idRaza));
                parametros.put("nombre", nombreMascota);
                parametros.put("fotografia", fotografia);
                parametros.put("color", color);
                parametros.put("genero", genero);

                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void resetUI() {

        etNombreMascota.setText("");
        etFotografia.setText("");
        etColor.setText("");
        spinnerRaza.setSelection(0);
        rbMacho.setChecked(false);
        rbHembra.setChecked(false);
    }




    private void loadUI(){
        etNombreMascota = findViewById(R.id.etNombreMascota);
        etFotografia = findViewById(R.id.etfotografia);
        etColor = findViewById(R.id.etColor);
        spinnerRaza = findViewById(R.id.spinner_Raza);
        btRegistrar = findViewById(R.id.btRegistrar);
        rbMacho = findViewById(R.id.rbMacho);
        rbHembra = findViewById(R.id.rbHembra);
    }
}