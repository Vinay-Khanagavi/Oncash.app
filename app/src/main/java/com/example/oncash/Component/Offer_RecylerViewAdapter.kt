package com.example.oncash.Component

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.oncash.R
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.oncash.DataType.Offer
import com.example.oncash.DataType.UserData
import com.example.oncash.View.Info
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import kotlin.properties.Delegates


class Offer_RecylerViewAdapter(val userData :UserData) : RecyclerView.Adapter<Offer_RecylerViewAdapter.viewholder>() {
    var offerList : ArrayList<Offer> = ArrayList<Offer>()

    var context : Context?=null
    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView
        val description :TextView
        val price :TextView
        val background :ConstraintLayout
        lateinit var  offerId :String
        init {
            name = itemView.findViewById(com.example.oncash.R.id.offer_name)
            description = itemView.findViewById(com.example.oncash.R.id.offer_desciption)
            price = itemView.findViewById(R.id.offer_price)
            background = itemView.findViewById(R.id.offer_recylerview_background)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        context = parent.context


        val v = LayoutInflater.from(parent.context).inflate(R.layout.offer_recyerview,parent,false)


        return viewholder(v)



    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.offerId = offerList.get(position).OfferId!!
        holder.name.text= offerList.get(position).Name
        holder.description.text = offerList.get(position).Description
        holder.price.text ="Rs "+ offerList.get(position).Price
        val url :URL = URL( offerList.get(position).Image )
        var lastPosition = -1

        var colour  :String = ""
        GlobalScope.launch { withContext(Dispatchers.IO) {
            val background = BitmapFactory.decodeStream(url.openConnection().getInputStream())  ;
            colour =  Integer.toHexString( getDominantColor(background)).substring(2)
            withContext(Dispatchers.Main){
                holder.background.background = linearGradientDrawable(colour)
            }
            }
            }
        val animation = AnimationUtils.loadAnimation(
            context, if (position > lastPosition) {com.example.oncash.R.anim.offeranimation }else {com.example.oncash.R.anim.offeranimationdown}
        )
        holder.itemView.startAnimation(animation)
        lastPosition = position

           // holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context , R.anim.offeranimation)


        holder.itemView.setOnClickListener {
            val offer_information : Offer = offerList.get(holder.offerId.toInt()-1)
            val intent = Intent(
                holder.itemView.context,
                Info::class.java
            ).putExtra("OfferId",offer_information.OfferId )
                .putExtra("OfferName",offer_information.Name)
                .putExtra("OfferImage",offer_information.Image)
                .putExtra("OfferPrice",offer_information.Price)
                .putExtra("OfferDescription",offer_information.Description)
                .putExtra("OfferLink",offer_information.Link)
                .putExtra("subid" , offer_information.subid)
                .putExtra("subid2" , offer_information.payout)
                .putExtra("recordId" , userData.userRecordId)
                .putExtra("number" , userData.userNumber.toString())


            holder.itemView.context.startActivity(
                intent

            )
        }

    }

    override fun getItemCount(): Int {

        return offerList.size

    }

    override fun onViewDetachedFromWindow(holder: viewholder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

    fun updateList(list :ArrayList<Offer>){
        val result =  DiffUtil.calculateDiff( Diffutil(offerList , list))
        this.offerList.clear()
        this.offerList.addAll(list)
        result.dispatchUpdatesTo(this)
    }

    fun getDominantColor(bitmapp: Bitmap?): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmapp!!, 10, 10, true)
        val color = newBitmap.getPixel(9, 9)
        newBitmap.recycle()
        return color
    }

    fun linearGradientDrawable(color: String): GradientDrawable {
        return GradientDrawable().apply {
            if (color.contains("fff"))
            {
                colors = intArrayOf(
                    Color.parseColor("#E6E3D3"),
                    Color.parseColor("#$color")

                )
            }else{
                colors = intArrayOf(
                    Color.parseColor("#$color"),
                    Color.parseColor("#ffffff")

                )
            }

            gradientType = GradientDrawable.LINEAR_GRADIENT
            shape = GradientDrawable.RECTANGLE
            orientation = GradientDrawable.Orientation.BL_TR

            // border around drawable
           // setStroke(5,Color.parseColor("#4B5320"))
        }
    }
}