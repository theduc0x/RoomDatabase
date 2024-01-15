package com.fftools.roomdatabase.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.fftools.roomdatabase.utils.Constants.LOG_D_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {
    protected lateinit var binding: VB

    abstract fun createBinding(): VB

    abstract fun initMain()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding()
        setContentView(binding.root)
        initMain()
    }

    protected fun showLog(@StringRes stringRes: Int?) {
        stringRes?.let {  Log.d(LOG_D_NAME, getString(it)) }

    }
    protected fun showLog(str: String?) {
        str?.let { Log.d(LOG_D_NAME, it) }
    }

    protected fun showToast(str: String?) = CoroutineScope(Dispatchers.Main).launch {
        str?.let { Toast.makeText(this@BaseActivity, str, Toast.LENGTH_SHORT).show() }
    }
}