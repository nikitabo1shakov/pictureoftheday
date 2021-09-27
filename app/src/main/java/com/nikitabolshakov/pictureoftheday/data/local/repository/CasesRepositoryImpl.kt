package com.nikitabolshakov.pictureoftheday.data.local.repository

import com.nikitabolshakov.pictureoftheday.data.local.cases.ComplexCase
import com.nikitabolshakov.pictureoftheday.data.local.cases.ItemData
import com.nikitabolshakov.pictureoftheday.data.local.cases.SimpleCase

class CasesRepositoryImpl : CasesRepository {

    override fun getCases(): MutableList<ItemData> = casesList

    private val casesList = mutableListOf(
        SimpleCase("Купить молоко"),
        ComplexCase("Супер важное", "Погладить кота. Улететь на луну."),
        ComplexCase("АБВ", "фывапролджфяцйрыыыыыыы."),
        SimpleCase("Пробежать марфон 20 км"),
        SimpleCase("Сделать то, непонятно что"),
        SimpleCase("Очень важное сообщение"),
        ComplexCase("Как бы заголовок", "Как бы текст")
    )
}