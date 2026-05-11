package me.dmadouros.pokerhand.domain.model

private const val UNICODE_SPADE = "\u2660"
private const val UNICODE_HEART = "\u2665"
private const val UNICODE_CLUB = "\u2663"
private const val UNICODE_DIAMOND = "\u2666"

enum class Suit(
    val symbol: String,
) {
    SPADES(UNICODE_SPADE),
    HEARTS(UNICODE_HEART),
    CLUBS(UNICODE_CLUB),
    DIAMONDS(UNICODE_DIAMOND),
}