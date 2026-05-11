package me.dmadouros.pokerhand.domain.model

import kotlin.random.Random

enum class PreviousAction(
    val display: String,
    val weight: Int,
) {
    UNRAISED("No one before you has raised", 66),
    RAISED("Someone before you has raised", 22),
    RAISED_AND_RERAISED("Someone before you raised and someone else reraised", 12);

    companion object {
        fun weightedRandom(): PreviousAction {
            val totalWeight = PreviousAction.entries.sumOf { it.weight }
            val r = Random.nextInt(totalWeight)

            var cumulative = 0
            for (entry in PreviousAction.entries) {
                cumulative += entry.weight
                if (r < cumulative) {
                    return entry
                }
            }

            error("Should never happen")
        }
    }
}