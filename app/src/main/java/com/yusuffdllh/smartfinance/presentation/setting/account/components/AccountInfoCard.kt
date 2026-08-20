package com.yusuffdllh.smartfinance.presentation.setting.account.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yusuffdllh.smartfinance.data.model.User
import com.yusuffdllh.smartfinance.ui.theme.*

@Composable
fun AccountInfoCard(
    user: User,
    isEditMode: Boolean,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onBirthDateClick: () -> Unit,
    onGenderClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Informasi Akun",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Name
            EditField(
                label = "Nama Lengkap",
                value = user.name,
                onValueChange = onNameChange,
                enabled = isEditMode
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone
            EditField(
                label = "Nomor Telepon",
                value = user.phone,
                onValueChange = onPhoneChange,
                placeholder = "Contoh: 08123456789",
                enabled = isEditMode
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Birth Date
            SelectorField(
                label = "Tanggal Lahir",
                value = user.birthDate,
                placeholder = "Pilih tanggal lahir",
                onClick = if (isEditMode) onBirthDateClick else null,
                icon = Icons.Default.CalendarToday
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gender
            SelectorField(
                label = "Jenis Kelamin",
                value = user.gender,
                placeholder = "Pilih jenis kelamin",
                onClick = if (isEditMode) onGenderClick else null
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email (Non-editable)
            Column {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimary.copy(alpha = 0.6f),
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun EditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    placeholder: String = ""
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
        if (enabled) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder, color = TextHint) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Transparent,
                    unfocusedContainerColor = Transparent,
                    focusedIndicatorColor = Primary,
                    unfocusedIndicatorColor = Border,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                textStyle = MaterialTheme.typography.bodyLarge
            )
        } else {
            Text(
                text = value.ifEmpty { "-" },
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp)
            )
            HorizontalDivider(color = Border.copy(alpha = 0.5f))
        }
    }
}

@Composable
private fun SelectorField(
    label: String,
    value: String,
    placeholder: String,
    onClick: (() -> Unit)?,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Column(modifier = Modifier.then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value.ifEmpty { placeholder },
                style = MaterialTheme.typography.bodyLarge,
                color = if (value.isEmpty()) TextHint else TextPrimary
            )
            if (onClick != null) {
                icon?.let {
                    Icon(imageVector = it, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(20.dp))
                }
            }
        }
        HorizontalDivider(color = Border.copy(alpha = 0.5f))
    }
}
