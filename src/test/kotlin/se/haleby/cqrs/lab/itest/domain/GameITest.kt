package se.haleby.cqrs.lab.itest.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import se.haleby.cqrs.lab.domain.command.CreateGame
import se.haleby.cqrs.lab.domain.event.GameCreated
import se.haleby.cqrs.lab.itest.support.EventSourcingITest
import java.util.*

class GameITest : EventSourcingITest() {

    @Test
    fun `when game is not started and CreateGame command is published then game is created`() {
        // Given
        val gameId = UUID.randomUUID().toString()
        val timestamp = Date()

        // When
        publishSync(CreateGame(gameId, timestamp))

        // Then
        assertThat(eventsInEventStore(gameId)).containsOnly(GameCreated(gameId, timestamp))
    }
}