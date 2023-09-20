package com.dicoding.submissions


import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var chara: Characters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val nameDetail: TextView = findViewById(R.id.tv_detailNama)
        val rarityDetail: TextView = findViewById(R.id.tv_detailRarity)
        val pathDetail: TextView = findViewById(R.id.tv_detailPath)
        val elementDetail: TextView = findViewById(R.id.tv_detailElement)
        val splashArtDetail: ImageView = findViewById(R.id.iv_splashArt)
        val iconPath: ImageView = findViewById(R.id.iv_detailIconPath)
        val iconElement: ImageView = findViewById(R.id.iv_detailIconElement)
        val descDetail: TextView = findViewById(R.id.tv_detailDesc)
        val btnShare: ImageButton = findViewById(R.id.action_share)

        chara = intent.getParcelableExtra<Characters>("varChara")!!

        if (chara != null) {
            nameDetail.text = chara.name
            rarityDetail.text = chara.rarity
            pathDetail.text = chara.path
            elementDetail.text = chara.element
            descDetail.text = chara.description

            Glide.with(splashArtDetail.context)
                .load(chara.splashArt)
                .into(splashArtDetail)

        }

        when (pathDetail.text) {
            "The Perservation" -> {
                Glide.with(iconPath.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/5/52/Path_The_Preservation.png/revision/latest?cb=20230511094124"))
                    .into(iconPath)
            }

            "The Destruction" -> {
                Glide.with(iconPath.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/3/37/Path_The_Destruction.png/revision/latest?cb=20230508230545"))
                    .into(iconPath)
            }

            "The Hunt" -> {
                Glide.with(iconPath.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/1/1c/Path_The_Hunt.png/revision/latest?cb=20230511094214"))
                    .into(iconPath)
            }

            "The Abundance" -> {
                Glide.with(iconPath.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/3/36/Path_The_Abundance.png/revision/latest?cb=20230508230528"))
                    .into(iconPath)
            }

            "The Harmony" -> {
                Glide.with(iconPath.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/b/b9/Path_The_Harmony.png/revision/latest?cb=20230508230617"))
                    .into(iconPath)
            }

            "The Erudition" -> {
                Glide.with(iconPath.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/d/d2/Path_The_Erudition.png/revision/latest?cb=20230511094126"))
                    .into(iconPath)
            }

            "The Nihility" -> {
                Glide.with(iconPath.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/5/50/Path_The_Nihility.png/revision/latest?cb=20230508230649"))
                    .into(iconPath)
            }
        }

        when (elementDetail.text) {
            "Lightning" -> {
                Glide.with(iconElement.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/1/15/Type_Lightning.png/revision/latest?cb=20220610120726"))
                    .into(iconElement)
            }

            "Physical" -> {
                Glide.with(iconElement.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/6/69/Type_Physical.png/revision/latest?cb=20220610120901"))
                    .into(iconElement)
            }

            "Fire" -> {
                Glide.with(iconElement.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/f/f0/Type_Fire.png/revision/latest?cb=20220610120131"))
                    .into(iconElement)
            }

            "Ice" -> {
                Glide.with(iconElement.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/3/35/Type_Ice.png/revision/latest?cb=20220610120329"))
                    .into(iconElement)
            }

            "Wind" -> {
                Glide.with(iconElement.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/e/ec/Type_Wind.png/revision/latest?cb=20220610121015"))
                    .into(iconElement)
            }

            "Quantum" -> {
                Glide.with(iconElement.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/5/54/Type_Quantum.png/revision/latest?cb=20220610121124"))
                    .into(iconElement)
            }

            "Imaginary" -> {
                Glide.with(iconElement.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/2/2f/Type_Imaginary.png/revision/latest?cb=20220610120517"))
                    .into(iconElement)
            }
        }

        btnShare.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val messages = "Check this out, ${chara.name} from Honkai Star Rail"
        shareIntent.putExtra(Intent.EXTRA_TEXT, messages)
        startActivity(Intent.createChooser(shareIntent,"Share ${chara.name} ?"))
    }
}