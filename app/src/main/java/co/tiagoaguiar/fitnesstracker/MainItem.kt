package co.tiagoaguiar.fitnesstracker

import androidx.annotation.DrawableRes

// this int is from R.
import androidx.annotation.StringRes

data class MainItem(
    var id: Int,
    @DrawableRes val drawableId: Int,
    @StringRes val textStringId: Int,
    val color: Int,

)