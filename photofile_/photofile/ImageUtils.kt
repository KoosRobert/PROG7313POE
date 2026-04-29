package com.yourpackage.budgettracker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ImageUtils(private val activity: Activity) {

    private var currentPhotoUri: Uri? = null
    private var onImageReceived: ((Uri) -> Unit)? = null

    fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE
        )
    }

    fun showImagePickerOptions(onImagePicked: (Uri) -> Unit) {
        this.onImageReceived = onImagePicked

        val options = arrayOf("Take Photo", "Choose from Gallery")
        AlertDialog.Builder(activity)
            .setTitle("Add Receipt Photo")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    private fun openCamera() {
        val photoFile = createImageFile()
        currentPhotoUri = FileProvider.getUriForFile(
            activity,
            "${activity.packageName}.fileprovider",
            photoFile
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)
        cameraLauncher.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoUri = Uri.fromFile(this)
        }
    }

    private val cameraLauncher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            currentPhotoUri?.let { uri ->
                onImageReceived?.invoke(uri)
                Toast.makeText(activity, "Photo captured", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val galleryLauncher = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                onImageReceived?.invoke(uri)
                Toast.makeText(activity, "Photo selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun showImagePreview(imagePath: String) {
        val imageView = ImageView(activity)
        val uri = Uri.parse(imagePath)

        Glide.with(activity)
            .load(uri)
            .centerCrop()
            .into(imageView)

        AlertDialog.Builder(activity)
            .setTitle("Receipt Preview")
            .setView(imageView)
            .setPositiveButton("Close", null)
            .show()
    }

    companion object {
        const val CAMERA_PERMISSION_CODE = 100
        const val GALLERY_PERMISSION_CODE = 101
    }
}