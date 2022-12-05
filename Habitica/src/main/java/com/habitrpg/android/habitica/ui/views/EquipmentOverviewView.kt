package com.habitrpg.android.habitica.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habitrpg.android.habitica.R
import com.habitrpg.android.habitica.models.user.Outfit
import com.habitrpg.android.habitica.models.user.Preferences
import com.habitrpg.android.habitica.ui.theme.HabiticaTheme
import com.habitrpg.android.habitica.ui.theme.caption2

@Composable
fun OverviewItem(
    text: String,
    iconName: String?,
    modifier: Modifier = Modifier,
    isTwoHanded: Boolean = false
) {
    val hasIcon = iconName?.isNotBlank() == true
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
            .width(70.dp)
    ) {
        Box(
            Modifier
                .size(70.dp)
                .clip(MaterialTheme.shapes.small)
                .background(colorResource(if (hasIcon) R.color.gray_700 else R.color.gray_10)),
            contentAlignment = Alignment.Center
        ) {
            if (isTwoHanded) {
                Image(painterResource(R.drawable.equipment_two_handed), null)
            } else if (hasIcon) {
                PixelArtView(
                    imageName = iconName, Modifier
                        .size(70.dp)
                )
            } else {
                Image(painterResource(R.drawable.equipment_nothing_equipped), null)
            }
        }
        Text(
            text,
            style = HabiticaTheme.typography.caption2,
            color = colorResource(R.color.gray_400),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun EquipmentOverviewView(
    outfit: Outfit?,
    onEquipmentTap: (String, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(colorResource(R.color.gray_50))
            .padding(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            OverviewItem(stringResource(R.string.outfit_weapon), outfit?.weapon.let { "shop_$it" }, Modifier.clickable {
                onEquipmentTap("weapon", null)
            })
            OverviewItem(stringResource(R.string.outfit_shield), outfit?.shield.let { "shop_$it" }, Modifier.clickable {
                onEquipmentTap("shield", null)
            })
            OverviewItem(stringResource(R.string.outfit_head), outfit?.head.let { "shop_$it" }, Modifier.clickable {
                onEquipmentTap("head", null)
            })
            OverviewItem(stringResource(R.string.outfit_armor), outfit?.armor.let { "shop_$it" }, Modifier.clickable {
                onEquipmentTap("armor", null)
            })
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            OverviewItem(
                stringResource(R.string.outfit_headAccessory),
                outfit?.headAccessory.let { "shop_$it" }, Modifier.clickable {
                    onEquipmentTap("headAccessory", null)
                })
            OverviewItem(stringResource(R.string.outfit_body), outfit?.body.let { "shop_$it" }, Modifier.clickable {
                onEquipmentTap("body", null)
            })
            OverviewItem(stringResource(R.string.outfit_back), outfit?.back.let { "shop_$it" }, Modifier.clickable {
                onEquipmentTap("back", null)
            })
            OverviewItem(
                stringResource(R.string.outfit_eyewear),
                outfit?.eyeWear.let { "shop_$it" }, Modifier.clickable {
                    onEquipmentTap("eyewear", null)
                })
        }
    }
}

@Composable
fun AvatarCustomizationOverviewView(
    preferences: Preferences?,
    onCustomizationTap: (String, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(colorResource(R.color.gray_50))
            .padding(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            OverviewItem(
                stringResource(R.string.avatar_shirt),
                preferences?.shirt.let { "${preferences?.size}_shirt$it" }, Modifier.clickable {
                    onCustomizationTap("shirt", null)
                })
            OverviewItem(
                stringResource(R.string.avatar_skin),
                preferences?.skin.let { "skin_$it" },
                Modifier.clickable {
                    onCustomizationTap("skin", null)
                })
            OverviewItem(
                stringResource(R.string.avatar_hair_color),
                if (preferences?.hair?.color != null && preferences.hair?.color != "") "hair_bangs_1_" + preferences.hair?.color else "",
                Modifier.clickable {
                    onCustomizationTap("hair", "color")
                }
            )
            OverviewItem(
                stringResource(R.string.avatar_hair_bangs),
                if (preferences?.hair?.bangs != null && preferences.hair?.bangs != 0) "hair_bangs_" + preferences.hair?.bangs + "_" + preferences.hair?.color else "",
                Modifier.clickable {
                    onCustomizationTap("hair", "bangs")
                }
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            OverviewItem(
                stringResource(R.string.avatar_style),
                if (preferences?.hair?.base != null && preferences.hair?.base != 0) "hair_base_" + preferences.hair?.base + "_" + preferences.hair?.color else "",
                Modifier.clickable {
                    onCustomizationTap("hair", "base")
                }
            )
            OverviewItem(
                stringResource(R.string.avatar_mustache),
                if (preferences?.hair?.mustache != null && preferences.hair?.mustache != 0) "hair_mustache_" + preferences.hair?.mustache + "_" + preferences.hair?.color else "",
                Modifier.clickable {
                    onCustomizationTap("hair", "mustache")
                }
            )
            OverviewItem(
                stringResource(R.string.avatar_beard),
                if (preferences?.hair?.beard != null && preferences.hair?.beard != 0) "hair_beard_" + preferences.hair?.beard + "_" + preferences.hair?.color else "",
                Modifier.clickable {
                    onCustomizationTap("hair", "beard")
                }
            )
            OverviewItem(
                stringResource(R.string.avatar_flower),
                if (preferences?.hair?.flower != null && preferences.hair?.flower != 0) "hair_flower_" + preferences.hair?.flower else "",
                Modifier.clickable {
                    onCustomizationTap("hair", "flower")
                }
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            OverviewItem(
                stringResource(R.string.avatar_wheelchair),
                preferences?.chair?.let { if (it.startsWith("handleless")) "chair_$it" else it })
            OverviewItem(
                stringResource(R.string.avatar_background),
                preferences?.background.let { "background_$it" })
            Box(Modifier.size(70.dp))
            Box(Modifier.size(70.dp))
        }
    }
}

@Preview
@Composable
fun EquipmentOverviewItemPreview() {
    Column(Modifier.width(320.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OverviewItem("Main-Hand", "shop_weapon_warrior_1")
            OverviewItem("Off-Hand", null, isTwoHanded = true)
            OverviewItem("Armor", null)
        }
        EquipmentOverviewView(null, { _, _ -> })
        AvatarCustomizationOverviewView(null, { _, _ -> })
    }
}