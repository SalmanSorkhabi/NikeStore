package com.example.nikestore.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nikestore.R
import com.example.nikestore.common.NikeCompletableObserver
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    val viewModel: AuthViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signUpLinkBtn = view.findViewById<MaterialButton>(R.id.signUpLinkBtn)
        val loginBtn = view.findViewById<MaterialButton>(R.id.loginBtn)
        val emailEt = view.findViewById<TextInputEditText>(R.id.emailEt)
        val emailEtl = view.findViewById<TextInputLayout>(R.id.emailEtl)
        val passwordEt = view.findViewById<TextInputEditText>(R.id.passwordEt)
        val passwordEtl = view.findViewById<TextInputLayout>(R.id.passwordEtl)

        loginBtn.setOnClickListener {
                viewModel.login(emailEt.text.toString(), passwordEt.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                        override fun onComplete() {
                            requireActivity().finish()
                        }

                    })
        }

        signUpLinkBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer,SignUpFragment())
            }.commit()
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}

