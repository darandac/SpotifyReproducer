package com.example.spotify7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.media.MediaPlayer;

public class SegundaActividad extends AppCompatActivity {

    private List<Cancion> albumCanciones;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_actividad);

        // Obtener datos del Intent
        Intent intent = getIntent();
        String albumTitulo = intent.getStringExtra("albumTitulo");
        albumCanciones = intent.getParcelableArrayListExtra("albumCanciones");

        // Mostrar título del álbum
        TextView tituloCancionTextView = findViewById(R.id.tituloCancionTextView);
        tituloCancionTextView.setText(albumTitulo);

        // Mostrar canciones del álbum
        updateViews();
    }

    private void updateViews() {
        LinearLayout container = findViewById(R.id.container);

        for (final Cancion cancion : albumCanciones) {
            View cancionView = getLayoutInflater().inflate(R.layout.layout_cancion, null);

            TextView tituloTextView = cancionView.findViewById(R.id.tituloCancionTextView);
            TextView escritorTextView = cancionView.findViewById(R.id.escritorTextView);
            TextView productorTextView = cancionView.findViewById(R.id.productorTextView);
            TextView duracionTextView = cancionView.findViewById(R.id.duracionCancionTextView);

            tituloTextView.setText("Título: " + cancion.getTitulo());
            escritorTextView.setText("Escritor: " + cancion.getEscritor());
            productorTextView.setText("Productor: " + cancion.getProductor());
            duracionTextView.setText("Duración: " + cancion.getDuracion());

            tituloTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playSong(cancion.getTitulo());
                }
            });

            container.addView(cancionView);
        }
    }

    private void playSong(String title) {
        int resourceId = 0;
        for (Cancion cancion : albumCanciones) {
            if (cancion.getTitulo().equals(title)) {
                resourceId = getResources().getIdentifier(cancion.getTitulo(), "raw", getPackageName());
                break;
            }
        }

        if (resourceId != 0) {
            try {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(this, resourceId);
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

}