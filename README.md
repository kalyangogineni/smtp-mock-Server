# smtp-mock-Server
Mock your SMTP server and get the text , subject and recipient information through an API


****

###Build package
```mvn clean package```

#####By Default this will run on port 3000. If you want to change the port number then change it in SimpleMessageListenerImpl


###Run the JAr
```java -jar smtp-server-1.0-SNAPSHOT.jar ```


###REST API Exposed
1) To Get list all the emails
 ```GET http://localhost:8080/email```
 
2) To Get emails for a specific email ID recipient. This will give the latest email to this recipient
```GET http://localhost:8080/email/{emailId}``` 

3) To Delete All Emails
```DELETE http://localhost:8080/email```


####You can also access H2 Database with below link
```http://localhost:8080/h2-console/```