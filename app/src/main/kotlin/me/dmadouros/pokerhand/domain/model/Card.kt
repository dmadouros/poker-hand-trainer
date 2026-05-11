package me.dmadouros.pokerhand.domain.model

data class Card(
    val rank: Rank,
    val suit: Suit,
) : Comparable<Card> {
    override fun compareTo(other: Card): Int =
        if (this.rank == other.rank) {
            this.suit.compareTo(other.suit)
        } else {
            this.rank.compareTo(other.rank)
        }

    override fun toString(): String = "${rank.symbol}${suit.symbol}"
}