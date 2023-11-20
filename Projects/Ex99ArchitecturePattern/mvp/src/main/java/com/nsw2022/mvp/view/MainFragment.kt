package com.nsw2022.mvp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nsw2022.mvp.databinding.FragmentMainBinding
import com.nsw2022.mvp.model.Item
import com.nsw2022.mvp.presenter.MainContract
import com.nsw2022.mvp.presenter.MainPresenter

class MainFragment :Fragment(),MainContract.View{

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    lateinit var binding: FragmentMainBinding

    lateinit var presenter: MainPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter= MainPresenter()
        presenter.initial(this)
    }

    
    override fun showData(item: Item) {
        TODO("Not yet implemented")
    }

    override fun getContext(): Context {
        TODO("Not yet implemented")
    }
}