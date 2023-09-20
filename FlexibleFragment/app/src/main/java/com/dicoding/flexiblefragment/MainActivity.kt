package com.dicoding.flexiblefragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val fragment = fragmentManager.findFragmentById(R.id.frame_container)

        if (fragment !is HomeFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + HomeFragment::class.java.simpleName)

            //Menggunakan KTX
            fragmentManager.commit  {
                add(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
            }

//            fragmentManager.beginTransaction()
//                .add(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
//                .commit()

        }
    }
}