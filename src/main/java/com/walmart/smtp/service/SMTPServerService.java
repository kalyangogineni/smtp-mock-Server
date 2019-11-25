package com.walmart.smtp.service;

import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.stereotype.Service;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;

@EnableSpringConfigured
@Service
public class SMTPServerService {

    private String enabled = "true";
    private String port ="3000";
    private String hostName="localhost";

    private SMTPServer smtpServer;

    public SMTPServerService() {

    }

    @PostConstruct
    public void start() {
        try {
            hostName = InetAddress.getLocalHost().toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(enabled.equalsIgnoreCase("true")){
            SimpleMessageListenerImpl l = new SimpleMessageListenerImpl();
            smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(l));
            smtpServer.setHostName(this.hostName);
            smtpServer.setPort(Integer.parseInt(port));
            smtpServer.start();
            System.out.println("****** SMTP Server is running for domain "+smtpServer.getHostName()+" on port "+smtpServer.getPort());
        } else {
            System.out.println("****** SMTP Server NOT ENABLED by settings ");
        }
    }
    @PreDestroy
    public void stop() {
        if(enabled.equalsIgnoreCase("true")){
            System.out.println("****** Stopping SMTP Server for domain "+smtpServer.getHostName()+" on port "+smtpServer.getPort());
            smtpServer.stop();
        }
    }
    public boolean isRunning() {
        return smtpServer.isRunning();
    }
}