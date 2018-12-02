package se.haleby.cqrs.lab.domain.model

import org.axonframework.test.saga.SagaTestFixture
import org.junit.Before
import org.junit.Test
import se.haleby.cqrs.lab.domain.command.ExpireGame
import se.haleby.cqrs.lab.domain.event.GameCreated
import se.haleby.cqrs.lab.domain.event.GameEnded
import se.haleby.cqrs.lab.domain.event.GameIdleTimeExpired
import se.haleby.cqrs.lab.domain.event.MoveMade
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

class GameIdlePolicyTest {

    private lateinit var fixture: SagaTestFixture<GameIdlePolicy>

    @Before
    fun `Saga test fixture is initialized before each test`() {
        fixture = SagaTestFixture(GameIdlePolicy::class.java)
    }

    @Test
    fun `when GameCreated event is published then GameIdlePolicy saga is started and it publishes GameIdleTimeExpired event after 10 minutes of inactivity`() {
        val gameId = UUID.randomUUID().toString()
        val duration = Duration.of(10, ChronoUnit.MINUTES)

        fixture.givenNoPriorActivity()
                .whenPublishingA(GameCreated(gameId, Date()))
                .expectActiveSagas(1)
                .expectScheduledEvent(duration, GameIdleTimeExpired(gameId, duration))
    }

    @Test
    fun `when game is created and a move is made then the idle timeout is reset to 10 minutes`() {
        val gameId = UUID.randomUUID().toString()
        val playerId = UUID.randomUUID().toString()
        val duration = Duration.of(10, ChronoUnit.MINUTES)

        fixture.givenAPublished(GameCreated(gameId, Date()))
                .whenPublishingA(MoveMade(gameId, Date(), playerId, Paper))
                .expectActiveSagas(1)
                .expectScheduledEvent(duration, GameIdleTimeExpired(gameId, duration))
    }

    @Test
    fun `when game is ended then idle timeout is canceled`() {
        val gameId = UUID.randomUUID().toString()

        fixture.givenAPublished(GameCreated(gameId, Date()))
                .whenPublishingA(GameEnded(gameId))
                .expectActiveSagas(0)
                .expectNoScheduledEvents()
    }

    @Test
    fun `when game idle time is overdue then an ExpireGame command is dispatched`() {
        val gameId = UUID.randomUUID().toString()
        val duration = Duration.of(10, ChronoUnit.MINUTES)
        val date = Date()

        fixture.givenAPublished(GameCreated(gameId, Date()))
                .andThenTimeAdvancesTo(date.toInstant())
                .whenPublishingA(GameIdleTimeExpired(gameId, duration))
                .expectDispatchedCommands(ExpireGame(gameId, date))
                .expectActiveSagas(0)
    }
}