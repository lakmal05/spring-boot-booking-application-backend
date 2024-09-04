package com.serviceApartment.serviceAparmtnet.config.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendgridConfig {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public void sendEmail(String toEmail, String message) {
        Email from = new Email("your-email@example.com");
        Email to = new Email(toEmail);
        String subject = "Subject Here";
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            SendGrid sendGrid = new SendGrid(sendGridApiKey);
            Response response = sendGrid.api(request);

            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Headers: " + response.getHeaders());
        } catch (Exception ex) {
            System.out.println(ex + " Error sending email");
        }
    }
}
