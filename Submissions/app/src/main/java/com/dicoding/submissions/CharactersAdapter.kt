package com.dicoding.submissions

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CharactersAdapter(private val listCharacter: ArrayList<Characters>) :
    RecyclerView.Adapter<CharactersAdapter.ListViewHolder>(), View.OnClickListener {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_characters)
        val tvName: TextView = itemView.findViewById(R.id.tv_characters)
        val tvPath: TextView = itemView.findViewById(R.id.tv_path)
        val ivPath: ImageView = itemView.findViewById(R.id.iv_path)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersAdapter.ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_star_rail, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersAdapter.ListViewHolder, position: Int) {
        val (name, rarity, path, element, icon, splashArt, description) = listCharacter[position]

        holder.tvName.text = name
        holder.tvPath.text = path
        Glide.with(holder.ivIcon.context).load(icon).into(holder.ivIcon)

        when (holder.tvPath.text) {
            "The Perservation" -> {
                Glide.with(holder.ivIcon.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/5/52/Path_The_Preservation.png/revision/latest?cb=20230511094124"))
                    .into(holder.ivPath)
            }
            "The Destruction" -> {
                Glide.with(holder.ivIcon.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/3/37/Path_The_Destruction.png/revision/latest?cb=20230508230545"))
                    .into(holder.ivPath)
            }
            "The Hunt" -> {
                Glide.with(holder.ivIcon.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/1/1c/Path_The_Hunt.png/revision/latest?cb=20230511094214"))
                    .into(holder.ivPath)
            }
            "The Abundance" -> {
                Glide.with(holder.ivIcon.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/3/36/Path_The_Abundance.png/revision/latest?cb=20230508230528"))
                    .into(holder.ivPath)
            }
            "The Harmony" -> {
                Glide.with(holder.ivIcon.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/b/b9/Path_The_Harmony.png/revision/latest?cb=20230508230617"))
                    .into(holder.ivPath)
            }
            "The Erudition" -> {
                Glide.with(holder.ivIcon.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/d/d2/Path_The_Erudition.png/revision/latest?cb=20230511094126"))
                    .into(holder.ivPath)
            }
            "The Nihility" -> {
                Glide.with(holder.ivIcon.context)
                    .load(Uri.parse("https://static.wikia.nocookie.net/houkai-star-rail/images/5/50/Path_The_Nihility.png/revision/latest?cb=20230508230649"))
                    .into(holder.ivPath)
            }
        }

        holder.itemView.setOnClickListener(this)
        holder.itemView.tag = position


    }

    override fun getItemCount(): Int {
        return listCharacter.size
    }

    override fun onClick(v: View?) {
        val position = v?.tag as Int
        val character = listCharacter[position]
        val IntentDetail = Intent(v.context, DetailActivity::class.java)
        IntentDetail.putExtra("varChara", character)
        v.context.startActivity(IntentDetail)
    }
}


