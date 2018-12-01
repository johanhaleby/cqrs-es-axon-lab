package se.haleby.cqrs.lab.domain.event

import se.haleby.cqrs.lab.domain.model.GameId
import se.haleby.cqrs.lab.domain.model.PlayerId

data class GameStarted(val gameId: GameId, val player : PlayerId) : DomainEvent