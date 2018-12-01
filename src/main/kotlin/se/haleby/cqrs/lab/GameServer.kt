package se.haleby.cqrs.lab

import org.axonframework.config.Configuration
import org.axonframework.config.DefaultConfigurer
import org.axonframework.config.EventProcessingModule
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import se.haleby.cqrs.lab.domain.model.Game
import se.haleby.cqrs.lab.domain.projection.TopPlayerProjection
import java.util.*

inline fun <reified T : Any> loggerFor(): Logger = LoggerFactory.getLogger(T::class.java)

class GameServer {
    private val log = loggerFor<GameServer>()

    val axon: Configuration
    val topPlayerProjection: TopPlayerProjection

    init {
        log.info("Starting {}", GameServer::class.simpleName)

        topPlayerProjection = TopPlayerProjection()
        axon = configureAxon(topPlayerProjection)
    }

    fun start(): GameServer {
        axon.start()
        return this
    }

    fun stop() {
        axon.shutdown()
    }

    private fun configureAxon(vararg projections: Any): Configuration {
        val eventProcessingModule = EventProcessingModule()
        Arrays.stream(projections).forEach { projection -> eventProcessingModule.registerEventHandler { projection } }
        return DefaultConfigurer.defaultConfiguration()
                .configureAggregate<Game>(Game::class.java)
                .configureEventStore { EmbeddedEventStore.builder().storageEngine(InMemoryEventStorageEngine()).build() }
                .registerModule(eventProcessingModule)
                // .registerQueryHandler(c -> projection)
                .buildConfiguration()
    }
}
