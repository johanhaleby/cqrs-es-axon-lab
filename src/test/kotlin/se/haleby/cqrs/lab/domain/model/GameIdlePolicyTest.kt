package se.haleby.cqrs.lab.domain.model

import org.axonframework.test.saga.SagaTestFixture
import org.junit.Before
import org.junit.Test
import se.haleby.cqrs.lab.domain.event.GameCreated
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

class GameIdlePolicyTest {

//    private lateinit var fixture: SagaTestFixture<GameIdlePolicy>
//
//    @Before
//    fun `Saga test fixture is initialized before each test`() {
//        fixture = SagaTestFixture(GameIdlePolicy::class.java)
//    }
//
//    @Test
//    fun `when GameCreated event is published then GameIdlePolicy saga is started and it publishes GameIdleTimeExpired event after 10 minutes of inactivity`() {
//        val gameId = UUID.randomUUID().toString()
//        val duration = Duration.of(10, ChronoUnit.MINUTES)
//
//        fixture.givenNoPriorActivity()
//                .whenPublishingA(GameCreated(gameId, Date()))
//                .expectActiveSagas(1)
//                .expectScheduledEvent(duration, GameIdleTimeExpired(gameId, duration))
//    }
}