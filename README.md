# BarcodeReader using Google Mobile Vision

BarcodeReader library used Google Mobile Vision, By adding some extra functionality in "Google Mobile Vision" sample barcode reader library is created.

![portrait](https://user-images.githubusercontent.com/37363651/44333590-d32b3100-a48c-11e8-9e1a-4ca0e11aa587.png)  ![landscape](https://user-images.githubusercontent.com/37363651/44333508-9c551b00-a48c-11e8-9e9f-67f704217104.png)


Implementation Guide :
-----------------------------------------

1. Adding BarcodeReader dependency in **build.gradle** (Module: app)
```gradle
dependencies {
    // barcode reader
    implementation 'com.sharma:Barcode:1.0.0'
}
```
Implementation Guide in Activity :
-----------------------------------------

1. Adding BarcodeReader fragment to your Activity XML
```xml
<!-- Barcode Reader fragment -->
    <fragment
        android:id="@+id/barcode_fragment"
        android:name="com.sharma.barcodereader.BarcodeCapture"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:auto_focus="true"
        app:use_flash="false" />
```

2. Using BarcodeReader in Activity

```java
public class MainActivity extends AppCompatActivity implements BarcodeCapture.BarcodeCaptureListener {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA=100;
    private BarcodeCapture barcodeCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check camera permission
        if (requestCameraPermission()) initBarcodeReader();
    }

    private void initBarcodeReader() {
        // getting barcode instance
        barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
    }

    @Override
    public void onBarcodeDetected(final Barcode barcode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initBarcodeReader();
                } else {
                    Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
```

Implementation Guide in Fragment :
-----------------------------------------

1. Adding BarcodeReader fragment to your Fragment XML

```xml
   <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- Barcode Reader fragment -->
    <fragment
        android:id="@+id/barcode_fragment"
        android:name="com.sharma.barcodereader.BarcodeCapture"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:auto_focus="true"
        app:use_flash="false" />

</RelativeLayout>
```

2. Using BarcodeReader in Fragment

```java

public class MainFragment extends Fragment implements BarcodeCapture.BarcodeCaptureListener {

    private Activity activity;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBarcodeReader();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    private void initBarcodeReader() {
        // getting barcode instance
        BarcodeCapture barcodeCapture = (BarcodeCapture) getChildFragmentManager().findFragmentById(R.id.barcode_fragment);
        barcodeCapture.setBarcodeListener(this);
    }

    @Override
    public void onBarcodeDetected(final Barcode barcode) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

```
