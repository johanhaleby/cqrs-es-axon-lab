package se.haleby.cqrs.lab.domain.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import se.haleby.cqrs.lab.domain.model.GameId
import java.util.*

data class CreateGame(@TargetAggregateIdentifier val gameId: GameId, val timestamp: Date) : Command