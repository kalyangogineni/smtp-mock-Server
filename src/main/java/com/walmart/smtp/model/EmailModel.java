package com.walmart.smtp.model;

import java.util.Date;

public class EmailModel {

    private String to;
    private String from;
    private String subject;
    private String body;
    private Date receivedDate;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Override
    public String toString() {
        return "{" +
                "\"to\": \"" + to + "\"," +
                 "\"from\": \"" + from + "\"," +
                "\"subject\": \"" + subject + "\"," +
                "\"body\": \"" + body + "\"," +
                "\"receivedDate\": \"" + receivedDate +"\""+
                "}";
    }
}
