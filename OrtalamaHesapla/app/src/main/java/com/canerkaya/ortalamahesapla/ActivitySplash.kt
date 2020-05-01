package com.canerkaya.ortalamahesapla

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class ActivitySplash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var gelenBalon=AnimationUtils.loadAnimation(this,R.anim.gelenbalon)
        var gelenbuton=AnimationUtils.loadAnimation(this,R.anim.gelenbuton)
        var gidenBalon=AnimationUtils.loadAnimation(this,R.anim.gidenbalon)
        var gidenButon=AnimationUtils.loadAnimation(this,R.anim.gidenbuton)

        button.animation=gelenbuton
        image.animation=gelenBalon

        button.setOnClickListener {
            image.startAnimation(gidenBalon)
            button.startAnimation(gidenButon)
            object :CountDownTimer(500,500){
                override fun onFinish() {
                    var intent=Intent(this@ActivitySplash,MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onTick(millisUntilFinished: Long) {

                }

            }.start()

        }
    }
}
