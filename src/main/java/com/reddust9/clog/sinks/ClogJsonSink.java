package com.reddust9.clog.sinks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reddust9.clog.Clog;
import com.reddust9.clog.ClogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClogJsonSink implements ClogSink {
    private static final Logger logger = LoggerFactory.getLogger(Clog.class);
    private final Path filePath;
    private final Gson gson;

    public ClogJsonSink(Path dataFolder) {
        // Create json serializer
        this.gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create();

        // Create JSON file with date/time in the filename
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy-HH-mm-ss");

        Path logFolder = dataFolder.resolve("json-logs");
        logFolder.toFile().mkdir();
        this.filePath = logFolder.resolve("clog-" + formatter.format(dt) + ".json");

        try {
            this.filePath.toFile().createNewFile();
            Files.writeString(this.filePath, gson.toJson(new ClogJsonFile()));
        } catch (IOException e) {
            logger.error("Could not create JSON log file!", e);
        }
    }

    @Override
    public void log(ClogEntry entry) {
        try {
            String originalJson = Files.readString(filePath);
            ClogJsonFile file = gson.fromJson(originalJson, ClogJsonFile.class);
            file.addEntry(entry);
            String newJson = gson.toJson(file);
            Files.writeString(filePath, newJson);
        } catch (IOException e) {
            logger.error("Could not write entry to file!", e);
        }
    }
}
