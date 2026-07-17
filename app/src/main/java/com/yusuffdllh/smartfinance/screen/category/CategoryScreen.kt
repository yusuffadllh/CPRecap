package com.yusuffdllh.smartfinance.screen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yusuffdllh.smartfinance.screen.category.components.AddCategoryButton
import com.yusuffdllh.smartfinance.screen.category.components.CategoryHeader
import com.yusuffdllh.smartfinance.screen.category.components.CategoryList
import com.yusuffdllh.smartfinance.ui.theme.Background

@Composable
fun CategoryScreen(

    navController: NavController

) {

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(Background)

    ) {

        LazyColumn(

            modifier = Modifier.fillMaxSize(),

            contentPadding = PaddingValues(

                horizontal = 20.dp,

                vertical = 20.dp

            ),

            verticalArrangement = Arrangement.spacedBy(24.dp)

        ) {

            item {

                CategoryHeader(

                    onBackClick = {

                        navController.popBackStack()

                    }

                )

            }

            item {

                CategoryList(

                    onCategoryClick = { category ->

                        // nanti jika di Figma ada Edit Category
                        // tinggal diarahkan dari sini

                    }

                )

            }

            item {

                AddCategoryButton(

                    onClick = {

                        // nanti jika memang ada Add Category
                        // tinggal navigate

                    }

                )

            }

        }

    }

}