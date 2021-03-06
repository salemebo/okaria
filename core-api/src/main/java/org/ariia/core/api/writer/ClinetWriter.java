package org.ariia.core.api.writer;


import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.network.speed.net.MonitorInputStreamWrapper;
import org.network.speed.report.SpeedMonitor;

public interface ClinetWriter {

    int RESPONSE_BUFFER = 2048;

    void write(InputStream source, RandomAccessFile destination, long[] ranges) throws IOException;

    default void writeRsponse(InputStream source, RandomAccessFile destination, long[] ranges, SpeedMonitor... monitors)
            throws IOException {
        source = MonitorInputStreamWrapper.wrap(source, monitors);
        write(source, destination, ranges);
    }


    default void addToRange(long[] ranges, long count) {
        ranges[0] += count;
    }
}
