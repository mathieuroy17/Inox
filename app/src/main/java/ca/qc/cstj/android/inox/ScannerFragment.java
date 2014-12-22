package ca.qc.cstj.android.inox;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerFragment extends Fragment implements  ZBarScannerView.ResultHandler {
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final String AUTO_FOCUS_STATE = "AUTO_FOCUS_STATE";

    private ZBarScannerView mScannerView;
    private boolean mFlash;
    private boolean mAutoFocus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        mScannerView = new ZBarScannerView(getActivity());
        if(state != null) {
            mFlash = state.getBoolean(FLASH_STATE, false);
            mAutoFocus = state.getBoolean(AUTO_FOCUS_STATE, true);

        } else {
            mFlash = false;
            mAutoFocus = true;

        }
        BarcodeFormat formats = BarcodeFormat.QRCODE;
        return mScannerView;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setHasOptionsMenu(true);
    }

      @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
        mScannerView.getFormats();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
        outState.putBoolean(AUTO_FOCUS_STATE, mAutoFocus);

    }


    @Override
    public void handleResult(Result rawResult) {

       rawResult.getContents(); // Prints scan results

    }






    public void onDialogPositiveClick(DialogFragment dialog) {
        // Resume the camera
        mScannerView.startCamera();
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(mAutoFocus);
    }






    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();


    }



}