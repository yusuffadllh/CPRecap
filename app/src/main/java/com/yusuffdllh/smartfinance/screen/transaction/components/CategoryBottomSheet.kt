package com.yusuffdllh.smartfinance.screen.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.*

data class CategoryModel(

    val title: String,

    val icon: ImageVector,

    val color: Color

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(

    selectedCategory: String,

    onDismiss: () -> Unit,

    onCategorySelected: (String) -> Unit

) {

    val sheetState = rememberModalBottomSheetState()

    val categories = listOf(

        CategoryModel(
            "Makanan",
            Icons.Default.Fastfood,
            Primary
        ),

        CategoryModel(
            "Transportasi",
            Icons.Default.DirectionsBus,
            ChartBlue
        ),

        CategoryModel(
            "Belanja",
            Icons.Default.ShoppingBag,
            ChartPurple
        ),

        CategoryModel(
            "Tagihan",
            Icons.Default.ReceiptLong,
            ChartOrange
        ),

        CategoryModel(
            "Hiburan",
            Icons.Default.Movie,
            ChartRed
        ),

        CategoryModel(
            "Kesehatan",
            Icons.Default.LocalHospital,
            Danger
        ),

        CategoryModel(
            "Pendidikan",
            Icons.Default.School,
            Secondary
        )

    )

    ModalBottomSheet(

        onDismissRequest = onDismiss,

        sheetState = sheetState,

        containerColor = Background

    ) {

        Text(

            text = "Pilih Kategori",

            modifier = Modifier.padding(horizontal = 24.dp),

            color = TextPrimary,

            style = MaterialTheme.typography.titleLarge,

            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(

            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            items(categories) { category ->

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            onCategorySelected(category.title)

                        },

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(

                        containerColor = Surface

                    )

                ) {

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Box(

                            modifier = Modifier
                                .size(46.dp)
                                .background(
                                    category.color.copy(alpha = .15f),
                                    CircleShape
                                ),

                            contentAlignment = Alignment.Center

                        ) {

                            Icon(

                                imageVector = category.icon,

                                contentDescription = null,

                                tint = category.color

                            )

                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(

                            text = category.title,

                            modifier = Modifier.weight(1f),

                            color = TextPrimary,

                            style = MaterialTheme.typography.bodyLarge

                        )

                        if (selectedCategory == category.title) {

                            Icon(

                                imageVector = Icons.Default.Check,

                                contentDescription = null,

                                tint = Primary

                            )

                        }

                    }

                }

            }

        }

        Spacer(modifier = Modifier.height(24.dp))

    }

}