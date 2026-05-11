package me.dmadouros.pokerhand.domain.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class CardTest {
    @Test
    fun testHandSortSameRankDifferentSuits() {
        val card1 = Card(Rank.ACE, Suit.HEARTS)
        val card2 = Card(Rank.ACE, Suit.SPADES)

        val cards = listOf(card1, card2)

        val actual = cards.sorted()

        assertThat(actual).isEqualTo(listOf(card2, card1))
    }

    @Test
    fun testHandSortDifferentRankDifferentSuits() {
        val card1 = Card(Rank.KING, Suit.HEARTS)
        val card2 = Card(Rank.ACE, Suit.SPADES)

        val cards = listOf(card1, card2)

        val actual = cards.sorted()

        assertThat(actual).isEqualTo(listOf(card2, card1))
    }

    @Test
    fun testHandSortDifferentRankSameSuits() {
        val card1 = Card(Rank.KING, Suit.SPADES)
        val card2 = Card(Rank.ACE, Suit.SPADES)

        val cards = listOf(card1, card2)

        val actual = cards.sorted()

        assertThat(actual).isEqualTo(listOf(card2, card1))
    }
}