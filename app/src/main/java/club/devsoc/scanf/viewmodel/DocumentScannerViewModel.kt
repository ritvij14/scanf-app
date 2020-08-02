package club.devsoc.scanf.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import club.devsoc.scanf.api.DocumentScannerRepository

class DocumentScannerViewModel(application: Application, private val context: Context) : AndroidViewModel(application) {

    private val TAG = "CameraX_BASIC"

    private lateinit var documentScannerRepository: DocumentScannerRepository
    fun init() {
        documentScannerRepository = DocumentScannerRepository(context)
    }

    fun captureImage(imageCapture: ImageCapture?) {
        // Get a stable reference of the modifiable image capture use case
        val imageCaptureRef = imageCapture ?: return

        // Create timestamped output file to hold the image
        val photoFile = documentScannerRepository.getPhotoFile()

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Setup image capture listener which is triggered after photo has been taken
        imageCaptureRef.takePicture(
            outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            })
    }
}