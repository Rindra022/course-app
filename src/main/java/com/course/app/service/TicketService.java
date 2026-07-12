package com.course.app.service;

import com.course.app.file.bucket.BucketComponent;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TicketService {
  private final BucketComponent bucketComponent;

  @SneakyThrows
  public String generateAndUploadTicket(UUID subscriptionId, String userName, String courseTitle) {
    var html = buildTicketHtml(userName, courseTitle);
    var pdfFile = File.createTempFile("ticket-" + subscriptionId, ".pdf");

    try (var outputStream = new FileOutputStream(pdfFile)) {
      var builder = new PdfRendererBuilder();
      builder.withHtmlContent(html, null);
      builder.toStream(outputStream);
      builder.run();
    }

    var bucketKey = "tickets/" + subscriptionId + ".pdf";
    bucketComponent.upload(pdfFile, bucketKey);
    return bucketComponent.presign(bucketKey, Duration.ofHours(24)).toString();
  }

  private String buildTicketHtml(String userName, String courseTitle) {
    return """
           <html>
             <body>
               <h1>Subscription confirmed</h1>
               <p>Dear %s,</p>
               <p>Your subscription to <strong>%s</strong> has been confirmed.</p>
               <p>This ticket is your proof of registration.</p>
             </body>
           </html>
           """
        .formatted(userName, courseTitle);
  }
}
