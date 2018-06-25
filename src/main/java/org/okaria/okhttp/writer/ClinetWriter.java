package org.okaria.okhttp.writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.okaria.range.RangeResponseHeader;
import org.okaria.speed.SpeedMonitor;
import org.okaria.speed.net.MonitorInputStreamWrapper;

import okhttp3.Response;

public interface ClinetWriter {
	
	int RESPONSE_BUFFER = 8192;
	
	
	default void writeResponse(Response response, RandomAccessFile destination, long[] ranges, SpeedMonitor... monitors)
			throws IOException {
		if (response.code() == 200) {
			destination.seek(0);
			if(ranges[0] != 0 ) return;
		} else if (response.code() == 206) {
			RangeResponseHeader range = new RangeResponseHeader(response.header("Content-Range"));
			destination.seek(range.start);
		}else if (response.code() == 416) {			// error state
			return;
		}
		write(response.body().byteStream(), destination, ranges, monitors);
	}
	
	default void write(InputStream source, RandomAccessFile destination, long[] ranges, SpeedMonitor... monitors)
			throws IOException {
		write(source, destination, ranges, true, monitors);
	}
	
	default void write(InputStream source, RandomAccessFile destination, long[] ranges, boolean stream, SpeedMonitor... monitors)
			throws IOException {
		source = MonitorInputStreamWrapper.wrap(source, monitors);
		write( source, destination,ranges, false);
	}
	
	void write(InputStream source, RandomAccessFile destination, long[] ranges, boolean stream) throws IOException;
	

	default void addToRange(long[] ranges, long count) {
		ranges[0] += count;
	}
}
