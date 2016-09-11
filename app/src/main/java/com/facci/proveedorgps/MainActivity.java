package com.facci.proveedorgps;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LocationManager locManager;

    private Double latitud;
    private Double longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        List<String> listaProviders = locManager.getAllProviders();

        LocationProvider provider = locManager.getProvider(listaProviders.get(0));

        int precision = provider.getAccuracy();
        boolean obtieneAltitud = provider.supportsAltitude();
        int consumoRecursos = provider.getPowerRequirement();
        Toast.makeText(MainActivity.this, String.format("Precision : %s \n Altitud : %s \n Consumo Recursos : %s",String.valueOf(precision),String.valueOf(obtieneAltitud),String.valueOf(consumoRecursos)), Toast.LENGTH_LONG).show();

    }

    public void ActualizarCoordenadasClick(View v){

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
        }

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2*60*1000,10,locationListenerGPS);

    }


    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitud = location.getLongitude();
            latitud = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EditText txtLongitud = (EditText) findViewById(R.id.txtLongitud);
                    EditText txtLatitud = (EditText) findViewById(R.id.txtLatitud);
                    txtLongitud.setText(longitud + "");
                    txtLatitud.setText(latitud + "");
                    Toast.makeText(MainActivity.this, "Coordenadas GPS Capturadas", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

}
