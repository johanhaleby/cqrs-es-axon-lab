package se.haleby.cqrs.lab.domain.event

import se.haleby.cqrs.lab.domain.model.GameId

data class GameAborted(val gameId: GameId) : DomainEvent