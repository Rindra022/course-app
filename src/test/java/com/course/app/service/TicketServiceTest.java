package com.course.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.course.app.file.bucket.BucketComponent;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class TicketServiceTest {

  private final BucketComponent bucketComponent = mock(BucketComponent.class);
  private final TicketService service = new TicketService(bucketComponent);

  @Test
  @SneakyThrows
  void generateAndUploadTicket_should_upload_pdf_and_return_presigned_url() {
    var subscriptionId = UUID.randomUUID();
    var expectedUrl = new URL("https://bucket.s3.amazonaws.com/tickets/" + subscriptionId + ".pdf");

    when(bucketComponent.presign(eq("tickets/" + subscriptionId + ".pdf"), any(Duration.class)))
        .thenReturn(expectedUrl);

    var result = service.generateAndUploadTicket(subscriptionId, "jdoe", "Spring Boot 101");

    assertThat(result).isEqualTo(expectedUrl.toString());
    verify(bucketComponent).upload(any(), eq("tickets/" + subscriptionId + ".pdf"));
  }
}
