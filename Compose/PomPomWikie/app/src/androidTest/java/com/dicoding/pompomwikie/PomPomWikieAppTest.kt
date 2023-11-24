package com.dicoding.pompomwikie

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.dicoding.pompomwikie.model.CharactersData
import com.dicoding.pompomwikie.navigation.Screen
import com.dicoding.pompomwikie.ui.theme.PomPomWikieTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PomPomWikieAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            PomPomWikieTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                PompomWikieApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyCorrectStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("CharaList").performScrollToIndex(8)
        composeTestRule.onNodeWithText(CharactersData.characters[8].name).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithText(CharactersData.characters[8].name).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_fav).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("CharaList").performScrollToIndex(8)
        composeTestRule.onNodeWithText(CharactersData.characters[8].name).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.moveBack))
            .performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun searchIncorrectChara() {
        val incorrectChara = "Kafka"
        composeTestRule.onNodeWithStringId(R.string.placeholder_search).performTextInput(incorrectChara)
    }

    @Test
    fun searchCorrectChara() {
        val correctChara = "Arlan"
        composeTestRule.onNodeWithStringId(R.string.placeholder_search).performTextInput(correctChara)
        composeTestRule.onNodeWithTag("CharaList").assertIsDisplayed()
    }
}