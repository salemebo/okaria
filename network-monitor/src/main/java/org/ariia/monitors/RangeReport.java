package org.ariia.monitors;

import java.util.Objects;

import org.ariia.range.RangeUtil;
import org.ariia.speed.report.SpeedMonitor;
import org.ariia.speed.report.SpeedReport;
import org.ariia.speed.report.SpeedSnapshot;
import org.ariia.util.Utils;

public class RangeReport extends SpeedReport<SpeedMonitor> implements SpeedSnapshot {
	
	protected RangeUtil info;
	protected String    name;
	protected long remainingTime;
	
	public RangeReport(RangeUtil info, String name) {
		super(new SpeedMonitor());
		this.info = Objects.requireNonNull(info);
		this.name = Objects.requireNonNull(name);
	}
	
	public String getName() {
		return name;
	}
	
	public RangeUtil getRangeUtil() {
		return info;
	}

	@Override
	public void snapshotPoint() {
		this.mointor.snapshotPoint();
	}
	
	@Override
	public void snapshotSpeed() {
		this.mointor.snapshotSpeed();
		info.oneCycleDataUpdate();
		remainingTime = (info.getRemainingLength() + 1) / (this.mointor.getTcpDownloadSpeed() + 1);
	}

	public long getRemainingTime() {
		return remainingTime;
	}
	
	public String getRemainingTimeString() {
		return Utils.timeformate(getRemainingTime());
	}

	public String getFileLength() {
		return unitLength(info.getFileLength());
	}

	public String getDownloadLength() {
		return unitLength(info.getDownloadLength());
	}

	public String getRemainingLength() {
		return unitLength(info.getRemainingLength());
	}

	public String getPercent() {
		return Utils.percent(info.getDownloadLength(), info.getFileLength());
	}
	protected float percent() {
		return (float) (info.getDownloadLength() + 1) /  (info.getFileLength() + 1);
	}

}