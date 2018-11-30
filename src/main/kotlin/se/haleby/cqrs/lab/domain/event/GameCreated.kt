package se.haleby.cqrs.lab.domain.event

import se.haleby.cqrs.lab.domain.model.GameId
import java.util.*

data class GameCreated(val gameId: GameId, val timestamp : Date) : DomainEvent