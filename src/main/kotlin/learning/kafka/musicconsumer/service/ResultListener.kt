package learning.kafka.musicconsumer.service

import com.beust.klaxon.Klaxon
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ResultListener {

    val artistResultMap: HashMap<String, ArtistResult> = HashMap()

    @KafkaListener(topics = ["result"], groupId = "default")
    fun consume(message: String) {
        val artistResult = ArtistResult.fromJson(message)
        artistResultMap[artistResult!!.artist] = artistResult
    }
}

data class ArtistResult(val artist: String, val listenersCount: Long, val songs: List<String>) {

    companion object {
        fun fromJson(jsonString: String): ArtistResult? {
            return Klaxon().parse<ArtistResult>(jsonString)
        }
    }
}
