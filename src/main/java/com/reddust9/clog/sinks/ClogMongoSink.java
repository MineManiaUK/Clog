package com.reddust9.clog.sinks;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.reddust9.clog.ClogEntry;
import org.bson.Document;

public class ClogMongoSink implements ClogSink {
    private final MongoCollection<Document> logsCollection;

    public ClogMongoSink(String uri, String dbName) {
        MongoClient client = MongoClients.create(uri);
        MongoDatabase db = client.getDatabase(dbName);
        logsCollection = db.getCollection("clog-data");
    }

    @Override
    public void log(ClogEntry entry) {
        Document doc = new Document()
                .append("timeStamp", entry.getTimeStamp())
                .append("playerName", entry.getPlayerName())
                .append("serverName", entry.getServerName())
                .append("sourceType", entry.getSourceType())
                .append("text", entry.getText());
        logsCollection.insertOne(doc);
    }
}
