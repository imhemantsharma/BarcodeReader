package com.sharma.barcodeexample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.sharma.barcodereader.BarcodeCapture;


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
