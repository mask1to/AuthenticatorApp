package com.example.authenticator.fragments

import android.app.ProgressDialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

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
        retrieveAndStoreToken() //generovanie tokenu
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

    fun retrieveAndStoreToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // ulozi token to firebaseDatabase, tam sa potom da token pozriet
            FirebaseDatabase.getInstance()
                .getReference("tokens")
                .setValue(token)

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(ContentValues.TAG, msg)

        })
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // TODO: Implement this method to send token to your app server.
        Log.d(ContentValues.TAG, "sendRegistrationTokenToServer($token)")
    }

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    fun onNewToken(token: String) {
        Log.d(ContentValues.TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)
    }


}