package se.haleby.cqrs.lab.domain.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import se.haleby.cqrs.lab.domain.model.GameId
import se.haleby.cqrs.lab.domain.model.Move
import se.haleby.cqrs.lab.domain.model.PlayerId
import java.util.*

data class MakeMove(@TargetAggregateIdentifier val gameId: GameId, val timestamp: Date, val playerId: PlayerId, val move: Move) : Command