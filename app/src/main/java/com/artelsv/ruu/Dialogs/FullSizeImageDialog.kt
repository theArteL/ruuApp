package com.artelsv.ruu.Dialogs

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.artelsv.ruu.R
import com.bumptech.glide.Glide

class FullSizeImageDialog(listener: OnFullSizeImageDialogClick, image: Drawable) : DialogFragment() {
    private val listener = listener
    private var buttonClose: Button? = null
    private var fullSizeImage: ImageView? = null

    private val image = image

    interface OnFullSizeImageDialogClick {
        fun onButtonCloseClick()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.dialog_full_size_image, container, false)

        initUI(rootView)

        return rootView
    }

    private fun initUI(rootView: View){
        buttonClose = rootView.findViewById(R.id.button)
        buttonClose!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                listener.onButtonCloseClick()
                dialog!!.dismiss()
            }
        })

        fullSizeImage = rootView.findViewById(R.id.imageView2)

        Glide.with(this).load(image).into(fullSizeImage!!)
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.setLayout(width, height)
    }
}