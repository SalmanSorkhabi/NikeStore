package com.example.nikestore.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nikestore.R
import com.example.nikestore.common.NikeCompletableObserver
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class SignUpFragment:Fragment() {
    val viewModel:AuthViewModel by inject()
    val compositeDisposable=CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signUpBtn = view.findViewById<MaterialButton>(R.id.signUpBtn)
        val loginLinkBtn = view.findViewById<MaterialButton>(R.id.loginLinkBtn)
        val emailEt = view.findViewById<TextInputEditText>(R.id.signUp_emailEt)
        val emailEtl = view.findViewById<TextInputLayout>(R.id.signUp_emailEtl)
        val passwordEt = view.findViewById<TextInputEditText>(R.id.signUp_passwordEt)
        val passwordEtl = view.findViewById<TextInputLayout>(R.id.signUp_passwordEtl)
        signUpBtn.setOnClickListener {
            viewModel.signUp(emailEt.text.toString(),passwordEt.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :NikeCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        requireActivity().finish()
                    }
                })
        }

        loginLinkBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer,LoginFragment())
            }.commit()
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}