package com.yusuffdllh.smartfinance.screen.category.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.data.model.Category
import com.yusuffdllh.smartfinance.data.repository.DummyCategoryRepository

@Composable
fun CategoryList(

    onCategoryClick: (Category) -> Unit

) {

    val categories = DummyCategoryRepository.categories

    Column(

        modifier = Modifier.fillMaxWidth(),

        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {

        categories.forEach { category ->

            CategoryItem(

                category = category,

                onClick = {

                    onCategoryClick(category)

                }

            )

        }

    }

}