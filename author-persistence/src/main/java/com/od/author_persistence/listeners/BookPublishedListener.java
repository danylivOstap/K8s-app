package com.od.author_persistence.listeners;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.od.author_persistence.model.Author;
import com.od.author_persistence.model.Notification;
import com.od.author_persistence.exceptions.InvalidMessageException;
import com.od.author_persistence.services.AuthorService;
import com.od.author_persistence.services.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile("production")
@Slf4j
@RequiredArgsConstructor
public class BookPublishedListener {
	private final ObjectMapper objectMapper;
	private final AuthorService authorService;
	private final NotificationService notificationService;

	@KafkaListener(topics = "books.published")
	public String listens(final String in) {
		log.info("Received Book: {}", in);
		try {
			final Map<String, Object> payload = readJsonAsMap(in);

			final Author author = authorFromPayload(payload);
			final Author savedAuthor = authorService.save(author);

			final String message = String.format(
				"Author '%s' [%d] persisted!",
				savedAuthor.getName(),
				savedAuthor.getId()
			);
			notificationService.publishNotification(
				Notification.builder()
					.message(message)
					.timestamp(LocalDateTime.now())
					.service("author-persistence")
				.build());

		} catch(final InvalidMessageException ex) {
			log.error("Invalid message received: {}", in);
		}


		return in;
	}

	private Map<String, Object> readJsonAsMap(final String json) {
		try{
			final TypeReference<HashMap<String,Object>> typeRef =
					new TypeReference<HashMap<String,Object>>() {};
			return objectMapper.readValue(json, typeRef);
		} catch(JsonProcessingException ex) {
			throw new InvalidMessageException();
		}
	}

	private Author authorFromPayload(final Map<String, Object> payload) {
		final Map<String, Object> authorMap = (HashMap<String, Object>) payload.get("author");
        return Author.builder()
			.id(((Integer)authorMap.get("id")).longValue())
			.name(authorMap.get("name").toString())
			.age((Integer)authorMap.get("age"))
			.build();
	}
}
