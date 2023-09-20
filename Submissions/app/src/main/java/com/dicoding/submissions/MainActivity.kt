package com.dicoding.submissions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var rvCharacters: RecyclerView
    private val list = ArrayList<Characters>()
    private lateinit var aboutButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        aboutButton = findViewById(R.id.about_page)
        rvCharacters = findViewById(R.id.rv_characters)
        rvCharacters.setHasFixedSize(true)

        list.addAll(getListCharacters())
        showRecylerList()

        aboutButton.setOnClickListener(this)

    }

    private fun getListCharacters(): ArrayList<Characters> {
        val name: Array<out String> = resources.getStringArray(R.array.character)
        val path: Array<out String> = resources.getStringArray(R.array.path)
        val rarity: Array<out String> = resources.getStringArray(R.array.rarity)
        val element: Array<out String> = resources.getStringArray(R.array.element)
        val icon: Array<out String> = resources.getStringArray(R.array.icon_chara)
        val splashArt: Array<out String> = resources.getStringArray(R.array.full_img_chara)
        val description: Array<out String> = resources.getStringArray(R.array.description)

        val listCharacters = ArrayList<Characters>()
        for (i in name.indices) {
            val chara = Characters(name[i], rarity[i], path[i], element[i], icon[i], splashArt[i], description [i])
            listCharacters.add(chara)
        }
        return listCharacters
    }

    private fun showRecylerList() {
        rvCharacters.layoutManager = LinearLayoutManager(this)
        val listCharaAdapter = CharactersAdapter(list)
        rvCharacters.adapter = listCharaAdapter
    }

    override fun onClick(v: View?) {
        startActivity(Intent(this,AboutPage::class.java))
    }
}