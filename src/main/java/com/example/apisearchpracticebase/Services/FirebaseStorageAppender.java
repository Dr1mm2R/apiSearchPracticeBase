package com.example.apisearchpracticebase.Services;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;

public class FirebaseStorageAppender extends AppenderBase<ILoggingEvent> {
    private String bucketName = "searchpracticebaseproject.appspot.com";
    private Storage storage;
    private long maxFileSize = 10 * 1024 * 1024;

    @Override
    public void start() {
        super.start();
        try {
            FileInputStream serviceAccount = new FileInputStream("/app/searchpracticebaseproject-firebase-adminsdk-60xwx-7514dc3fb5.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();
            storage = options.getService();
        } catch (IOException e) {
            addError("Failed to initialize Firebase Cloud Storage", e);
        }
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if (storage != null) {
            String logMessage = convertEventToString(eventObject);
            try {
                File fileFromServer = getCurrentFile();
                double lengthFile = fileFromServer.length();
                if (lengthFile + logMessage.getBytes(StandardCharsets.UTF_8).length > maxFileSize) {
                    fileFromServer = archiveLogFile(fileFromServer);
                }

                BlobId blobId = BlobId.of(bucketName, "logs/" + fileFromServer.getName());
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
                FileWriter fw = new FileWriter(fileFromServer, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(logMessage);
                bw.close();
                fw.close();
                storage.create(blobInfo, new FileInputStream(fileFromServer));
                fileFromServer.delete();
            } catch (Exception e) {
                addError("Failed to append log to Firebase Cloud Storage", e);
            }
        } else {
            addError("Firebase Cloud Storage is not initialized");
        }
    }

    public String convertEventToString(ILoggingEvent eventObject) {
        LoggerContext context = new LoggerContext();
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{35} - %msg%n");
        encoder.setContext(context);
        encoder.start();

        MessageConverter messageConverter = new MessageConverter();
        messageConverter.start();

        ThrowableProxyConverter throwableProxyConverter = new ThrowableProxyConverter();
        throwableProxyConverter.start();

        LayoutWrappingEncoder<ILoggingEvent> layoutWrappingEncoder = new LayoutWrappingEncoder<>();
        layoutWrappingEncoder.setLayout(encoder.getLayout());
        layoutWrappingEncoder.start();

        return new String(layoutWrappingEncoder.encode(eventObject));
    }

    private File getCurrentFile() throws IOException{
        File outputFile = new File("spring-boot-logger.log");
        Blob blob = storage.get(bucketName, "logs/spring-boot-logger.log");
        byte[] content = blob.getContent();
        FileOutputStream fos = new FileOutputStream(outputFile);
        fos.write(content);
        fos.close();
        return outputFile;
    }

    private File archiveLogFile(File fileFromServer) throws Exception{
        BlobId blobId = BlobId.of(bucketName, "logs/archive/" + LocalDate.now() + " - " + LocalTime.now() + fileFromServer.getName());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, new FileInputStream(fileFromServer));
        File clearFile = new File("spring-boot-logger.log");
        FileWriter fw = new FileWriter(clearFile);
        fw.write("");
        fw.close();
        return clearFile;
    }
}
