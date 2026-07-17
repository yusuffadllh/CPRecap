package com.yusuffdllh.smartfinance.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.runtime.mutableStateListOf
import com.yusuffdllh.smartfinance.data.model.Category
import com.yusuffdllh.smartfinance.ui.theme.ChartBlue
import com.yusuffdllh.smartfinance.ui.theme.ChartOrange
import com.yusuffdllh.smartfinance.ui.theme.ChartPurple
import com.yusuffdllh.smartfinance.ui.theme.ChartRed
import com.yusuffdllh.smartfinance.ui.theme.Danger
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.Secondary

object DummyCategoryRepository {

    val categories = mutableStateListOf(

        Category(

            id = 1,

            name = "Makanan",

            icon = Icons.Default.Fastfood,

            color = Primary

        ),

        Category(

            id = 2,

            name = "Transportasi",

            icon = Icons.Default.DirectionsBus,

            color = ChartBlue

        ),

        Category(

            id = 3,

            name = "Belanja",

            icon = Icons.Default.ShoppingBag,

            color = ChartPurple

        ),

        Category(

            id = 4,

            name = "Tagihan",

            icon = Icons.AutoMirrored.Filled.ReceiptLong,

            color = ChartOrange

        ),

        Category(

            id = 5,

            name = "Hiburan",

            icon = Icons.Default.Movie,

            color = ChartRed

        ),

        Category(

            id = 6,

            name = "Kesehatan",

            icon = Icons.Default.LocalHospital,

            color = Danger

        ),

        Category(

            id = 7,

            name = "Pendidikan",

            icon = Icons.Default.School,

            color = Secondary

        )

    )

    fun addCategory(

        name: String,

        icon: androidx.compose.ui.graphics.vector.ImageVector,

        color: androidx.compose.ui.graphics.Color

    ) {

        categories.add(

            Category(

                id = categories.size + 1,

                name = name,

                icon = icon,

                color = color

            )

        )

    }

    fun getCategoryByName(

        name: String

    ): Category? {

        return categories.find {

            it.name == name

        }

    }

}