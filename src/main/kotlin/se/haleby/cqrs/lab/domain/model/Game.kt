package se.haleby.cqrs.lab.domain.model

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventhandling.EventHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.modelling.command.AggregateRoot
import se.haleby.cqrs.lab.domain.command.CreateGame
import se.haleby.cqrs.lab.domain.command.MakeMove
import se.haleby.cqrs.lab.domain.event.GameCreated
import se.haleby.cqrs.lab.domain.event.GameEnded
import se.haleby.cqrs.lab.domain.event.GameStarted
import se.haleby.cqrs.lab.domain.event.MoveMade
import java.util.*

typealias GameId = String
typealias PlayerId = String

data class PlayerMove(val playerId: PlayerId, val move: Move) {
    fun isMadeBy(playerId: PlayerId) = this.playerId == playerId
}

sealed class GameState
object Uninitialized : GameState()
object Created : GameState()
object Ongoing : GameState()
object Ended : GameState()

class GameAlreadyStartedException : RuntimeException("Game already started")
class GameAlreadyEndedException : RuntimeException("Game already ended")
class PlayerAlreadyPlayedException : RuntimeException("Player already ended")

@AggregateRoot
data class Game(@AggregateIdentifier private var gameId: GameId, private var createdAt: Date, private var gameState: GameState,
                private var playerMove1: PlayerMove? = null, private var playerMove2: PlayerMove? = null) {


}