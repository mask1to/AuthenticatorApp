package com.example.authenticator.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.example.authenticator.R

class SettingUpFragment : Fragment()
{

    private lateinit var continueBtn : Button
    private lateinit var secondAnim : LottieAnimationView
    private lateinit var txtView : TextView
    private lateinit var userEmail : EditText


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        parentFragment?.activity?.actionBar?.hide()
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            //do nothing - just disabled back press
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        parentFragment?.activity?.actionBar?.hide()
        return inflater.inflate(R.layout.settingup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        continueBtn = view.findViewById(R.id.firstButton)
        secondAnim = view.findViewById(R.id.secondAnimation)
        txtView = view.findViewById(R.id.textView)
        userEmail = view.findViewById(R.id.emailField)

        continueBtn.setOnClickListener{

            if(userEmail.text.isEmpty())
            {
                showToast("Enter your e-mail please")
            }
            else if(userEmail.text.contains("masko552@gmail.com"))
            {
                showToast("Hurray")
            }
            else
            {
                showToast("another email")
            }
        }

        secondAnim.repeatCount = Animation.INFINITE

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun showToast(message : String) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
}