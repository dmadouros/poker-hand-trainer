package me.dmadouros.pokerhand.domain.model

enum class Action(
    val display: String,
) {
    RAISE("Raise"),
    CALL("Call"),
    FOLD("Fold"),
    CHECK("Check"),
}