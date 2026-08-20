package com.yusuffdllh.smartfinance.data.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.yusuffdllh.smartfinance.data.local.entity.CategoryEntity
import com.yusuffdllh.smartfinance.data.model.Category
import com.yusuffdllh.smartfinance.ui.theme.ChartBlue
import com.yusuffdllh.smartfinance.ui.theme.ChartPurple
import com.yusuffdllh.smartfinance.ui.theme.Danger
import com.yusuffdllh.smartfinance.ui.theme.Primary
import com.yusuffdllh.smartfinance.ui.theme.Secondary
import com.yusuffdllh.smartfinance.ui.theme.TextSecondary
import com.yusuffdllh.smartfinance.ui.theme.Warning

/**
 * Maps between the persisted [CategoryEntity] (Room) and the UI-facing [Category] domain model.
 *
 * Icons and colors are stored as stable string keys so they survive persistence while keeping
 * the on-screen appearance identical to the original preset list. Unknown keys fall back to
 * safe defaults instead of crashing.
 */
object CategoryMapper {

    private val defaultIcon = Icons.Default.MoreHoriz
    private val defaultColor = TextSecondary

    private val iconByKey: Map<String, ImageVector> = mapOf(
        "fastfood" to Icons.Default.Fastfood,
        "bus" to Icons.Default.DirectionsBus,
        "shopping_bag" to Icons.Default.ShoppingBag,
        "receipt" to Icons.AutoMirrored.Filled.ReceiptLong,
        "movie" to Icons.Default.Movie,
        "hospital" to Icons.Default.LocalHospital,
        "school" to Icons.Default.School,
        "send" to Icons.AutoMirrored.Filled.Send,
        "money" to Icons.Default.AttachMoney,
        "wallet" to Icons.Default.AccountBalanceWallet,
        "gift" to Icons.Default.CardGiftcard,
        "trending_up" to Icons.AutoMirrored.Filled.TrendingUp,
        "call_received" to Icons.AutoMirrored.Filled.CallReceived,
        "more" to Icons.Default.MoreHoriz
    )

    private val colorByKey: Map<String, Color> = mapOf(
        "primary" to Primary,
        "secondary" to Secondary,
        "chart_blue" to ChartBlue,
        "chart_purple" to ChartPurple,
        "warning" to Warning,
        "danger" to Danger,
        "text_secondary" to TextSecondary
    )

    fun toDomain(entity: CategoryEntity, id: Int = 0): Category = Category(
        id = id,
        name = entity.name,
        icon = iconByKey[entity.icon] ?: defaultIcon,
        color = colorByKey[entity.color] ?: defaultColor
    )

    fun toDomainList(entities: List<CategoryEntity>): List<Category> =
        entities.mapIndexed { index, entity -> toDomain(entity, id = index + 1) }
}
