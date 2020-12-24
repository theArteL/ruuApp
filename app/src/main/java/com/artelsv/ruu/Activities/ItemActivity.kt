package com.artelsv.ruu.Activities

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.artelsv.ruu.Dialogs.FullSizeImageDialog
import com.artelsv.ruu.Dialogs.PostDialog
import com.artelsv.ruu.Model.ImagePostResponse
import com.artelsv.ruu.R
import com.artelsv.ruu.RetrofitTools.RetrofitHelper
import com.artelsv.ruu.RetrofitTools.RetrofitTools
import com.artelsv.ruu.mvvm.SendDataViewModel
import com.artelsv.ruu.mvvm.SendDataViewModelFactory
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_item.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class ItemActivity : AppCompatActivity(){

    lateinit var postDialog: PostDialog
    lateinit var fullSizeImageDialog: FullSizeImageDialog
    private var responseText: TextView? = null

    private var id: Int = 0

    private lateinit var viewModel: SendDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        initUI(intent)
        hideProgress()
        initViewModel()
    }

    fun initUI(extras: Intent){
        textView_target_text.text = extras.getStringExtra("target")
        id = extras.getIntExtra("id", 0)
        Glide.with(this).load(R.drawable.back).into(imageView)
        responseText = findViewById(R.id.textView_response_text)
    }

    fun initViewModel(){
        viewModel = ViewModelProviders.of(
            this,
            SendDataViewModelFactory(RetrofitHelper(RetrofitTools.unsafeRetrofitService))
        ).get(SendDataViewModel::class.java)
    }

    private fun sendImage(_action: RequestBody,_id: RequestBody, image: MultipartBody.Part, contacts: Map<String, String>){
        val service = RetrofitTools.unsafeRetrofitService
        Log.e("teeta", image.body.contentType().toString())
        Log.e("teeta", image.body.contentLength().toString())

        showProgress()

        service.postData2(_action, _id, image).enqueue(object : Callback<ImagePostResponse> {
            override fun onResponse(
                call: Call<ImagePostResponse>,
                response: Response<ImagePostResponse>
            ) {
                Log.e("test1", response.body()!!.status)
                button_takePhoto.isEnabled = true
                updateResponseText(response.body()!!.status)
                hideProgress()
            }
            override fun onFailure(call: Call<ImagePostResponse>, t: Throwable) {
                Log.e("test2", t.message)
                button_takePhoto.isEnabled = true
                hideProgress()
                Toast.makeText(this@ItemActivity, t.message, Toast.LENGTH_LONG)
            }
        })
    }

    private fun showProgress(){
        progressBar2.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        progressBar2.visibility = View.INVISIBLE
    }

    private fun updateResponseText(str: String){
        responseText!!.text = str
    }

    fun takePhoto(view: View) {
        dispatchTakePictureIntent()
    }

    private val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Log.e("TakePhotoError", e.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            val path = saveImage(imageBitmap).path
            Log.e("test", path)
            postDialog = PostDialog(object : PostDialog.OnPostDialogClick {
                override fun onButtonNoClick() {
                    //postDialog.dismiss()
                }

                override fun onButtonTakePhotoAgainClick() {
                    dispatchTakePictureIntent()
                }

                override fun onButtonYesClick(isEditTextFilled: Boolean, view: View) {
                    if (isEditTextFilled) {
                        val _action =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), "send_data")
                        val _id =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), id.toString())
                        sendImage(
                            _action,
                            _id,
                            loadImage(path!!),
                            mapOf("name" to "test", "surname" to "test", "patronymic" to "test")
                        )
                        val snackBar = Snackbar.make(
                            responseText!!.rootView, "Данные отправлены на сервер",
                            Snackbar.LENGTH_LONG
                        ).setAction("Action", null)
                        snackBar.setActionTextColor(Color.WHITE)
                        val snackBarView = snackBar.view
                        snackBarView.setBackgroundColor(Color.rgb(28, 28, 31))
                        val textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                        textView.setTextColor(Color.WHITE)
                        snackBar.show()
                    } else {
                        Toast.makeText(this@ItemActivity, "Заполните все поля", Toast.LENGTH_SHORT)
                    }
                }

                override fun showImageFullSize() {
                    postDialog.dismiss()
                    fullSizeImageDialog = FullSizeImageDialog(object : FullSizeImageDialog.OnFullSizeImageDialogClick{
                        override fun onButtonCloseClick() {
                            postDialog.show(supportFragmentManager, "postDialog")
                        }
                    }, BitmapDrawable(resources, imageBitmap))
                    fullSizeImageDialog.show(supportFragmentManager, "fullSizeImageDialog")
                }

            })
            postDialog.show(supportFragmentManager, "postDialog")
            button_takePhoto.isEnabled = false

        }
    }

    private fun loadImage(path: String): MultipartBody.Part{
        val file = File(path)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "image", file.getName(), requestFile)
        return filePart
    }

    private fun saveImage(bitmap: Bitmap): Uri{
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir("image", Context.MODE_PRIVATE)

        file = File(file, "image.png")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException){
            Log.e("TakePhotoError", e.toString())
        }

        return Uri.parse(file.absolutePath)
    }
}