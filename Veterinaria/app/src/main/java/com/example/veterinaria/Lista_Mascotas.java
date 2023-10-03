package com.example.veterinaria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lista_Mascotas extends AppCompatActivity {

    ListView lvMascotas;

    private List<String> datalist = new ArrayList<>();

    private CustomAdapter adapter;

    final String URL = "http://192.168.1.36/Wveterinaria/controllers/veterinaria.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);

        loadUI();
        getData();
    }


    private void getData(){
        datalist.clear();
        adapter = new CustomAdapter(this, datalist);
        lvMascotas.setAdapter(adapter);

        Uri.Builder newUrl = Uri.parse(URL).buildUpon();
        newUrl.appendQueryParameter("op", "getMascotas");
        String url_UPDATE = newUrl.build().toString();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_UPDATE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for (int i=0 ; i<response.length(); i++){
                        JSONObject jsonObject = new JSONObject(response.getString(i));

                        String dueño = jsonObject.getString("cliente");
                        String mascota = jsonObject.getString("nombre");
                        String item = "Dueño: " + dueño + "                                         "
                                + "Mascota: " + mascota;
                        datalist.add(item);
                    }

                    adapter.notifyDataSetChanged();

                    // Imprimir el contenido de datalist en el Logcat para verificar
                    for (String item : datalist) {
                        Log.d("DataList", item);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Errorxd", error.toString());

            }
        });
        //Enviamos el request(requerimiento)
        Volley.newRequestQueue(this).add(jsonArrayRequest);


    }


    private void loadUI(){
        lvMascotas = findViewById(R.id.lvMascotas);
    }
}