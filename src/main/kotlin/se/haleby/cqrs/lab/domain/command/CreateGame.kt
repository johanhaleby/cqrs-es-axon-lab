package se.haleby.cqrs.lab.domain.command

import se.haleby.cqrs.lab.domain.model.GameId
import java.util.*

data class CreateGame(val gameId: GameId, val timestamp: Date)