package com.nsw2022.favdbtest

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import java.lang.Exception
import java.util.ArrayList

class CoffeeAdapter(coffeeItems: ArrayList<CoffeeItem>, context: Context) :
    RecyclerView.Adapter<CoffeeAdapter.ViewHolder?>() {
    private val coffeeItems: ArrayList<CoffeeItem>
    private val context: Context
    private var favDB: FavDB? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        favDB = FavDB(context)
        //create table on first
        val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val firstStart: Boolean = prefs.getBoolean("firstStart", true)
        if (firstStart) {
            createTableOnFirstStart()
        }
        val view: View = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.item,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coffeeItem: CoffeeItem = coffeeItems[position]
        readCursorData(coffeeItem, holder)
        holder.imageView.setImageResource(coffeeItem.getImageResourse())
        holder.titleTextView.setText(coffeeItem.getTitle())
    }

    val itemCount: Int
        get() = coffeeItems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var titleTextView: TextView
        var likeCountTextView: TextView
        var favBtn: Button

        init {
            imageView = itemView.findViewById(R.id.imageView)
            titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)
            favBtn = itemView.findViewById(R.id.favBtn)
            likeCountTextView = itemView.findViewById<TextView>(R.id.likeCountTextView)

            //add to fav btn
            favBtn.setOnClickListener {
                val position: Int = getAdapterPosition()
                val coffeeItem: CoffeeItem = coffeeItems[position]
                likeClick(coffeeItem, favBtn, likeCountTextView)
            }
        }
    }

    private fun createTableOnFirstStart() {
        favDB!!.insertEmpty()
        val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putBoolean("firstStart", false)
        editor.apply()
    }

    private fun readCursorData(coffeeItem: CoffeeItem, viewHolder: ViewHolder) {
        val cursor = favDB!!.read_all_data(coffeeItem.getKey_id())
        val db: SQLiteDatabase = favDB!!.readableDatabase
        try {
            while (cursor.moveToNext()) {
                val item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS))
                coffeeItem.setFavStatus(item_fav_status)

                //check fav status
                if (item_fav_status != null && item_fav_status == "1") {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
                } else if (item_fav_status != null && item_fav_status == "0") {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed) cursor.close()
            db.close()
        }
    }

    // like click
    private fun likeClick(coffeeItem: CoffeeItem, favBtn: Button, textLike: TextView) {
        val refLike: DatabaseReference =
            FirebaseDatabase.getInstance().getReference().child("likes")
        val upvotesRefLike: DatabaseReference = refLike.child(coffeeItem.getKey_id())
        if (coffeeItem.getFavStatus().equals("0")) {
            coffeeItem.setFavStatus("1")
            favDB!!.insertIntoTheDatabase(
                coffeeItem.getTitle(), coffeeItem.getImageResourse(),
                coffeeItem.getKey_id(), coffeeItem.getFavStatus()
            )
            favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
            favBtn.isSelected = true
            upvotesRefLike.runTransaction(object : Handler() {
                fun doTransaction(mutableData: MutableData): SurfaceControl.Transaction.Result {
                    try {
                        val currentValue: Int = mutableData.getValue(Int::class.java)
                        if (currentValue == null) {
                            mutableData.setValue(1)
                        } else {
                            mutableData.setValue(currentValue + 1)
                            Handler(Looper.getMainLooper()).post(Runnable {
                                textLike.setText(
                                    mutableData.getValue().toString()
                                )
                            })
                        }
                    } catch (e: Exception) {
                        throw e
                    }
                    return SurfaceControl.Transaction.success(mutableData)
                }

                fun onComplete(
                    databaseError: DatabaseError?,
                    b: Boolean,
                    dataSnapshot: DataSnapshot?
                ) {
                    println("Transaction completed")
                }
            })
        } else if (coffeeItem.getFavStatus().equals("1")) {
            coffeeItem.setFavStatus("0")
            favDB!!.remove_fav(coffeeItem.getKey_id())
            favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
            favBtn.isSelected = false
            upvotesRefLike.runTransaction(object : Handler() {
                fun doTransaction(mutableData: MutableData): SurfaceControl.Transaction.Result {
                    try {
                        val currentValue: Int = mutableData.getValue(Int::class.java)
                        if (currentValue == null) {
                            mutableData.setValue(1)
                        } else {
                            mutableData.setValue(currentValue - 1)
                            Handler(Looper.getMainLooper()).post(Runnable {
                                textLike.setText(
                                    mutableData.getValue().toString()
                                )
                            })
                        }
                    } catch (e: Exception) {
                        throw e
                    }
                    return SurfaceControl.Transaction.success(mutableData)
                }

                fun onComplete(
                    databaseError: DatabaseError?,
                    b: Boolean,
                    dataSnapshot: DataSnapshot?
                ) {
                    println("Transaction completed")
                }
            })
        }
    }

    init {
        this.coffeeItems = coffeeItems
        this.context = context
    }
}