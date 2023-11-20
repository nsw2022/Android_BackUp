package com.example.musicplayer

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.MainItemViewBinding
import com.nsw2022.ko_sql.MainActivity
import com.nsw2022.ko_sql.R

class MainListAdapter(val context: Context, val musicList: MutableList<Music>):
    RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    val mainActivity: MainActivity = (context as MainActivity)

    val dbHelper: DBHelper by lazy {
        DBHelper(context)
    }

    val ALBUM_IMAGE_SIZE = 150

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListAdapter.ViewHolder {
        val binding = MainItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainListAdapter.ViewHolder, position: Int) {
        val binding = holder.binding

        binding.tvRVArtist.text = musicList[holder.adapterPosition].artist
        binding.tvRVTitle.text = musicList[holder.adapterPosition].title

        //즐겨찾기 버튼을 누르면 아이콘 변화
        when(musicList[holder.adapterPosition].favorite){
            0 -> {
                holder.binding.ivRVFavorite.setImageResource(R.drawable.ic_baseline_star_border_24)
            }

            1 -> {
                holder.binding.ivRVFavorite.setImageResource(R.drawable.ic_baseline_star_24)
            }
        }

        //통상 앨범 이미지
        val bitmap: Bitmap? = musicList[holder.adapterPosition].getAlbumImage(context, ALBUM_IMAGE_SIZE)
        if (bitmap != null){
            holder.binding.ivRVAlbumImage.setImageBitmap(bitmap)
        } else {
            holder.binding.ivRVAlbumImage.setImageResource(R.drawable.itunes)
        }

        //즐겨찾기 아이콘을 클릭했을 때
        binding.ivRVFavorite.setOnClickListener {
            when(musicList[position].favorite){

                //즐겨찾기에 추가돼있지 않던 곡이면 추가한다
                0 -> {
                    musicList[position].favorite = 1

                    if (dbHelper.updateFavorite(musicList[position])){
                        Toast.makeText(context, "즐겨찾기에 추가했습니다.", Toast.LENGTH_SHORT).show()
                    }

                    notifyItemChanged(position)
                }

                //즐겨찾기에 추가돼있던 곡이라면 삭제한다.
                1 -> {
                    musicList[position].favorite = 0

                    if (dbHelper.updateFavorite(musicList[position])){
                        Toast.makeText(context, "즐겨찾기에서 제거하였습니다.", Toast.LENGTH_SHORT).show()

                        //즐겨찾기 창에서 즐겨찾기 해제를 눌렀다면 창에서 없어지도록 한다.
                        if (mainActivity.typeOfList == Type.FAVORITE){
                            Log.d("Log_debug", "list type = ${mainActivity.typeOfList}")
                            musicList.remove(musicList[position])
                        }
                    }
                    notifyDataSetChanged()
                }
            }
        }

        binding.root.setOnClickListener {
            mainActivity.nowPlaying(musicList[position])
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    inner class ViewHolder(val binding: MainItemViewBinding): RecyclerView.ViewHolder(binding.root)

}