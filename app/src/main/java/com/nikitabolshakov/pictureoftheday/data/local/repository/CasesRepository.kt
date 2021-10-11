package com.nikitabolshakov.pictureoftheday.data.local.repository

import com.nikitabolshakov.pictureoftheday.data.local.cases.ItemData

interface CasesRepository {
    fun getCases(): MutableList<ItemData>
}