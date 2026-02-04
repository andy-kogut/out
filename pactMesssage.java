package com.example.contract;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
class CalendarPublicationResponseMessagePactTest {

  // ===== CONTRACT DEFINITION =====
  @Pact(
    consumer = "caira-event-orchestrator",
    provider = "caira-calendar-publication"
  )
  public MessagePact publicationResponseMessagePact(
      MessagePactBuilder builder
  ) {

    String body = """
      {
        "AllRegRegulatoryUnitType": "COUNTRY"
      }
      """;

    return builder
      .given("SomeProviderState")
      .expectsToReceive("calendar publication response event")
      .withMetadata(Map.of(
        "contentType", "application/json",
        "partitionKey", "ABC01"
      ))
      .withContent(body)
      .toPact();
  }

  // ===== CONSUMER TEST =====
  @Test
  @PactTestFor(
    pactMethod = "publicationResponseMessagePact",
    providerType = ProviderType.ASYNCH,
    pactVersion = PactSpecVersion.V3
  )
  void shouldConsumePublicationResponseMessage(
      List<Message> messages
  ) {

    Message message = messages.get(0);

    String payload = new String(
        message.getContents().value(),
        StandardCharsets.UTF_8
    );

    // simulate real handler logic
    assertThat(payload).contains("COUNTRY");
  }
}
