package com.dicoding.storyapp

import com.dicoding.storyapp.api.response.ListStoryItem

object DataDummy {
    fun generateDummyStoriesResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                i.toString(),
                "author + $i",
                "quote $i",
                "description $i"
            )
            items.add(quote)
        }
        return items
    }
}