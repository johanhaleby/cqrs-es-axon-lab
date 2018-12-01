package se.haleby.cqrs.lab.domain.model

import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.Before
import org.junit.Test
import se.haleby.cqrs.lab.domain.command.CreateGame
import se.haleby.cqrs.lab.domain.command.MakeMove
import se.haleby.cqrs.lab.domain.event.*
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

    @Test
    fun `when game is started and create game is issued then GameAlreadyStartedException is thrown`() {
        val gameId = UUID.randomUUID().toString()
        val date = Date()

        fixture.given(GameCreated(gameId, date))
                .`when`(CreateGame(gameId, date))
                .expectException(GameAlreadyStartedException::class.java)
                .expectExceptionMessage("Game already started")
    }

    @Test
    fun `when game is played by one player then next move ends game`() {
        val playerId1 = UUID.randomUUID().toString()
        val playerId2 = UUID.randomUUID().toString()
        val gameId = UUID.randomUUID().toString()
        val date = Date()

        fixture.given(GameCreated(gameId, date), GameStarted(gameId, playerId1), MoveMade(gameId, date, playerId1, Rock))
                .`when`(MakeMove(gameId, date, playerId2, Scissors))
                .expectSuccessfulHandlerExecution()
                .expectEvents(MoveMade(gameId, date, playerId2, Scissors), GameWon(gameId, playerId1, playerId2), GameEnded(gameId))
    }
}