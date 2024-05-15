package com.example.apisearchpracticebase.Controllers;

import com.example.apisearchpracticebase.Models.WorkApiLogs;
import com.example.apisearchpracticebase.Repositories.WorkApiLogsRepos;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ch.qos.logback.classic.util.StatusViaSLF4JLoggerFactory.addError;

@RestController
@RequestMapping("/firebase_files")
public class AdminController {
    private String bucketName = "searchpracticebaseproject.appspot.com";
    private Storage storage;

    @Autowired
    WorkApiLogsRepos workApiLogsRepos;

    @GetMapping("/getLogs")
    public List<String> getAllLogs(){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);

        List<String> logFiles = new ArrayList<>();
        try {
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\gusdi\\IdeaProjects\\ApiSearchPracticeBase\\src\\main\\resources\\searchpracticebaseproject-firebase-adminsdk-60xwx-7514dc3fb5.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();
            storage = options.getService();
            try {
                Bucket bucket = storage.get(bucketName);

                Iterable<Blob> blobs = bucket.list(Storage.BlobListOption.prefix("logs/archive/")).iterateAll();
                for (Blob blob : blobs) {
                    logFiles.add(blob.getName().split("logs/archive/")[1]);
                }
            } catch (Exception e) {
                workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
                workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
                workApiLogsRepos.save(workApiLogs);
                e.printStackTrace();
            }
        } catch (IOException e) {
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
            workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            addError("Failed to initialize Firebase Cloud Storage", e);
        }
        return logFiles;
    }

    @GetMapping("/getBackUps")
    public List<String> getAllBackupFiles(){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);
        List<String> backupFiles = new ArrayList<>();
        try {
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\gusdi\\IdeaProjects\\ApiSearchPracticeBase\\src\\main\\resources\\searchpracticebaseproject-firebase-adminsdk-60xwx-7514dc3fb5.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();
            storage = options.getService();
            try {
                Bucket bucket = storage.get(bucketName);

                Iterable<Blob> blobs = bucket.list(Storage.BlobListOption.prefix("backups/")).iterateAll();
                for (Blob blob : blobs) {
                    backupFiles.add(blob.getName().split("backups/")[1]);
                }
            } catch (Exception e) {
                workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
                workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
                workApiLogsRepos.save(workApiLogs);
                e.printStackTrace();
            }
        } catch (IOException e) {
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
            workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            addError("Failed to initialize Firebase Cloud Storage", e);
        }

        return backupFiles;
    }

    @PostMapping("/download-file")
    public byte[] getFile(@RequestBody Map<String, Object> map){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        boolean isLogFile = (boolean) map.get("isLogFile");
        String nameFile = (String) map.get("nameFile");
        try {
            FileInputStream serviceAccount = new FileInputStream("C:\\Users\\gusdi\\IdeaProjects\\ApiSearchPracticeBase\\src\\main\\resources\\searchpracticebaseproject-firebase-adminsdk-60xwx-7514dc3fb5.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();
            storage = options.getService();
            try {
                Bucket bucket = storage.get(bucketName);

                Iterable<Blob> blobs = bucket.list(Storage.BlobListOption.prefix(isLogFile ? "logs/archive/" : "backups/")).iterateAll();
                for (Blob blob : blobs) {
                    if(blob.getName().split(isLogFile ? "logs/archive/" : "backups/")[1].equals(nameFile)){
                        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
                        workApiLogsRepos.save(workApiLogs);
                        return blob.getContent();
                    }
                }
            } catch (Exception e) {
                workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
                workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
                workApiLogsRepos.save(workApiLogs);
                e.printStackTrace();
            }
        } catch (IOException e) {
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
            workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            addError("Failed to initialize Firebase Cloud Storage", e);
        }
        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
        workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);
        return null;
    }
}
