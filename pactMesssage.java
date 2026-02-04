import au.com.dius.pact.consumer.PactBuilder;
import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.Pact;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.messaging.Message;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
class CalendarPublicationResponseMessagePactNewTest {

  @Pact(consumer = "caira-event-orchestrator", provider = "caira-calendar-publication")
  public V4Pact publicationResponseMessagePact(PactBuilder builder) {

    // IMPORTANT: cast to MessagePactBuilder (because PactBuilder is generic)
    MessagePactBuilder mb = (MessagePactBuilder) builder;

    String body = """
      {
        "Something": "COUNTRY"
      }
      """;

    return mb
      .given("SomeProviderState")
      .expectsToReceive("calendar publication response event")
      .withMetadata(md -> {
        md.add("contentType", "application/json");
        md.matchRegex("partitionKey", "[A-Z]{3}\\d{2}", "ABC01");
      })
      .withContent(body)
      .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "publicationResponseMessagePact")
  void shouldConsumePublicationResponseMessage(List<Message> messages) {
    Message msg = messages.get(0);

    String payload = new String(msg.getContents().value(), StandardCharsets.UTF_8);

    assertThat(payload).contains("AllRegRegulatoryUnitType");
  }
}
