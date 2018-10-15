package fr.wildcodeschool.queteapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private final static String API_KEY ="b40092bb9a326413dde3e8a9d51ed1f3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crée une file d'attente pour les requêtes vers l'API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // TODO : URL de la requête vers l'API
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Privas&appid=" + API_KEY;

        // Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray list = response.getJSONArray("list");
                            for(int j=0; j<list.length(); j = j + 8) {

                                JSONObject item = list.getJSONObject(j);
                                String date = item.getString("dt_txt");
                                JSONArray weather = item.getJSONArray("weather");
                                for (int i = 0; i < weather.length(); i++) {
                                    JSONObject weatherInfos = (JSONObject) weather.get(i);
                                    String description = weatherInfos.getString("description");
                                    System.out.println("le : "+date+" : "+description);
                                    Toast.makeText(MainActivity.this, "le "+date+" : "+description, Toast.LENGTH_LONG).show();
                                    Log.d("test", "le "+date+" : "+description);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);
    }
}
