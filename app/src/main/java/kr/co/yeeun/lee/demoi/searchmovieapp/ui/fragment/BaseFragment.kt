package kr.co.yeeun.lee.demoi.searchmovieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kr.co.yeeun.lee.demoi.searchmovieapp.R

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getBinding(layoutInflater, container)
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun subscribeUi()
}