package com.example.android_proyecto.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_proyecto.Adapters.EventsAdapter;
import com.example.android_proyecto.Models.Event;
import com.example.android_proyecto.Models.RegisterResponse;
import com.example.android_proyecto.R;
import com.example.android_proyecto.RetrofitClient;
import com.example.android_proyecto.Services.ApiService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsActivity extends AppCompatActivity {

    private RecyclerView recyclerEvents;
    private EventsAdapter adapter;
    private ApiService api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        api = RetrofitClient.getApiService();

        recyclerEvents = findViewById(R.id.recyclerEvents);
        recyclerEvents.setLayoutManager(new LinearLayoutManager(this));

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        adapter = new EventsAdapter(this::registerToEvent);
        recyclerEvents.setAdapter(adapter);

        loadEvents();
    }

    private void loadEvents() {
        api.getEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setEvents(response.body());
                } else {
                    Toast.makeText(EventsActivity.this,
                            "Could not load Events (" + response.code() + ")",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(EventsActivity.this,
                        "Connection error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerToEvent(Event event) {
        api.registerToEvent(event.getId()).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                // OK (200)
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(EventsActivity.this,
                            response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Error (ej: 404) -> backend devuelve JSON {ok:false, message:"El evento X no existe"}
                String msg = parseErrorMessage(response.errorBody());
                if (msg == null) msg = "Could not register (" + response.code() + ")";
                Toast.makeText(EventsActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(EventsActivity.this,
                        "Connection error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String parseErrorMessage(ResponseBody errorBody) {
        if (errorBody == null) return null;
        try {
            String json = errorBody.string();
            RegisterResponse rr = new Gson().fromJson(json, RegisterResponse.class);
            if (rr != null && rr.getMessage() != null && !rr.getMessage().isEmpty()) {
                return rr.getMessage();
            }
        } catch (IOException ignored) {
        }
        return null;
    }
}
