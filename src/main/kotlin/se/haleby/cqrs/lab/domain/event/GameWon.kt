package se.haleby.cqrs.lab.domain.event

import se.haleby.cqrs.lab.domain.model.GameId
import se.haleby.cqrs.lab.domain.model.PlayerId

data class GameWon(val gameId: GameId, val winnerId: PlayerId, val looserId: PlayerId) : DomainEvent