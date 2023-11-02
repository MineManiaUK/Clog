package com.reddust9.clog;

// Again, IntelliJ thinks they're unused, but they need to be deserialized :3
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class ClogEntry {
    private final long timeStamp;
    private final String playerName;
    private final String serverName;
    private final ClogSourceType sourceType;
    private final String text;

    public ClogEntry(long timeStamp, String playerName, String serverName, ClogSourceType sourceType, String text) {
        this.timeStamp = timeStamp;
        this.playerName = playerName;
        this.serverName = serverName;
        this.sourceType = sourceType;
        this.text = text;
    }
}
