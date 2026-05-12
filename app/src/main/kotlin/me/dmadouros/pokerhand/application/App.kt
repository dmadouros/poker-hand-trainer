package me.dmadouros.pokerhand.application

import com.github.ajalt.mordant.terminal.StringPrompt
import com.github.ajalt.mordant.terminal.Terminal
import com.github.ajalt.mordant.terminal.prompt
import me.dmadouros.pokerhand.domain.model.Action
import me.dmadouros.pokerhand.domain.model.Deck
import me.dmadouros.pokerhand.domain.model.Position
import me.dmadouros.pokerhand.domain.model.PreviousAction
import me.dmadouros.pokerhand.domain.model.ShuffledDeck
import me.dmadouros.pokerhand.infrastructure.RuleSet

private const val UNICODE_CHECKMARK = "\u2705"
private const val UNICODE_X = "\u274C"

data class Stats(
    private val previousActions: MutableMap<PreviousAction, Int> = mutableMapOf(),
    private val positions: MutableMap<Position, Int> = mutableMapOf(),
    private val actions: MutableMap<Action, Int> = mutableMapOf(),
) {
    fun onPreviousAction(previousAction: PreviousAction) {
        if (!previousActions.containsKey(previousAction)) {
            previousActions[previousAction] = 0
        }
        previousActions[previousAction] = previousActions[previousAction]!! + 1
    }

    fun onPosition(position: Position) {
        if (!positions.containsKey(position)) {
            positions[position] = 0
        }
        positions[position] = positions[position]!! + 1
    }

    fun onAction(action: Action) {
        if (!actions.containsKey(action)) {
            actions[action] = 0
        }
        actions[action] = actions[action]!! + 1
    }
}

class App {
    fun start() {
        val t = Terminal(interactive = true)
        val ruleSet = RuleSet()
        val deck = Deck.create()
        val stats = Stats()

        var score = 0
        val maxQuestions = 100
        (1..maxQuestions).forEach { i ->
            t.clearScreen()
            t.println("Question $i of $MAX_QUESTIONS.")
            score += tick(t, ruleSet, deck.shuffle(), stats)

            t.println()
            t.println("Your score: $score/$i")

            t.println()
            t.prompt(
                "Press <enter> to continue",
                choices = listOf(""),
                promptSuffix = " ",
                showChoices = false,
            )
        }
        t.clearScreen()
        t.println("Your final score: $score/$MAX_QUESTIONS")
        t.println()
        t.println(stats)
    }

    private fun tick(
        t: Terminal,
        ruleSet: RuleSet,
        deck: ShuffledDeck,
        stats: Stats,
    ): Int {
        t.println()

        val position = Position.random()
        stats.onPosition(position)
        t.println("You are in ${position.display}.")

        val previousAction = PreviousAction.weightedRandom()
        stats.onPreviousAction(previousAction)
        t.println("${previousAction.display}.")

        val hand = deck.dealHand()
        t.println("You've been dealt: $hand.")

        t.println()

        val options = buildOptions(previousAction, position)
        t.println("What should you do?")
        options.forEachIndexed { index, it -> t.println("${index + 1}. ${it.display}") }

        val input =
            StringPrompt(
                "",
                terminal = t,
                choices = List(options.size) { index -> "${index + 1}" },
                showChoices = false,
                allowBlank = false,
                promptSuffix = "",
            ).ask()!!.toInt()
        val actual = options[input - 1]
        stats.onAction(actual)

        val expected = ruleSet.findAction(position, previousAction, hand)
        val symbol =
            if (expected == actual) {
                UNICODE_CHECKMARK
            } else {
                UNICODE_X
            }
        t.println()
        t.println("$symbol Your answer: ${actual.display}; Correct answer: ${expected.display}")

        return if (expected == actual) {
            1
        } else {
            0
        }
    }

    private fun buildOptions(
        previousAction: PreviousAction,
        position: Position,
    ): List<Action> =
        if (previousAction == PreviousAction.UNRAISED && position == Position.BIG_BLIND) {
            listOf(Action.CHECK, Action.RAISE)
        } else {
            listOf(Action.CALL, Action.RAISE, Action.FOLD)
        }
}

fun main() {
    App().start()
}

fun Terminal.clearScreen() {
    this.cursor.move {
        setPosition(0, 0)
        clearScreenAfterCursor()
    }
}
