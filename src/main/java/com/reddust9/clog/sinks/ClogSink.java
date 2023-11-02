package com.reddust9.clog.sinks;

import com.reddust9.clog.ClogEntry;

public interface ClogSink {
    void log(ClogEntry entry);
}
