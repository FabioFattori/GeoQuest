package com.example.geoquest.ui.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.geoquest.R
import com.example.geoquest.ui.components.baseComponents.IconGradient
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.ui.theme.getSize

@Composable
fun MissionsTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(stringResource(R.string.mission), stringResource(R.string.completed))
    val icons = listOf(
        ImageVector.vectorResource(id = R.drawable.bookmark_star),
        ImageVector.vectorResource(id = R.drawable.bookmark_check)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, title ->
            Column(
                modifier = Modifier
                    .clickable { onTabSelected(index) }
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        title,
                        fontSize = getSize(TextType.Normal),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
                    )
                    IconGradient(
                        icon = icons[index],
                        contentDescription = "",
                        modifier = Modifier.padding(start = 4.dp).size(50.dp)
                    )
                }

                // Underline
                if (selectedTab == index) {
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .width(150.dp)
                            .background(getGradient())
                            .padding(top = 4.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}
