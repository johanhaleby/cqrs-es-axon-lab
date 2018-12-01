package se.haleby.cqrs.lab.domain.model

sealed class Move
object Rock : Move() {
    override fun toString(): String {
        return "Rock"
    }
}

object Paper : Move() {
    override fun toString(): String {
        return "Paper"
    }
}

object Scissors : Move() {
    override fun toString(): String {
        return "Scissors"
    }
}

fun Move.beats(otherMove: Move): Boolean = when (this) {
    Rock -> otherMove == Scissors
    Paper -> otherMove == Rock
    Scissors -> otherMove == Paper
}