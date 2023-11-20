package com.nsw2022.tpqucikplacebykakaosearchapi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nsw2022.tpqucikplacebykakaosearchapi.activities.MainActivity
import com.nsw2022.tpqucikplacebykakaosearchapi.adapters.PlaceListRecyclerAdapter
import com.nsw2022.tpqucikplacebykakaosearchapi.databinding.FragmentSearchListBinding

class SearchListFragment :Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return binding.root
    }

    val binding:FragmentSearchListBinding by lazy{ FragmentSearchListBinding.inflate(layoutInflater)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 메인액티비티 참조하기
        val ma=activity as MainActivity

        //아직 MainActivity 에서 파싱작업이 완료 되지 않았다면 데이터가 없음.
        if (ma.searchPlaceResponse==null) return

        binding.recyclerView.adapter = PlaceListRecyclerAdapter(requireContext(),ma.searchPlaceResponse!!.documents)




    }
}