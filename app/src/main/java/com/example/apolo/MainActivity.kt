package com.example.apolo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.example.apolo.views.MapsFragment
import com.example.apolo.views.ListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    //TODO usar injeção de dependencias
    private val maps = MapsFragment()
    private val teste = ListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(maps)

        val nbv: BottomNavigationView = findViewById(R.id.bnv_navigation_view)

        //TODO criar ViewModel para controlar as transições
        nbv.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mi_lista -> replaceFragment(teste)
                R.id.mi_mapa -> replaceFragment(maps)
            }
            true
        }
    }

    //TODO criar ViewModel para controlar as transições
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_container, fragment)
            transaction.commit()
        }
    }
}