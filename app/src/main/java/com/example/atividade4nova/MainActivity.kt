package com.example.atividade4nova

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val PICK_IMAGE = 111
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btShare.setOnClickListener { send(txtShare.text.toString()) }
        btPickImage.setOnClickListener { pickImage() }

    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val shareIntent = Intent.createChooser(intent, "Select a Image")
        startActivityForResult(shareIntent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == PICK_IMAGE) {
            val inputStream = contentResolver.openInputStream(data?.data!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            sharerImage(bitmap)
        }

        super.onActivityResult(requestCode, resultCode, data)

    }

    fun sharerImage(bitmap: Bitmap?) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("image/*")
        val path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null)
        val imageUri = Uri.parse(path)
        intent.putExtra(Intent.EXTRA_STREAM, imageUri)
        startActivity(Intent.createChooser(intent, "Select"))
    }

    private fun send(message: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(EXTRA_TEXT, message)
            type = "text/plain"

        }
        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }

}
