package com.artelsv.ruu.Activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artelsv.ruu.Model.GetModel
import com.artelsv.ruu.Model.GetModelList
import com.artelsv.ruu.R
import com.artelsv.ruu.RecyclerViewRuu.RecyclerTouchListener
import com.artelsv.ruu.RecyclerViewRuu.ruuAdapter
import com.artelsv.ruu.RetrofitTools.RetrofitHelper
import com.artelsv.ruu.RetrofitTools.RetrofitTools
import com.artelsv.ruu.Utils.Status
import com.artelsv.ruu.mvvm.GetModelViewModel
import com.artelsv.ruu.mvvm.ViewModelFactory
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.net.InetAddress


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GetModelViewModel

    lateinit var recyclerList: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: ruuAdapter

    //
    private var horizontalDialogProgress = false
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        initViewModel()
        initRecyclerView()
        initDialogs()
        initObserver()
    }

    private fun initViewModel(){
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(RetrofitHelper(RetrofitTools.unsafeRetrofitService))
        ).get(GetModelViewModel::class.java)
    }

    private fun initRecyclerView(){
        recyclerList = findViewById(R.id.recyclerList)
        layoutManager = LinearLayoutManager(this)
        recyclerList.layoutManager = layoutManager

        adapter = ruuAdapter(baseContext, GetModelList(listOf(GetModel())))
        recyclerList.adapter = adapter
        recyclerList.addOnItemTouchListener(object : RecyclerTouchListener(
            this,
            recyclerList,
            object : ClickListener {
                override fun onClick(view: View?, position: Int) {
                    Log.e("test", position.toString())
                    startActivityWithParameters(position)
                }

                override fun onLongClick(view: View?, position: Int) {
                    Log.e("test1", position.toString())
                }
            }) {})
    }

    private fun startActivityWithParameters(position: Int){
        var intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("target", adapter.getModelList().list[position].target)
        intent.putExtra("id", position)
        startActivity(intent)
    }

    private fun initDialogs(){
        dialog = SpotsDialog.Builder().setContext(this).build()
    }

    private fun initObserver(){
        viewModel.getModelList().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerList.visibility = View.VISIBLE
                        resource.data?.let { data -> updateList(data) }
                        hideProgress()
                    }
                    Status.ERROR -> {
                        recyclerList.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        Log.e("error", it.message + "| restart")
                        hideProgress()

                        if (isInternetAvailable()) {
                            initObserver()
                        }
                    }
                    Status.LOADING -> {
                        recyclerList.visibility = View.GONE
                        showProgress()
                    }
                }
            }
        })
    }

    private fun showProgress(){
        if (horizontalDialogProgress){
            dialog.show()
            progressBar.visibility = View.INVISIBLE
        } else {
            dialog.dismiss()
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideProgress(){
        if (horizontalDialogProgress){
            dialog.dismiss()
        } else {
            progressBar.visibility = View.INVISIBLE
        }
    }

    private fun updateList(getModelList: GetModelList) {
        adapter.apply {
            setGetModel(getModelList)
            notifyDataSetChanged()
        }
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }




}