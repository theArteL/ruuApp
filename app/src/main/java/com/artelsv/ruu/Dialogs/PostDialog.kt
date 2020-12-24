package com.artelsv.ruu.Dialogs

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.artelsv.ruu.R

class PostDialog(listener: OnPostDialogClick) : DialogFragment() {

    private val listener = listener

    private var buttonNo: Button? = null
    private var buttonTakePhotoAgain: Button? = null
    private var buttonYes: Button? = null

    private var editName: EditText? = null
    private var editFam: EditText? = null
    private var editOtch: EditText? = null
    private var editImage: EditText? = null

    interface OnPostDialogClick {
        fun onButtonNoClick()
        fun onButtonTakePhotoAgainClick()
        fun onButtonYesClick(isEditTextFilled: Boolean)
        fun showImageFullSize()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);

        val rootView = inflater.inflate(R.layout.dialog_post, container, false)

        initUI(rootView)

        return rootView
    }

    private fun initUI(rootView: View){
        buttonNo = rootView.findViewById(R.id.button_no)
        buttonNo!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                listener.onButtonNoClick()
                dialog!!.dismiss()
            }
        })

        buttonTakePhotoAgain = rootView.findViewById(R.id.button_takePhotoAgain)
        buttonTakePhotoAgain!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                listener.onButtonTakePhotoAgainClick()
                dialog!!.dismiss()
            }
        })

        buttonYes = rootView.findViewById(R.id.button_yes)
        buttonYes!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                listener.onButtonYesClick(isTextEditFilled())
                if (!isTextEditFilled()) {
                    focusTextEdit()
                } else {
                    dialog!!.dismiss()
                }
            }
        })

        editImage = rootView.findViewById(R.id.edit_image)
        editImage!!.setFocusable(false);
        editImage!!.setClickable(true);
        editImage!!.setInputType(InputType.TYPE_NULL);
        editImage!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                listener.showImageFullSize()
            }
        })

        editName = rootView.findViewById(R.id.edit_name)
        editFam = rootView.findViewById(R.id.edit_fam)
        editOtch = rootView.findViewById(R.id.edit_otch)
    }

    private fun isTextEditFilled(): Boolean{
        var out = false
        out = editName!!.text.isNotEmpty() && editFam!!.text.isNotEmpty() && editOtch!!.text.isNotEmpty()
        return out
    }

    private fun focusTextEdit(){
        editName!!.requestFocus()
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}