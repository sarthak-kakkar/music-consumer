package learning.kafka.musicconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MusicConsumerApplication

fun main(args: Array<String>) {
    runApplication<MusicConsumerApplication>(*args)
}
