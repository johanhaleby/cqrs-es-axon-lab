package se.haleby.cqrs.lab.domain.projection

import se.haleby.cqrs.lab.domain.model.PlayerId

typealias Victories = Int

open class TopPlayerProjection {

//    @EventHandler
//    fun on(evt: SomeEvent) {
//        ongoingGames.add(OngoingGame(evt.gameId, evt.player))
//    }

//    @EventHandler
//    fun on(evt: SomeOtherEvent) {
//    }

    fun topPlayer() = TopPlayer("playerId", 1)

}

data class TopPlayer(val playerId: PlayerId, val victories: Victories)