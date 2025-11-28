package se.sprinto.hakan.chatapp.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseUtil {
    private static DatabaseUtil instance;
    private final String url;
    private final String user;
    private final String password;

    private DatabaseUtil(){
        Properties props = new Properties();

        try (InputStream in = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (in == null){
                throw new RuntimeException("Could not find application.properties!");
            }

            props.load(in);


        } catch (Exception e) {
            throw new RuntimeException("Could not load application.properties!", e);
        }

        this.url = props.getProperty("db.url");
        this.user = props.getProperty("db.user");
        this.password = props.getProperty("db.password");
    }

    public static synchronized DatabaseUtil getInstance(){
        if(instance == null){
            instance = new DatabaseUtil();
        }
        return instance;
    }

    public Connection getConnection() throws Exception{
        return DriverManager.getConnection(url, user,password);
    }
}


