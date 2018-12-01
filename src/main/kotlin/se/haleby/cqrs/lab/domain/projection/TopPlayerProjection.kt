package se.haleby.cqrs.lab.domain.projection

import org.axonframework.eventhandling.EventHandler
import se.haleby.cqrs.lab.domain.event.GameStarted
import se.haleby.cqrs.lab.domain.event.GameWon
import se.haleby.cqrs.lab.domain.model.GameId
import se.haleby.cqrs.lab.domain.model.PlayerId
import java.util.concurrent.atomic.AtomicReference

typealias Victories = Int


open class TopPlayerProjection {

    private val topPlayer: AtomicReference<TopPlayer?> = AtomicReference()

    private val ongoingGames: MutableList<OngoingGame> = mutableListOf()

    @EventHandler
    fun on(evt: GameStarted) {
        ongoingGames.add(OngoingGame(evt.gameId, evt.player))
    }

    @EventHandler
    fun on(evt: GameWon) {
        topPlayer.updateAndGet { topPlayer ->
            when {
                topPlayer == null -> TopPlayer(evt.winnerId, 1)
                topPlayer.playerId == evt.winnerId -> topPlayer.copy(victories = topPlayer.victories.inc())
                topPlayer.playerId == evt.looserId -> topPlayer.copy(victories = topPlayer.victories.dec())
                else -> null
            }
        }
        ongoingGames.removeIf { game -> game.gameId == evt.gameId }
    }

    fun topPlayer(): TopPlayer? = topPlayer.get()
}

data class TopPlayer(val playerId: PlayerId, val victories: Victories)

private data class OngoingGame(val gameId: GameId, val playerId1: PlayerId, val playerId2: PlayerId? = null)