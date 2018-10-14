package com.antoinecampbell.biometricdemo

import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.os.CancellationSignal
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CancellationSignal.OnCancelListener, DialogInterface.OnClickListener {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val callback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            Log.d(TAG, "onAuthenticationSucceeded:")
        }

        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {
            Log.d(TAG, "onAuthenticationHelp: code: $helpCode message: $helpString")
        }

        override fun onAuthenticationFailed() {
            Log.d(TAG, "onAuthenticationFailed:")
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            Log.d(TAG, "onAuthenticationError: code: $errorCode message: $errString")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_auth.setOnClickListener {
            displayPrompt(callback)
        }
    }

    override fun onCancel() {
        Log.d(TAG, "onCancel:")
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        Log.d(TAG, "onCancel: which: $which")
    }

    private fun displayPrompt(callback: BiometricPrompt.AuthenticationCallback) {
        BiometricPrompt.Builder(this)
            .setTitle("Auth title")
            .setSubtitle("Auth sub-title")
            .setDescription("Auth description here")
            .setNegativeButton("Cancel", mainExecutor, this)
            .build()
            .authenticate(
                CancellationSignal().apply { setOnCancelListener(this@MainActivity) },
                mainExecutor,
                callback
            )
    }
}
