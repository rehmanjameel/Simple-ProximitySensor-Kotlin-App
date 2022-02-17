package com.example.proximitysensor

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)

class MainActivity : AppCompatActivity() {
    private lateinit var powerManager: PowerManager
    private lateinit var lock: PowerManager.WakeLock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        lock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "simplewakelock:wakelocktag")

        button_activate.setOnClickListener {
            enableBehavior()
            textview.text = "Behaviour Enabled"
        }

        button_disable.setOnClickListener {
            disableBehavior()
            textview.text = "Behavior Disabled"
        }
    }

    override fun onPause() {
        super.onPause()
        disableBehavior()
    }

    @SuppressLint("WakelockTimeout")
    private fun enableBehavior() {
        if (!lock.isHeld) {
            lock.acquire()
        }
    }

    private fun disableBehavior() {
        if (lock.isHeld) {
            lock.release()
        }
    }
}