package se.haleby.cqrs.lab

import org.awaitility.kotlin.await
import org.awaitility.kotlin.until
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.GenericCommandMessage
import org.axonframework.eventsourcing.eventstore.EventStore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import se.haleby.cqrs.lab.domain.command.MakeMove
import se.haleby.cqrs.lab.domain.model.Rock
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
class GameITest {

    @Autowired
    lateinit var commandBus: CommandBus

    @Autowired
    lateinit var eventStore : EventStore

    @Test
    fun `Ikk`() {
        // Given
        val gameId = UUID.randomUUID().toString()
        val cmd = MakeMove(gameId, Date(), "player1", Rock)

        // When
        commandBus.dispatch(GenericCommandMessage(cmd))

        // Then
        await until { eventStore.readEvents(gameId).asSequence().count() == 1  }
    }
}