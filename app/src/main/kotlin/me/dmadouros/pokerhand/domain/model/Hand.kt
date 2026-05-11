package me.dmadouros.pokerhand.domain.model

@ConsistentCopyVisibility
data class Hand private constructor(
    private val cards: List<Card>,
) {
    companion object {
        fun create(cards: List<Card>): Hand = Hand(cards.sorted())
    }

    private fun isSuited(): Boolean = cards[0].suit == cards[1].suit

    override fun toString(): String = "${cards[0]}${cards[1]}"

    fun toHandType(): String {
        val suited =
            if (isSuited()) {
                "s"
            } else {
                ""
            }
        return "${cards[0].rank.symbol}${cards[1].rank.symbol}$suited"
    }
}
