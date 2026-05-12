package me.dmadouros.pokerhand.domain.model

interface RuleSet {
    fun findAction(
        position: Position,
        previousAction: PreviousAction,
        hand: Hand,
    ): Action
}
