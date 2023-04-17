package com.fanpower.lib.adapter

import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.fanpower.lib.R


import com.fanpower.lib.api.model.Pick
import com.fanpower.lib.api.model.PickForAdapter
import com.fanpower.lib.interfaces.OnClickCallBack
import com.fanpower.lib.utils.SharedPrefs
import com.fanpower.lib.utils.Utilities

class AnswerListAdapter(
    private val mList: ArrayList<PickForAdapter>,
    private val onClickCallBack: OnClickCallBack,
    activity: Activity
) : RecyclerView.Adapter<AnswerListAdapter.ViewHolder>() {

    private val TAG = "AnswerListAdapter"

    var answerMode: Boolean = false
    var activity: Activity
    var selectedIndex: Int = -1
    var mainViewWidth = 0



    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.answer_list_item, parent, false)

        return ViewHolder(view)
    }

    init {
        this.activity = activity
    }

    fun setAnswerModeView(answerMode: Boolean) {
        this.answerMode = answerMode
        notifyDataSetChanged()
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]
        val onClickCallBack = onClickCallBack

        val intPartPercentage = itemsViewModel.pick.pick_popularity.toInt()

        val percString = intPartPercentage.toString().plus("%")
        holder.percentageText.setText(percString)

        holder.main.post(Runnable {
            mainViewWidth = holder.main.width //width is ready
//            Log.i(TAG, "onBindViewHolder: width of mian view is " + width)
//            val percentWidth = (width / 100) * intPartPercentage
//            Log.i(TAG, "onBindViewHolder: percent width " + percentWidth)
//            Log.i(TAG, "onBindViewHolder: px to dp " + Utilities.pxToDp(percentWidth))
//
//            holder.percentageView.getLayoutParams().width = 150;

        })

//        holder.percentageView.post {
//            //   holder.percentageView.translationX = -1000f
//            holder.percentageView.translationX = (((holder.percentageView.width.toFloat() / 100f) * itemsViewModel.pick_popularity.toFloat()) - holder.percentageView.width.toFloat())
//
//        }

        if(answerMode) {
            holder.percentageView.post {
                if(!itemsViewModel.hasDoneAnimation) {
                    Log.i(TAG, "onBindViewHolder: in animation item " + position)
                    holder.percentageView.animate()
                        .translationX(((holder.percentageView.width.toFloat() / 100f) * itemsViewModel.pick.pick_popularity.toFloat()) - holder.percentageView.width.toFloat())
                        .setDuration(800)
                }else{
                    Log.i(TAG, "onBindViewHolder: without animation item " + position)
                   // holder.percentageView.clearAnimation().translationX = ((holder.percentageView.width.toFloat() / 100f) * itemsViewModel.pick.pick_popularity.toFloat()) - holder.percentageView.width.toFloat()
                    holder.percentageView.animate()   .translationX(((holder.percentageView.width.toFloat() / 100f) * itemsViewModel.pick.pick_popularity.toFloat()) - holder.percentageView.width.toFloat())
                        .setDuration(0)
                }
            }
        }

        Log.i(TAG, "onBindViewHolder: answer mode  " + answerMode)

        if (answerMode) {
            Log.i(TAG, "onBindViewHolder: answer mode selected index " + selectedIndex)
            holder.percentageText.visibility = View.VISIBLE
            holder.percentageView.visibility = View.VISIBLE

            if (position == selectedIndex) {
                holder.percentageView.setBackgroundColor(SharedPrefs.Utils.getSecondaryColor(activity))
         //        holder.main.setBackgroundResource(R.drawable.yellow_rounded_not_filled)
                Utilities.getPickerStrokeBackground(holder.main,0,SharedPrefs.Utils.getPrimaryColor(activity))

                holder.textView.setTextColor(SharedPrefs.Utils.getPrimaryColor(activity))
                holder.percentageText.setTextColor(SharedPrefs.Utils.getPrimaryColor(activity))

            } else {
                holder.percentageView.setBackgroundColor(ContextCompat.getColor(activity,R.color.answer_list_percentage_grey))
        //        holder.main.setBackgroundResource(R.drawable.black_rounded_not_filled)
                Utilities.getPickerStrokeBackground(holder.main,0,R.color.answer_black_border)
                holder.textView.setTextColor(SharedPrefs.Utils.getPrimaryColor(activity))
                holder.percentageText.setTextColor(SharedPrefs.Utils.getPrimaryColor(activity))
            }
//            val percentWidth = (mainViewWidth / 100) * intPartPercentage
//            holder.percentageView.getLayoutParams().width = 150
        } else {
            holder.percentageText.visibility = View.GONE
            holder.percentageView.visibility = View.GONE
            holder.textView.setTextColor(SharedPrefs.Utils.getPrimaryColor(activity))

            Utilities.getPickerStrokeBackground(holder.main,0,R.color.answer_black_border)
      //      holder.main.setBackgroundResource(R.drawable.black_rounded_not_filled)

//            holder.main.background.setColorFilter(SharedPrefs.Utils.getPrimaryColor(activity),
//                PorterDuff.Mode.SRC_OVER)
        }

        // sets the text to the textview from our itemHolder class
        holder.textView.text = itemsViewModel.pick.display_title

        holder.main.setOnClickListener {
            if(selectedIndex == -1) {
                onClickCallBack.onClick(itemsViewModel.pick)
                selectedIndex = position

                Handler().postDelayed({
                    for(x in mList){
                        Log.i(TAG, "onBindViewHolder: in delay")
                        x.hasDoneAnimation= true
                    }
                },1000)


            }



//            var animation = AnimationUtils.loadAnimation(activity,R.anim.left_to_right_anim)
//            holder.percentageView.startAnimation(animation)

        }
    }



    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        // val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.title)
        val percentageText: TextView = itemView.findViewById(R.id.percentage)
        val percentageView: View = itemView.findViewById(R.id.percentageProgressView)
        val main: RelativeLayout = itemView.findViewById(R.id.main)
    }
}