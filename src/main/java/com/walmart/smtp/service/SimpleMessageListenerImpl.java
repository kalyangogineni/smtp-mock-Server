package com.walmart.smtp.service;

import com.walmart.smtp.model.EmailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.subethamail.smtp.helper.SimpleMessageListener;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


/**
 * Listens to incoming emails and redirects them to the {@code MailSaver} object.
 *
 * @author kgogin1
 * @since 1.0
 */

@Service
@EnableAutoConfiguration
public final class SimpleMessageListenerImpl implements SimpleMessageListener {

    private Logger logger = LoggerFactory.getLogger(SimpleMessageListenerImpl.class);

    public SimpleMessageListenerImpl() {
    }

    @Override
    public boolean accept(String from, String recipient) {
        return true;
    }

    @Override
    public void deliver(String from, String recipient, InputStream data) {
        try {


            MimeMessage message = new MimeMessage(Session.getDefaultInstance(new Properties()), data);
            EmailModel emailModel = new EmailModel();
            emailModel.setFrom(from);
            emailModel.setTo(recipient);
            emailModel.setBody(getTextFromMessage(message));
            emailModel.setReceivedDate(new Date());
            emailModel.setSubject(message.getSubject());
            logger.info("email received is {}",emailModel.toString());
            Path newFilePath = Paths.get("src/main/resources/data/emailData.sql");
            File file= new File(newFilePath.toUri());
            if(!file.exists()){
                logger.info("file does not exist");
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.newLine();
            String query = String.format("INSERT INTO email (email,data,date) values('%s','%s','%s');", recipient,emailModel.toString(),new Date());
            writer.write(query);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String getTextFromMessage(MimeMessage message) throws Exception{
        String result = "";
        if(message.isMimeType("text/plain")){
            result = message.getContent().toString().trim();
        }
//        else if (message.isMimeType("multipart/*")) {
//            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
//            result = getTextFromMimeMultipart(mimeMultipart);
//        }
        return result;

    }

//    private String getTextFromMimeMultipart(
//            MimeMultipart mimeMultipart) throws IOException, MessagingException {
//
//        int count = mimeMultipart.getCount();
//        if (count == 0)
//            throw new MessagingException("Multipart with no body parts not supported.");
//        boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
//        if (multipartAlt)
//            // alternatives appear in an order of increasing
//            // faithfulness to the original content. Customize as req'd.
//            return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
//        String result = "";
//        for (int i = 0; i < count; i++) {
//            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
//            result += getTextFromBodyPart(bodyPart);
//        }
//        return result;
//    }
//
//    private String getTextFromBodyPart(
//            BodyPart bodyPart) throws IOException, MessagingException {
//
//        String result = "";
//        if (bodyPart.isMimeType("text/plain")) {
//            result = (String) bodyPart.getContent();
//        } else if (bodyPart.isMimeType("text/html")) {
//            String html = (String) bodyPart.getContent();
//            result = org.jsoup.Jsoup.parse(html).text();
//        } else if (bodyPart.getContent() instanceof MimeMultipart){
//            result = getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
//        }
//        return result;
//    }
}
