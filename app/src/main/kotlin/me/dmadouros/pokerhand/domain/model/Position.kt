package me.dmadouros.pokerhand.domain.model

enum class Position(
    val display: String,
) {
    EARLY("Early Position"),
    MIDDLE("Middle Position"),
    LATE("Late Position"),
    SMALL_BLIND("the Small Blind"),
    BIG_BLIND("the Big Blind"),
    ;

    companion object {
        fun random(): Position = Position.entries.random()
    }
}
