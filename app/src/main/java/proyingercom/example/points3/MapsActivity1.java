package proyingercom.example.points3;

import android.os.Bundle;
import android.widget.Toast;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity1 extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback , GoogleMap.OnMarkerClickListener {


    private GoogleMap mMap;
    private Marker markerPrueba,InfoWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Latitud Bogotá
       /* LatLng bogota = new LatLng(4.60971, -74.08175);
        mMap.addMarker(new MarkerOptions().position(bogota).title("Bogotá D.C.").snippet("Puntos por compras on line").icon(BitmapDescriptorFactory.fromResource(R.drawable.logopequeno)));*/

       /* LatLng otraciudad = new LatLng(4.60971, -74.08175);
        mMap.addMarker(new MarkerOptions().position(otraciudad).draggable(true).title("City").snippet("").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));*/

        //Latitud Vet Market Calle 68 No. 62-06
        LatLng vetMarket = new LatLng(4.672749,  -74.082440);
        markerPrueba = googleMap.addMarker(new MarkerOptions().position(vetMarket).title("Tienda de mascotas Vet Market" +
                "").icon(BitmapDescriptorFactory.fromResource(R.drawable.logopequeno)));

        //Latitud Soacha
       /* LatLng soacha = new LatLng(4.583,  -74.217);
        InfoWindow =googleMap.addMarker(new MarkerOptions().position(soacha).title("Tienda de mascotas Soacha").icon(BitmapDescriptorFactory.fromResource(R.drawable.logopequeno)));*/

        //Cámara
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vetMarket,7));

        //Eventos

        googleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

        //Ventana con información

        googleMap.setOnInfoWindowClickListener(this);

    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        if(marker.equals(markerPrueba)){
            Toast.makeText(this,"En esta tienda podras acumular puntos por tus compras",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        if (marker.equals(InfoWindow)){

            VetMarketDialogFragment.newInstance(marker.getTitle(), getString(R.string.SoachaInfo)).show(getSupportFragmentManager(),null);
        }

    }
}


