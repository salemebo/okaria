package org.ariia.okhttp.writer;

import org.ariia.manager.Item;
import org.ariia.segment.Segment;

public class DirectChannelMetaDataWriter extends ChannelMetaDataWriter {

	public DirectChannelMetaDataWriter(Item item) {
		super(item);
	}
	
	@Override
	public synchronized void offerSegment(Segment segment) {
		if(writeSegment(segment)) {
			releaseSegment(segment);
			forceUpdate();
//			saveItem2CacheFile();
		} else {
			if(raf == null || !raf.getChannel().isOpen()) {
				initRandomAccessFile();
				initMetaData();
				offerSegment(segment);
			}
		}
	}
}