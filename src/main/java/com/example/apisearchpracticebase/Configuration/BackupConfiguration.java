package com.example.apisearchpracticebase.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.smattme.MysqlExportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class BackupConfiguration {
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.database.name}")
    String databaseName;

    private final Storage storage;

    public BackupConfiguration() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("C:\\Users\\gusdi\\IdeaProjects\\ApiSearchPracticeBase\\src\\main\\resources\\searchpracticebaseproject-firebase-adminsdk-60xwx-7514dc3fb5.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();
        storage = options.getService();
    }

    @Scheduled(fixedDelay = 1500*60*60*4)
    public void createBackUpDataBase(){
        try {
            Properties properties = new Properties();
            properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, url);
            properties.setProperty(MysqlExportService.DB_NAME, databaseName);
            properties.setProperty(MysqlExportService.DB_USERNAME, username);
            properties.setProperty(MysqlExportService.DB_PASSWORD, password);
            properties.setProperty(MysqlExportService.TEMP_DIR, new File("external").getPath());

            MysqlExportService mysqlExportService = new MysqlExportService(properties);
            mysqlExportService.export();

            BlobId blobId = BlobId.of("searchpracticebaseproject.appspot.com", "backups/" + mysqlExportService.getSqlFileName());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, mysqlExportService.getGeneratedSql().getBytes());
            mysqlExportService.clearTempFiles();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}