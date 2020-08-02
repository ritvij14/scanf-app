package club.devsoc.scanf.api

import android.content.Context
import club.devsoc.scanf.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class DocumentScannerRepository(private val context: Context) {

    private val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    private fun getOutputDirectory(): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir
    }

    fun getPhotoFile(): File {
        return File(
            getOutputDirectory(),
            SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis()) + ".jpg"
        )
    }
}