package com.yusuffdllh.smartfinance.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.ui.theme.Primary

@Composable
fun MainFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.size(60.dp),
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Tambah",
            modifier = Modifier.size(32.dp)
        )
    }
}
