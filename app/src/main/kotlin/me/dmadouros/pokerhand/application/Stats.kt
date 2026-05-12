package me.dmadouros.pokerhand.application

import me.dmadouros.pokerhand.domain.model.Action
import me.dmadouros.pokerhand.domain.model.Position
import me.dmadouros.pokerhand.domain.model.PreviousAction

data class Stats(
    private val previousActions: MutableMap<PreviousAction, Int> = mutableMapOf(),
    private val positions: MutableMap<Position, Int> = mutableMapOf(),
    private val actions: MutableMap<Action, Int> = mutableMapOf(),
) {
    fun onPreviousAction(previousAction: PreviousAction) {
        increment(previousActions, previousAction)
    }

    fun onPosition(position: Position) {
        increment(positions, position)
    }

    fun onAction(action: Action) {
        increment(actions, action)
    }

    private fun <T> increment(
        map: MutableMap<T, Int>,
        item: T,
    ) {
        if (!map.containsKey(item)) {
            map[item] = 0
        }
        map[item] = map[item]!! + 1
    }
}
