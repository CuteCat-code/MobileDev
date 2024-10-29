package ru.mirea.bachurinaaa.yandexdriver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.directions.driving.VehicleOptions;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.bachurinaaa.yandexdriver.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements DrivingSession.DrivingRouteListener {
    private ActivityMainBinding binding;
    private final Point ROUTE_END_LOCATION = new Point(55.757131, 37.617114);
    private MapView mapView;
    private MapObjectCollection mapObjects;
    private DrivingRouter drivingRouter;
    private DrivingSession drivingSession;
    private int[] colors = {0xFFFF0000, 0xFF00FF00, 0x00FFBBBB, 0xFF0000FF};

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.initialize(this);
        DirectionsFactory.initialize(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapView = binding.mapview;
        mapView.getMap().setRotateGesturesEnabled(false);

        mapView.getMap().move(new CameraPosition(ROUTE_END_LOCATION, 10, 0, 0));
        mapObjects = mapView.getMap().getMapObjects();
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            initializeUserLocation();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializeUserLocation();
        } else {
            Toast.makeText(this, "Разрешение на доступ к местоположению отклонено", Toast.LENGTH_SHORT).show();
        }
    }

    private UserLocationLayer userLocationLayer;

    private void initializeUserLocation() {
        MapKit mapKit = MapKitFactory.getInstance();
        userLocationLayer = mapKit.createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);

        userLocationLayer.setObjectListener(new UserLocationObjectListener() {
            @Override
            public void onObjectAdded(@NonNull UserLocationView userLocationView) {
                userLocationLayer.setAnchor(
                        new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.5)),
                        new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.83))
                );

                Point userLocation = userLocationView.getPin().getGeometry();
                Log.d("MainActivity", "User Location Added: " + userLocation.getLatitude() + ", " + userLocation.getLongitude());

                if (userLocation.getLatitude() != 0.0 && userLocation.getLongitude() != 0.0) {
                    submitRequest(userLocation);
                } else {
                    Log.e("MainActivity", "Received user location is (0, 0), not submitting request.");
                }
            }

            @Override
            public void onObjectRemoved(@NonNull UserLocationView view) {}

            @Override
            public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent event) {
                Point userLocation = userLocationView.getPin().getGeometry();
                Log.d("MainActivity", "User Location Updated: " + userLocation.getLatitude() + ", " + userLocation.getLongitude());

                if (userLocation.getLatitude() != 0.0 && userLocation.getLongitude() != 0.0) {
                    submitRequest(userLocation);
                } else {
                    Log.e("MainActivity", "Received updated user location is (0, 0), not submitting request.");
                }
            }
        });
    }

    private void submitRequest(Point userLocation) {
        Log.d("MainActivity", "User Location: " + userLocation.getLatitude() + " " + userLocation.getLongitude()); // Log user location
        Log.d("MainActivity", "Route End: " + ROUTE_END_LOCATION.getLatitude() + " " + ROUTE_END_LOCATION.getLongitude()); // Log start and end locations

        DrivingOptions drivingOptions = new DrivingOptions();
        VehicleOptions vehicleOptions = new VehicleOptions();
        drivingOptions.setRoutesCount(4);

        ArrayList<RequestPoint> requestPoints = new ArrayList<>();
        requestPoints.add(new RequestPoint(userLocation, RequestPointType.WAYPOINT, null));
        requestPoints.add(new RequestPoint(ROUTE_END_LOCATION, RequestPointType.WAYPOINT, null));

        drivingSession = drivingRouter.requestRoutes(requestPoints, drivingOptions, vehicleOptions, this);
    }



    @Override
    public void onDrivingRoutes(@NonNull List<DrivingRoute> list) {
        if (list.isEmpty()) {
            Log.e("MainActivity", "No routes found");
            return; // No routes found, exit early
        }

        int color = colors[0];

        // Clear previous map objects
        if (mapObjects != null) {
            mapObjects.clear();
        }

        // Add all routes to the map
        for (DrivingRoute route : list) {
            mapObjects.addPolyline(route.getGeometry()).setStrokeColor(color);
        }

        // Add a marker at the end location
        PlacemarkMapObject marker = mapObjects.addPlacemark(
                ROUTE_END_LOCATION, ImageProvider.fromResource(this, com.yandex.maps.mobile.R.drawable.house_marker_expandable)); // Ensure correct drawable resource
        marker.addTapListener((mapObject, point) -> {
            Toast.makeText(this, "Это ваше любимое заведение!", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public void onDrivingRoutesError(@NonNull Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

}