package com.sankets.expensetracker.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.sankets.expensetracker.R
import com.sankets.expensetracker.domain.util.Constants
import com.sankets.expensetracker.presentation.ui.theme.Background
import com.sankets.expensetracker.presentation.ui.theme.DotsSelectedColor
import com.sankets.expensetracker.presentation.ui.theme.DotsUnSelectedColor
import com.sankets.expensetracker.presentation.ui.theme.PrimaryText
import com.sankets.expensetracker.presentation.ui.theme.SecondaryText
import com.sankets.expensetracker.presentation.ui.theme.fonts
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@ExperimentalAnimationApi
@Composable
fun OnboardingScreen(
    viewModel: TransactionViewModel,
    navController: NavController
) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )
    val pagerState = rememberPagerState(
        pageCount = pages.size
    )

    Scaffold(backgroundColor = Background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                modifier = Modifier.wrapContentSize(),
                state = pagerState,
                verticalAlignment = Alignment.Top
            ) { position ->
                PagerScreen(onBoardingPage = pages[position])
            }

            PageIndicator(
                pageCount = pagerState.pageCount,
                currentPage = pagerState.currentPage,
                modifier = Modifier.padding(40.dp)
            )

            ButtonsSection(
                pagerState = pagerState,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        Modifier
            .wrapContentSize()
            .padding(26.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .size(360.dp)
                .padding(top = 24.dp),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager Image",
        )
        Text(
            text = onBoardingPage.title,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryText,
            fontFamily = fonts
        )
        Text(
            text = onBoardingPage.description,
            Modifier.padding(top = 45.dp, start = 8.dp, end = 8.dp),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Thin,
            fontFamily = fonts,
            color = SecondaryText

        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ButtonsSection(
    pagerState: PagerState,
    navController: NavController,
    viewModel: TransactionViewModel
) {

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        if (pagerState.currentPage != 2) {
            Image(
                modifier = Modifier
                    .size(104.dp)
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .clickable {

                        scope.launch {
                            val nextPage = pagerState.currentPage + 1
                            pagerState.scrollToPage(nextPage)
                        }
                    },
                painter = painterResource(id = R.drawable.round_navigate_next_24),
                contentDescription = "Next",
            )
            if (pagerState.currentPage != 0) {
                Image(
                    modifier = Modifier
                        .size(104.dp)
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .clickable {

                            scope.launch {
                                val prevPage = pagerState.currentPage - 1
                                if (prevPage >= 0) {
                                    pagerState.scrollToPage(prevPage)
                                }
                            }
                        },
                    painter = painterResource(id = R.drawable.round_navigate_before_24),
                    contentDescription = "Previous",
                )
            }
        } else {
            OutlinedButton(
                onClick = {
                    viewModel.sharedPrefs.setBoolean(Constants.IS_FIRST_LAUNCH, false)
                    navController.popBackStack()
                    navController.navigate(Screen.ChooseBanksScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DotsUnSelectedColor
                )
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Background,
                    fontFamily = fonts
                )
            }
        }
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorSingleDot(isSelected = it == currentPage)
        }


    }
}

@Composable
fun IndicatorSingleDot(isSelected: Boolean) {

    val width = animateDpAsState(targetValue = if (isSelected) 35.dp else 15.dp)
    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(12.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(if (isSelected) DotsSelectedColor else DotsUnSelectedColor)
    )
}

@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(backgroundColor = Background) {
            PagerScreen(onBoardingPage = OnBoardingPage.First)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(backgroundColor = Background) {
            PagerScreen(onBoardingPage = OnBoardingPage.Second)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(backgroundColor = Background) {
            PagerScreen(onBoardingPage = OnBoardingPage.Third)
        }
    }

}