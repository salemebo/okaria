package org.okaria.mointors;

import java.util.List;

import org.terminal.beans.ColumnStyle;
import org.terminal.beans.Row;

public class Table extends org.terminal.beans.Table<String> {

	public Table() {
		super(7);
		head("File Name", "File Length", "TDown", "Remain", "Down", "speed", "%");
	}

	
	@Override
	protected void callColWidth() {

		if (head.isEmpty() & rows.isEmpty()) {
			for (int i = 0; i < columnCount; i++) {
				columnWidths.add(10);
			}
			return;
		} else if (!head.isEmpty() & rows.isEmpty()) {
			columnWidths.add(22);
			for (int i = 1; i < head.size(); i++) {
				columnWidths.add(head.get(i).toString().length() + 2);
			}
			return;
		} else if (head.isEmpty() & !rows.isEmpty()) {
			Integer[] width = new Integer[rows.get(0).size()];
			for (int i = 0; i < rows.get(0).size(); i++) {
				width[i] = rows.get(0).get(i).toString().length() + 2;
			}
			for (int col = 0; col < columnCount; col++) {
				for (int i = 1; i < rows.size(); i++) {
					int wi = rows.get(i).get(col).toString().length() + 2;
					width[col] = width[col] > wi ? width[col] : wi;
				}
				columnWidths.add(width[col]);
			}
			return;
		} else {
			Integer[] width = new Integer[head.size()];
			columnWidths.add(22);
			for (int i = 1; i < head.size(); i++) {
				width[i] = head.get(i).toString().length() + 2;
			}
			for (int col = 1; col < head.size(); col++) {
				for (int i = 0; i < rows.size(); i++) {
					int wi = rows.get(i).get(col).toString().length() + 2;
					width[col] = width[col] > wi ? width[col] : wi;
				}
				columnWidths.add(width[col]);
			}
			return;
		}

	}
	
	
	protected void printLine(StringBuilder builder, Row<String> row, List<ColumnStyle> styles) {
		if (tableTheme.left() != 0)
			builder.append(borderStyle.build(tableTheme.left() + ""));
		for (int i = 0; i < columnCount; i++) {
			String print = row.get(i).toString();
			int length = (columnWidths.get(i) - print.length());
			if(length < 0) {
				print = print.substring(0, 18) + "..";
				length = 1;
			}else{
				length /= 2;
			}
			int j = 0;
			for (; j < length; j++) {
				builder.append(' ');
			}
			j += print.length();
			print = styles.get(i).build(print);
			builder.append(print);
			for (; j < columnWidths.get(i); j++) {
				builder.append(' ');
			}
			if (i < columnWidths.size() - 1) {
				if (tableTheme.middle() != 0)
					builder.append(borderStyle.build(tableTheme.middle() + ""));
			}

			else {
				if (tableTheme.right() != 0)
					builder.append(borderStyle.build(tableTheme.right() + ""));
			}
		}
		builder.append('\n');
	}

}