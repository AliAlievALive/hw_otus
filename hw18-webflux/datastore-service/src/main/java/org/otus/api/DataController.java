package org.otus.api;

import org.otus.domain.Message;
import org.otus.domain.MessageDto;
import org.otus.service.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@RestController
public class DataController {
    private static final Logger log = LoggerFactory.getLogger(DataController.class);
    private static final String MAGIC_ROOM = "1408";
    private final DataStore dataStore;
    private final Scheduler workerPool;

    public DataController(DataStore dataStore, Scheduler workerPool) {
        this.dataStore = dataStore;
        this.workerPool = workerPool;
    }

    @PostMapping(value = "/msg/{roomId}")
    public Mono<Long> messageFromChat(@PathVariable("roomId") String roomId, @RequestBody MessageDto messageDto) {
        if (MAGIC_ROOM.equals(roomId)) {
            throw new IllegalArgumentException("magic room");
        }
        var messageStr = messageDto.messageStr();

        var msgId = Mono.just(new Message(null, roomId, messageStr))
                .doOnNext(msg -> log.info("messageFromChat:{}", msg))
                .flatMap(dataStore::saveMessage)
                .publishOn(workerPool)
                .doOnNext(msgSaved -> log.info("msgSaved id:{}", msgSaved.id()))
                .map(Message::id)
                .subscribeOn(workerPool);

        log.info("messageFromChat, roomId:{}, msg:{} done", roomId, messageStr);
        return msgId;
    }

    @GetMapping(value = "/msg/{roomId}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MessageDto> getMessagesByRoomId(@PathVariable("roomId") String roomId) {
        return Mono.just(roomId)
                .doOnNext(room -> log.info("getMessagesByRoomId, room:{}", room))
                .flatMapMany(dataStore::loadMessages)
                .map(message -> new MessageDto(message.msgText()))
                .doOnNext(msgDto -> log.info("msgDto:{}", msgDto))
                .subscribeOn(workerPool);
    }

    @GetMapping(value = "/msg", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MessageDto> getMessagesByRoomId() {
        log.info("get all messages");
        return dataStore
                .getAllMessages()
                .map(message -> new MessageDto(message.msgText()))
                .doOnNext(msgDto -> log.info("msgDto:{}", msgDto))
                .subscribeOn(workerPool);
    }
}
