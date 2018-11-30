package se.haleby.cqrs.lab.domain.model

import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.Before
import org.junit.Test
import se.haleby.cqrs.lab.domain.command.CreateGame
import se.haleby.cqrs.lab.domain.event.GameCreated
import java.util.*

class GameTest {

    lateinit var fixture: AggregateTestFixture<Game>

    @Before
    fun `Fixture is configured`() {
        fixture = AggregateTestFixture(Game::class.java)
    }

    @Test
    fun `when game is not started and create game is issued then game is created`() {
        val gameId = UUID.randomUUID().toString()
        val date = Date()

        fixture.givenNoPriorActivity()
                .`when`(CreateGame(gameId, date))
                .expectSuccessfulHandlerExecution()
                .expectEvents(GameCreated(gameId, date))
    }
}