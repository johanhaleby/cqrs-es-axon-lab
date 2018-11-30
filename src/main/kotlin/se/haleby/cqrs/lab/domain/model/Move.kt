package se.haleby.cqrs.lab.domain.model

sealed class Move
object Rock : Move()
object Paper : Move()
object Scissors : Move()

fun Move.beats(otherMove: Move) : Boolean = when (this) {
    Rock -> otherMove == Scissors
    Paper -> otherMove == Rock
    Scissors -> otherMove == Paper
}