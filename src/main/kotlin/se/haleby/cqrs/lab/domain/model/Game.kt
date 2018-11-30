package se.haleby.cqrs.lab.domain.model

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.modelling.command.AggregateRoot
import se.haleby.cqrs.lab.domain.command.CreateGame
import se.haleby.cqrs.lab.domain.event.GameCreated
import java.util.*

typealias GameId = String
typealias PlayerId = String

sealed class GameState
object Created : GameState()
object Ongoing : GameState()
object Ended : GameState()

class GameAlreadyStartedException : RuntimeException("Game already started")
class GameAlreadyEndedException : RuntimeException("Game already ended")
class PlayerAlreadyPlayedException : RuntimeException("Player already ended")

@AggregateRoot
class Game internal constructor() {

    @AggregateIdentifier
    private lateinit var gameId: GameId
    private lateinit var createdAt: Date
    private var gameState: GameState? = null

    @CommandHandler
    constructor(cmd: CreateGame) : this() {
        apply(GameCreated(cmd.gameId, cmd.timestamp))
    }

//    @CommandHandler
//    fun handle(cmd: OtherCommand) {
//    }

    /*
     * Technically, @EventSourcingHandler and @EventHandler are exactly identical.
     * They merely have different names to allow you to be explicit about the intent of the handlers.
     * Use @EventSourcingHandler in your Aggregates and @EventHandler for projections.
     */
    @EventSourcingHandler
    private fun on(evt: GameCreated) {
        gameId = evt.gameId
        createdAt = evt.timestamp
        gameState = Created
    }
}