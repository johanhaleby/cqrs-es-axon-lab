package se.haleby.cqrs.lab.itest.support

import org.awaitility.Awaitility
import org.junit.After
import org.junit.Before
import se.haleby.cqrs.lab.GameServer
import se.haleby.cqrs.lab.domain.command.Command
import se.haleby.cqrs.lab.domain.event.DomainEvent
import java.util.concurrent.TimeUnit.MILLISECONDS


open class EventSourcingITest {
    lateinit var gameServer: GameServer

    @Before
    fun `Game server is configured`() {
        gameServer = GameServer().start()
    }

    @After
    fun `Stopping game server after each test`() {
        gameServer.stop()
    }

    @Before
    fun `Awaitility is configured`() {
        Awaitility.setDefaultPollInterval(50, MILLISECONDS)
    }

    @After
    fun `Awaitility is reset`() {
        Awaitility.reset()
    }


    protected fun publishSync(command: Command, vararg moreCommands: Command) = mutableListOf(*moreCommands).apply { add(0, command) }.forEach { cmd ->
        gameServer.axon.commandGateway().sendAndWait<Any>(cmd)
    }

    protected fun eventsInEventStore(aggregateId: String): List<DomainEvent> =
            gameServer.axon.eventStore().readEvents(aggregateId).asSequence().map { it.payload as DomainEvent }.toList()
}