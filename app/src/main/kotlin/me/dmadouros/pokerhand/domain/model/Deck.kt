package me.dmadouros.pokerhand.domain.model

@ConsistentCopyVisibility
data class Deck private constructor(
    private val cards: List<Card>,
) {
    companion object {
        fun create(): Deck {
            val cards =
                Suit.entries.flatMap { suit ->
                    Rank.entries.map { rank ->
                        Card(rank, suit)
                    }
                }
            return Deck(cards)
        }
    }

    fun shuffle(): ShuffledDeck = ShuffledDeck(cards.shuffled())
}