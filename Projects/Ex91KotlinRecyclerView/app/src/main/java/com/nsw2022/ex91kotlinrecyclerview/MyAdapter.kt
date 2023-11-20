package com.nsw2022.ex91kotlinrecyclerview

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter constructor(val context: Context,var items: MutableList<Item>): RecyclerView.Adapter<MyAdapter.VH>(){/////////////////////


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val itemView:View = layoutInflater.inflate(R.layout.recycler_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:Item = items.get(position)

        holder.tvTitle.text = item.title
        //코틀린 언어는 setXXX()를 싫어함. 그냥 XXX를 변숴럼 - 대입연산자로 값 설정을 권장
        holder.tvMSG.text = item.msg
        Glide.with(context).load(item.img).into(holder.iv)

    }

    //함수의 return 코드 단순화 [ 할당연산자= ]
    override fun getItemCount(): Int= items.size


    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        // itemView 의 자식뷰들을 참조
        val tvTitle:TextView by lazy { itemView.findViewById(R.id.tv_title) }
        val tvMSG:TextView by lazy { itemView.findViewById(R.id.tv_msg) }
        val iv:ImageView by lazy { itemView.findViewById(R.id.iv) }
    }
}