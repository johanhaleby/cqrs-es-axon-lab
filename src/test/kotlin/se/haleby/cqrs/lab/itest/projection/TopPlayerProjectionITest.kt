package se.haleby.cqrs.lab.itest.projection

import org.awaitility.kotlin.await
import org.awaitility.kotlin.matches
import org.awaitility.kotlin.untilCallTo
import org.junit.Test
import se.haleby.cqrs.lab.domain.command.CreateGame
import se.haleby.cqrs.lab.domain.command.MakeMove
import se.haleby.cqrs.lab.domain.model.Rock
import se.haleby.cqrs.lab.domain.model.Scissors
import se.haleby.cqrs.lab.domain.projection.TopPlayer
import se.haleby.cqrs.lab.itest.support.EventSourcingITest
import java.util.*

class TopPlayerProjectionITest : EventSourcingITest() {

    @Test
    fun `when game is not started and CreateGame command is published then game is created`() {
        // Given

        // When
        val gameId1 = uuid()
        val gameId2 = uuid()
        val playerId1 = uuid()
        val playerId2 = uuid()

        publishSync(
                CreateGame(gameId1, Date()),
                MakeMove(gameId1, Date(), playerId1, Rock),
                MakeMove(gameId1, Date(), playerId2, Scissors),

                CreateGame(gameId2, Date()),
                MakeMove(gameId2, Date(), playerId1, Rock),
                MakeMove(gameId2, Date(), playerId2, Scissors)
        )

        // Then
        await untilCallTo { gameServer.topPlayerProjection.topPlayer() } matches { player -> player == TopPlayer(playerId1, 2) }
    }
}

private fun uuid() = UUID.randomUUID().toString()