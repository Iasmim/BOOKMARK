package bookmark.convert;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class ExcelConverterConfig {

	public static String sourceFile = null;
	public static String sourceFileModel = null;
	
	private boolean parsePercentAsFloats = false;
	private boolean omitEmpty = false;
	private boolean pretty = false;
	private boolean fillColumns = false;
	private int numberOfSheets = 0;
	private int rowLimit = 0; // 0 -> no limit
	private int rowOffset = 0;
	private DateFormat formatDate = null;



	
	public String valid() {
		if(sourceFile==null) {
			return "Source file may not be empty.";
		}
		File file = new File(sourceFile);
		if(!file.exists()) {
			return "Source file does not exist.";
		}
		if(!file.canRead()) {
			return "Source file is not readable.";
		}

		return null;
	}
	
	public String validModel() {
		if(sourceFileModel==null) {
			return "Source file may not be empty.";
		}
		File file = new File(sourceFileModel);
		if(!file.exists()) {
			return "Source file does not exist.";
		}
		if(!file.canRead()) {
			return "Source file is not readable.";
		}

		return null;
	}
	
	// GET/SET

	public int getRowLimit() {
		return rowLimit;
	}

	public void setRowLimit(int rowLimit) {
		this.rowLimit = rowLimit;
	}

	public int getRowOffset() {
		return rowOffset;
	}

	public void setRowOffset(int rowOffset) {
		this.rowOffset = rowOffset;
	}

	public int getNumberOfSheets()
	{
		return numberOfSheets;
	}
	public void setNumberOfSheets(int numberOfSheets)
	{
		this.numberOfSheets = numberOfSheets;
	}
	public String getSourceFile(Boolean model) {
		if(model)
		  return sourceFileModel;
		else
			return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public boolean isParsePercentAsFloats() {
		return parsePercentAsFloats;
	}

	public void setParsePercentAsFloats(boolean parsePercentAsFloats) {
		this.parsePercentAsFloats = parsePercentAsFloats;
	}

	public boolean isOmitEmpty() {
		return omitEmpty;
	}

	public void setOmitEmpty(boolean omitEmpty) {
		this.omitEmpty = omitEmpty;
	}

	public DateFormat getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(DateFormat formatDate) {
		this.formatDate = formatDate;
	}

	public boolean isPretty() {
		return pretty;
	}

	public void setPretty(boolean pretty) {
		this.pretty = pretty;
	}

	public boolean isFillColumns() {
		return fillColumns;
	}

	public void setFillColumns(boolean fillColumns) {
		this.fillColumns = fillColumns;
	}
}
