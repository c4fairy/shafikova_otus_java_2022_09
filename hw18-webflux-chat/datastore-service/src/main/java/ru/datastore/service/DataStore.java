package ru.datastore.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.datastore.domain.Message;

public interface DataStore {

    Mono<Message> saveMessage(Message message);

    Flux<Message> loadMessages(String roomId);

    Flux<Message> getAllMessages();
}
