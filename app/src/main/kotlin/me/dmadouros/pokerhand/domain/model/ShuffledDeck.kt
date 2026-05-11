package me.dmadouros.pokerhand.domain.model

data class ShuffledDeck(
    private val cards: List<Card>,
) {
    fun dealHand(): Hand {
        return Hand.create(cards.take(2))
    }
}