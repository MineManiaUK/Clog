package com.reddust9.clog;

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

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getServerName() {
        return serverName;
    }

    public ClogSourceType getSourceType() {
        return sourceType;
    }

    public String getText() {
        return text;
    }
}
