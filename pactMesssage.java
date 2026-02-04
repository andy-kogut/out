import au.com.dius.pact.consumer.MessagePact;
import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.Pact;
import au.com.dius.pact.core.model.messaging.Message;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
class CalendarPublicationResponseMessagePactTest {

  @Pact(
    consumer = "caira-event-orchestrator",
    provider = "caira-calendar-publication"
  )
  MessagePact publicationResponseMessagePact(
      MessagePactBuilder builder
  ) {

    String body = """
      {
        "AllRegRegulatoryUnitType": "COUNTRY"
      }
      """;

    return builder
      .expectsToReceive("calendar publication response event")
      .withMetadata(Map.of(
        "contentType", "application/json",
        "partitionKey", "ABC01"
      ))
      .withContent(body)
      .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "publicationResponseMessagePact")
  void shouldConsumePublicationResponseMessage(
      List<Message> messages
  ) {
    Message message = messages.get(0);

    String payload = new String(
        message.getContents().value(),
        StandardCharsets.UTF_8
    );

    assertThat(payload).contains("COUNTRY");
  }
}
