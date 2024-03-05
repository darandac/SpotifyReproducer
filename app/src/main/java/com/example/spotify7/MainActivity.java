package com.example.spotify7;

// Importación de clases y paquetes necesarios
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables miembro
    private TextView titleTextView;
    private ImageView albumImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Establecer el layout de la actividad

        // Inicialización de vistas
        titleTextView = findViewById(R.id.titleTextView);
        albumImageView = findViewById(R.id.albumImageView);

        leerXML(); // Llamar al método para leer el archivo XML
    }

    // Método para leer el archivo XML
    private void leerXML() {
        // Obtener el XMLPullParser a partir del archivo spotify.xml
        XmlPullParser parser = getResources().getXml(R.xml.spotify);
        List<Album> albumList = new ArrayList<>(); // Lista para almacenar los álbumes

        try {
            String titulo = "";
            String imagen = "";
            List<Cancion> listaCanciones = new ArrayList<>(); // Lista temporal para las canciones

            // Iterar a través del XML
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG) {
                    String tagName = parser.getName();
                    if (tagName.equals("album")) {
                        titulo = "";
                        listaCanciones.clear(); // Limpiar la lista de canciones para el nuevo álbum
                    } else if (tagName.equals("titulo")) {
                        titulo = parser.nextText(); // Leer el título del álbum
                    } else if (tagName.equals("imagen")) {
                        imagen = parser.nextText(); // Leer la imagen del álbum
                    } else if (tagName.equals("cancion")) {
                        // Leer los detalles de la canción
                        String cancionTitulo = "";
                        String escritor = "";
                        String productor = "";
                        String duracion = "";

                        while (parser.next() != XmlPullParser.END_TAG) {
                            if (parser.getEventType() == XmlPullParser.START_TAG) {
                                String innerTagName = parser.getName();
                                switch (innerTagName) {
                                    case "titulo_cancion":
                                        cancionTitulo = parser.nextText();
                                        break;
                                    case "escritor":
                                        escritor = parser.nextText();
                                        break;
                                    case "productor":
                                        productor = parser.nextText();
                                        break;
                                    case "duracion_cancion":
                                        duracion = parser.nextText();
                                        break;
                                }
                            }
                        }

                        // Crear instancia de Cancion y agregarla a la lista de canciones
                        Cancion cancion = new Cancion(cancionTitulo, escritor, productor, duracion);
                        listaCanciones.add(cancion);
                    }
                } else if (parser.getEventType() == XmlPullParser.END_TAG && parser.getName().equals("album")) {
                    // Crear instancia de Album y agregarla a la lista de álbumes
                    albumList.add(new Album(titulo, imagen, new ArrayList<>(listaCanciones)));
                }
            }

            updateViews(albumList); // Llamar al método para actualizar las vistas

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar las vistas con los datos obtenidos del XML
    private void updateViews(List<Album> albumList) {
        LinearLayout container = findViewById(R.id.container);

        // Iterar a través de la lista de álbumes
        for (final Album album : albumList) {
            // Crear una vista para el álbum a partir del layout 'album_layout.xml'
            View albumView = getLayoutInflater().inflate(R.layout.album_layout, null);

            // Obtener referencias a las vistas dentro del layout del álbum
            TextView titleTextView = albumView.findViewById(R.id.titleTextView);
            final ImageView albumImageView = albumView.findViewById(R.id.albumImageView);

            titleTextView.setText(album.getTitulo()); // Establecer el título del álbum en el TextView

            // Obtener el ID del recurso drawable utilizando su nombre
            int resourceId = getResources().getIdentifier(album.getImagen(), "drawable", getPackageName());

            // Verificar si se encontró el recurso y establecer la imagen en el ImageView
            if (resourceId != 0) {
                albumImageView.setImageResource(resourceId);
            } else {
                // Manejar el caso en que no se encuentra la imagen
            }

            container.addView(albumView); // Agregar la vista del álbum al contenedor

            // Agregar listener de clics a la imagen del álbum
            albumImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crear un Intent para abrir la SegundaActividad y pasar los datos del álbum
                    Intent intent = new Intent(MainActivity.this, SegundaActividad.class);
                    intent.putExtra("albumTitulo", album.getTitulo());
                    intent.putExtra("albumImagen", album.getImagen());
                    intent.putParcelableArrayListExtra("albumCanciones", new ArrayList<>(album.getListaCanciones()));
                    startActivity(intent); // Iniciar la SegundaActividad
                }
            });

            // Agregar listener de clics al título del álbum
            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crear un Intent para abrir la SegundaActividad y pasar los datos del álbum
                    Intent intent = new Intent(MainActivity.this, SegundaActividad.class);
                    intent.putExtra("albumTitulo", album.getTitulo());
                    intent.putExtra("albumImagen", album.getImagen());
                    intent.putParcelableArrayListExtra("albumCanciones", new ArrayList<>(album.getListaCanciones()));
                    startActivity(intent); // Iniciar la SegundaActividad
                }
            });
        }
    }
}
