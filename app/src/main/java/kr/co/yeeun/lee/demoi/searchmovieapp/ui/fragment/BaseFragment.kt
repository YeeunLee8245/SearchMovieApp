package kr.co.yeeun.lee.demoi.searchmovieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kr.co.yeeun.lee.demoi.searchmovieapp.R

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    protected var binding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getBinding(layoutInflater, container)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun subscribeUi()
}