package se.haleby.cqrs.lab.domain.model

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.Timestamp
import org.axonframework.eventhandling.scheduling.EventScheduler
import org.axonframework.eventhandling.scheduling.ScheduleToken
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import se.haleby.cqrs.lab.domain.command.ExpireGame
import se.haleby.cqrs.lab.domain.event.GameCreated
import se.haleby.cqrs.lab.domain.event.GameEnded
import se.haleby.cqrs.lab.domain.event.GameIdleTimeExpired
import se.haleby.cqrs.lab.domain.event.MoveMade
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit.MINUTES
import java.util.*
import javax.inject.Inject


class GameIdlePolicy {
    @Inject
    @Transient
    private lateinit var eventScheduler: EventScheduler
    @Inject
    @Transient
    private lateinit var commandGateway: CommandGateway

    companion object {
        val TIMEOUT : Duration = Duration.of(10, MINUTES)
    }

    lateinit var deadline: ScheduleToken

    @StartSaga
    @SagaEventHandler(associationProperty = "gameId")
    fun on(event: GameCreated) {
        deadline = eventScheduler.schedule(TIMEOUT, GameIdleTimeExpired(event.gameId, TIMEOUT));
    }

    @SagaEventHandler(associationProperty = "gameId")
    fun on(event: MoveMade) {
        // Reset idle timeout when move is made
        deadline = eventScheduler.reschedule(deadline, TIMEOUT, GameIdleTimeExpired(event.gameId, TIMEOUT));
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "gameId")
    fun on(event: GameIdleTimeExpired, @Timestamp instant: Instant) {
        commandGateway.send<GameId>(ExpireGame(event.gameId, Date(instant.toEpochMilli())))
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "gameId")
    fun on(event: GameEnded) {
        eventScheduler.cancelSchedule(deadline);
    }
}