package com.habitrpg.wearos.habitica.ui.activities

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import com.habitrpg.android.habitica.R
import com.habitrpg.android.habitica.databinding.ActivityStatsBinding
import com.habitrpg.android.habitica.extensions.observeOnce
import com.habitrpg.common.habitica.views.HabiticaIconsHelper
import com.habitrpg.wearos.habitica.extensions.waitForLayout
import com.habitrpg.wearos.habitica.models.Stats
import com.habitrpg.wearos.habitica.models.User
import com.habitrpg.wearos.habitica.ui.viewmodels.StatsViewModel
import com.habitrpg.wearos.habitica.ui.views.StatValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsActivity : BaseActivity<ActivityStatsBinding, StatsViewModel>() {
    override val viewModel: StatsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStatsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setStatViews()
        viewModel.user.observe(this) {
            updateStats(it)
        }
    }

    private fun setStatViews() {
        binding.hpStatValue.setStatValueResources(HabiticaIconsHelper.imageOfHeartLightBg(), R.color.hp_bar_color)
        binding.expStatValue.setStatValueResources(HabiticaIconsHelper.imageOfExperience(), R.color.exp_bar_color)
        binding.mpStatValue.setStatValueResources(HabiticaIconsHelper.imageOfMagic(), R.color.mpColor)
    }

    private fun updateStats(user: User) {
        val stats = user.stats
        stats?.let { updateBarViews(it) }
        stats?.let { updateStatViews(it) }
    }

    private fun updateBarViews(stats: Stats) {
        binding.hpBar.setPercentageValues(stats.hp?.toInt() ?: 0, stats.maxHealth ?: 0)
        binding.hpBar.animateProgress()

        binding.expBar.setPercentageValues(stats.exp?.toInt() ?: 0, stats.toNextLevel ?: 0)
        binding.expBar.animateProgress()

        if (stats.lvl ?: 0 < 10) {
            binding.mpBar.visibility = View.GONE
        } else {
            binding.mpBar.setPercentageValues(stats.mp?.toInt() ?: 0, stats.maxMP ?: 0)
            binding.mpBar.animateProgress()
        }
    }

    private fun updateStatViews(stats: Stats) {
        binding.hpStatValue.setStatValue(stats.maxHealth ?: 0, stats.hp?.toInt() ?: 0)
        binding.expStatValue.setStatValue(stats.toNextLevel ?: 0, stats.exp?.toInt() ?: 0)
        if (stats.lvl ?: 0 < 10) {
            binding.mpStatValue.visibility = View.GONE
        } else {
            binding.mpStatValue.setStatValue(stats.maxMP ?: 0, stats.mp?.toInt() ?: 0)
        }
    }
}

