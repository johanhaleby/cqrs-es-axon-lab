package se.haleby.cqrs.lab.domain.event

import se.haleby.cqrs.lab.domain.model.GameId
import java.time.Duration

data class GameIdleTimeExpired(val gameId: GameId, val idledFor: Duration)