package se.haleby.cqrs.lab.domain.event

import se.haleby.cqrs.lab.domain.model.GameId
import se.haleby.cqrs.lab.domain.model.Move
import se.haleby.cqrs.lab.domain.model.PlayerId
import java.util.*

data class MoveMade(val gameId: GameId, val timestamp: Date, val playerId: PlayerId, val move: Move) : DomainEvent