package com.kanneki.moneybook.ui.scanner

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.gson.Gson
import com.kanneki.moneybook.R
import com.kanneki.moneybook.activityresult.contract.ScannerResultContract
import com.kanneki.moneybook.base.BaseActivity
import com.kanneki.moneybook.databinding.ActivityScannerBinding
import kotlinx.android.synthetic.main.activity_scanner.*
import kotlinx.android.synthetic.main.activity_scanner.view.*
import java.io.IOException

class ScannerActivity: BaseActivity<ScannerViewModel, ActivityScannerBinding>() {


    private val barcodeDetector: BarcodeDetector by lazy {
        BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build()
    }

    val cameraSource: CameraSource by lazy {
        CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1000, 1000)
                .setAutoFocusEnabled(true)
                .build()
    }

    private val requestPermission = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
    ){type ->
        if (type){ setQRCode() }
    }

    override val viewModel: ScannerViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.activity_scanner

    override fun initDataBinding() {
        super.initDataBinding()
        requestPermission.launch(Manifest.permission.CAMERA)
    }

    private fun setQRCode(){
        dataBinding.root.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(p0: SurfaceHolder) {
                if (ActivityCompat.checkSelfPermission(
                                applicationContext,
                                Manifest.permission.CAMERA
                        ) != PackageManager.PERMISSION_GRANTED){

                    return
                }
                try {
                    cameraSource.start(p0)
                }catch (e :IOException){
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {

            }

            override fun receiveDetections(p0: Detector.Detections<Barcode>) {
                // TODO
                val qrCodes: SparseArray<Barcode> = p0.detectedItems
                if (qrCodes.size() != 0){
                    Log.d("TAG", qrCodes.valueAt(0).displayValue)
                    val data = viewModel.StringToModel(qrCodes.valueAt(0).displayValue)
                    data.receipt?.let {
                        val intent = Intent().apply {
                            putExtra(ScannerResultContract.RESULT_CODE, Gson().toJson(data))
                        }
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                }
            }
        })
    }

}