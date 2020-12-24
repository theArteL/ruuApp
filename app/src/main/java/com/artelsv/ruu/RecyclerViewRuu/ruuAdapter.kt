package com.artelsv.ruu.RecyclerViewRuu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artelsv.ruu.Model.GetModelList
import com.artelsv.ruu.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_layout.view.*

class ruuAdapter(private val context: Context, private var getModelList: GetModelList):RecyclerView.Adapter<ruuAdapter.ruuViewHolder>() {

    class ruuViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txt_id: TextView = itemView.textId
        val txt_target: TextView = itemView.text
        val imageRight: ImageView = itemView.imageView3

        fun bind(getModelList: GetModelList) {
            //для того чтобы клики обрабатывать
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ruuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ruuViewHolder(itemView)
    }

    override fun getItemCount() = getModelList.list.size

    override fun onBindViewHolder(holder: ruuViewHolder, position: Int) {
        val listItem = getModelList.list[position]
        holder.bind(getModelList)

        holder.txt_id.text = "id: "+getModelList.list[position].id.toString()
        holder.txt_target.text = getModelList.list[position].target
        Glide.with(holder.itemView).load(R.drawable.zzz).into(holder.imageRight)
    }

    fun setGetModel(data: GetModelList) {
        getModelList = data
    }

    fun getModelList(): GetModelList{
        return getModelList
    }

}