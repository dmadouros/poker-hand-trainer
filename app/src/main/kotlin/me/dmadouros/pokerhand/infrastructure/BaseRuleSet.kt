package me.dmadouros.pokerhand.infrastructure

import me.dmadouros.pokerhand.domain.model.Action
import me.dmadouros.pokerhand.domain.model.Hand
import me.dmadouros.pokerhand.domain.model.Position
import me.dmadouros.pokerhand.domain.model.PreviousAction
import me.dmadouros.pokerhand.domain.model.RuleSet

abstract class BaseRuleSet : RuleSet {
    protected data class Rule(
        val hand: String,
        val previousAction: PreviousAction,
        val position: Position,
        val action: Action,
    )

    protected abstract val rules: Set<Rule>

    override fun findAction(
        position: Position,
        previousAction: PreviousAction,
        hand: Hand,
    ): Action {
        val default =
            if (position == Position.BIG_BLIND && previousAction == PreviousAction.UNRAISED) {
                Action.CHECK
            } else {
                Action.FOLD
            }

        return rules
            .find { rule ->
                rule.position == position &&
                    rule.previousAction == previousAction &&
                    rule.hand == hand.toHandType()
            }?.action ?: default
    }
}
