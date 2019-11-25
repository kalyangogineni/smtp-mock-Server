package com.walmart.smtp.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public class Repository {
    private static Logger logger = LoggerFactory.getLogger(Repository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void saveEmailData(String email, String data){

        String query = String.format("INSERT INTO email(email, data, date) " +
                "VALUES ('%s', '%s','%s')", email, data, new Date());
        logger.debug("insert query is {}",query);
        jdbcTemplate.execute(query);

    }

    public String getEmailData(String email){
        String query = String.format("Select * from email where email='%s' ORDER BY date desc",email);
        return jdbcTemplate.queryForList(query).get(0).get("data").toString();
    }

    public List<Map<String,Object>> getAllEmailData(){
        return jdbcTemplate.queryForList("Select * FROM email ORDER BY date DESC");
    }

    public void deleteEmailData(String email){
        jdbcTemplate.execute("Delete from email where email='"+email+"'");
    }

    public void deleteAllEmail(){
        jdbcTemplate.execute("Delete from email");
    }

    public void runSql(String query){
        jdbcTemplate.execute(query);
    }


}
