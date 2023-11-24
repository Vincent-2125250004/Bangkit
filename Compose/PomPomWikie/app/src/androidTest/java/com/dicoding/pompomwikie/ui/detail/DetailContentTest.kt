package com.dicoding.pompomwikie.ui.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.dicoding.pompomwikie.model.Characters
import com.dicoding.pompomwikie.ui.theme.PomPomWikieTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeChara = Characters(
        "6",
        "Kafka",
        "⭐⭐⭐⭐",
        "The Perservation",
        "Fire",
        "https://static.wikia.nocookie.net/houkai-star-rail/images/a/a9/Character_Arlan_Icon.png/revision/latest?cb=20230723080444",
        "Characters Splash Art",
        "Kafka Kafka Kafka"
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            PomPomWikieTheme {
                DetailContent(
                    fullImgUrl = fakeChara.fullImgChara,
                    description = fakeChara.description,
                    id = fakeChara.id,
                    path = fakeChara.path,
                    name = fakeChara.name,
                    rarity = fakeChara.rarity,
                    element = fakeChara.element,
                    onBackClick = {},
                    isFavorite = fakeChara.isFavorited,
                    onFavoriteClick = { _, _ ->
                    }
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(
            fakeChara.name
        ).assertIsDisplayed()
    }

    @Test
    fun favoritedChara_clickable() {
        composeTestRule.onNodeWithTag("favorite").assertHasClickAction()
    }
}