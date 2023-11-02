package com.reddust9.clog.sinks;

import com.reddust9.clog.ClogEntry;

import java.util.ArrayList;

public class ClogJsonFile {
    private final ArrayList<ClogEntry> entries;

    public ClogJsonFile() {
        this.entries = new ArrayList<>();
    }

    public void addEntry(ClogEntry entry) {
        this.entries.add(entry);
    }
}
