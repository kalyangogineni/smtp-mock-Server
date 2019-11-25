package com.walmart.smtp.service;


import com.walmart.smtp.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class FileReadService {
    private static Logger logger = LoggerFactory.getLogger(FileReadService.class);

    @Autowired
    Repository repository;

    @Bean
    public void getFiles(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try{
                    logger.info("Running the timer task");
                    Path filePath = Paths.get("src/main/resources/data/emailData.sql");
                    File oldFile= new File(filePath.toUri());
                    if(oldFile.exists()) {
                        File newFile = new File(Paths.get("src/main/resources/data/reading.sql").toUri());
                        if(newFile.exists()){
                            newFile.delete();
                        }
                        if (oldFile.renameTo(newFile)) {
                            System.out.println("Rename successful");
                        } else {
                            System.out.println("Rename failed");
                        }
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(newFile));
                        String data;
                        if ((data = bufferedReader.readLine()) != null) {
                            repository.runSql(data);
                        }
                        while ((data = bufferedReader.readLine()) != null) {
                            repository.runSql(data);
                        }
                    }

                }catch (Exception e){
                    logger.error("File not found or failed to update database ");
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 5000L;
        long period = 5000L;
        timer.scheduleAtFixedRate(timerTask,delay,period);
    }



}
