package se.haleby.cqrs.lab.itest.projection

import org.awaitility.kotlin.await
import org.awaitility.kotlin.matches
import org.awaitility.kotlin.untilCallTo
import org.junit.Test
import se.haleby.cqrs.lab.domain.command.CreateGame
import se.haleby.cqrs.lab.domain.projection.TopPlayer
import se.haleby.cqrs.lab.itest.support.EventSourcingITest
import java.util.*

class TopPlayerProjectionITest : EventSourcingITest() {

    @Test
    fun `when multiple games are played and won by the same player then a top player projection returns this player`() {
        // When
        val gameId1 = uuid()
        val playerId1 = uuid()

        publishSync(
                CreateGame(gameId1, Date())

                // TODO More command

        )

        // Then
        await untilCallTo { gameServer.topPlayerProjection.topPlayer() } matches { player -> player == TopPlayer(playerId1, 2) }
    }
}

private fun uuid() = UUID.randomUUID().toString()