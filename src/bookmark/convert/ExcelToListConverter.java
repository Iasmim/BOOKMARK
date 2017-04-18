package bookmark.convert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//"dd.MM.yy"
public class ExcelToListConverter {

	private ExcelConverterConfig config = null;
	ArrayList<String> fields = new ArrayList<String>();

	public ExcelToListConverter(ExcelConverterConfig config) {
		this.config = config;
	}

	public static ArrayList<ItemResult> convert(
			ExcelConverterConfig config) throws InvalidFormatException,
			IOException {

		ArrayList<ItemResult> result = new ArrayList<ExcelToListConverter.ItemResult>();

		ArrayList<Item> model = new ArrayList<ExcelToListConverter.Item>();
		ArrayList<Item> customer = new ArrayList<ExcelToListConverter.Item>();

		model = new ExcelToListConverter(config).convert(true);
		customer = new ExcelToListConverter(config).convert(false);

		int count = 0;
		if (model.size() < customer.size())
			count = model.size();
		else
			count = customer.size();

		for (int i = 0; i < count; i++) {

			ItemResult r = new ItemResult();

			Item c = i < customer.size() ? customer.get(i) : null;
			Item m = i < model.size() ? model.get(i) : null;

			if (c == null) {
				r.Main_Category = null;
				r.Sub_Category = null;
				r.Field = null;
				r.Value = null;
			} else {
				r.Main_Category = c.Main_Category;
				r.Sub_Category = c.Sub_Category;
				r.Field = c.Field;
				r.Value = c.Value;
			}

			if (m == null) {
				r.Main_Category_Model = null;
				r.Sub_Category_Model = null;
				r.Field_Model = null;
				r.Value_Model = null;
			} else {
				r.Main_Category_Model = m.Main_Category;
				r.Sub_Category_Model = m.Sub_Category;
				r.Field_Model = m.Field;
				r.Value_Model = m.Value;
			}

			if (m.Value.equals(c.Value))
				r.Color = 0;
			else
				r.Color = 1;

			result.add(r);

		}

		if (model.size() > count) {
			for (int i = count; i < model.size() ; i++) {
				Item m = model.get(i);
				ItemResult r = new ItemResult();

				r.Main_Category = null;
				r.Sub_Category = null;
				r.Field = null;
				r.Value = null;

				r.Main_Category_Model = m.Main_Category;
				r.Sub_Category_Model = m.Sub_Category;
				r.Field_Model = m.Field;
				r.Value_Model = m.Value;

			}

		}
		
		if (customer.size() > count) {
			for (int i = count; i < customer.size(); i++) {
				Item c = customer.get(i);
				ItemResult r = new ItemResult();

				r.Main_Category_Model = null;
				r.Sub_Category_Model = null;
				r.Field_Model = null;
				r.Value_Model = null;

				r.Main_Category = c.Main_Category;
				r.Sub_Category = c.Sub_Category;
				r.Field = c.Field;
				r.Value = c.Value;

			}

		}
		
		return result;
	}

	public ArrayList<Item> convert(Boolean model)
			throws InvalidFormatException, IOException {

		fields.add("Network Name");
		fields.add("Editable");
		fields.add("Name");
		fields.add("URL");

		InputStream inp = new FileInputStream(config.getSourceFile(model));
		Workbook wb = WorkbookFactory.create(inp);
		ArrayList<Item> rowData = new ArrayList<Item>();

		int loopLimit = wb.getNumberOfSheets();
		if (config.getNumberOfSheets() > 0
				&& loopLimit > config.getNumberOfSheets()) {
			loopLimit = config.getNumberOfSheets();
		}

		int id = 0;
		for (int i = 0; i < loopLimit; i++) {
			Sheet sheet = wb.getSheetAt(i);
			if (sheet == null) {
				continue;
			}

			if (sheet.getSheetName().equals("Browser")) {

				for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
					Row row = sheet.getRow(j);
					if (row == null) {
						continue;
					}
					boolean hasValues = false;
					Item item = new Item();
					for (int k = 0; k <= row.getLastCellNum(); k++) {

						Cell cell = row.getCell(k);
						if (cell != null) {
							Object value = cellToObject(cell);
							hasValues = hasValues || value != null;
							switch (k) {
							case 0:
								item.Main_Category = (String) value;
								break;
							case 1:
								item.Sub_Category = (String) value;
								break;
							case 2:
								item.Field = (String) value;
								break;
							case 4:
								item.Value = (String) value;
								break;
							default:
								break;
							}

						}
					}
					id++;
					item.Id = id;
					if (fields.contains(item.Field))
						rowData.add(item);
				}

			}
		}

		return rowData;
	}

	private Object cellToObject(Cell cell) {

		int type = cell.getCellType();

		if (type == Cell.CELL_TYPE_STRING) {
			return cleanString(cell.getStringCellValue());
		}

		if (type == Cell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		}

		if (type == Cell.CELL_TYPE_NUMERIC) {

			if (cell.getCellStyle().getDataFormatString().contains("%")) {
				return cell.getNumericCellValue() * 100;
			}

			return numeric(cell);
		}

		return null;

	}

	private String cleanString(String str) {
		return str.replace("\n", "").replace("\r", "");
	}

	private Object numeric(Cell cell) {
		if (HSSFDateUtil.isCellDateFormatted(cell)) {
			if (config.getFormatDate() != null) {
				return config.getFormatDate().format(cell.getDateCellValue());
			}
			return cell.getDateCellValue();
		}
		return Double.valueOf(cell.getNumericCellValue());
	}

	public class Item {
		public int Id;
		public String Main_Category;
		public String Sub_Category;
		public String Field;
		public String Value;

		public int getId() {
			return Id;
		}

		public void setId(int id) {
			Id = id;
		}

		public String getMain_Category() {
			return Main_Category;
		}

		public void setMain_Category(String main_Category) {
			Main_Category = main_Category;
		}

		public String getSub_Category() {
			return Sub_Category;
		}

		public void setSub_Category(String sub_Category) {
			Sub_Category = sub_Category;
		}

		public String getField() {
			return Field;
		}

		public void setField(String field) {
			Field = field;
		}

		public String getValue() {
			return Value;
		}

		public void setValue(String value) {
			Value = value;
		}

	}

	public static class ItemResult {

		public int Color;
		public String Main_Category;
		public String Sub_Category;
		public String Field;
		public String Value;

		public String Main_Category_Model;
		public String Sub_Category_Model;
		public String Field_Model;
		public String Value_Model;

		public int getColor() {
			return Color;
		}

		public void setColor(int color) {
			Color = color;
		}

		public String getMain_Category() {
			return Main_Category;
		}

		public void setMain_Category(String main_Category) {
			Main_Category = main_Category;
		}

		public String getSub_Category() {
			return Sub_Category;
		}

		public void setSub_Category(String sub_Category) {
			Sub_Category = sub_Category;
		}

		public String getField() {
			return Field;
		}

		public void setField(String field) {
			Field = field;
		}

		public String getValue() {
			return Value;
		}

		public void setValue(String value) {
			Value = value;
		}

		public String getMain_Category_Model() {
			return Main_Category_Model;
		}

		public void setMain_Category_Model(String main_Category_Model) {
			Main_Category_Model = main_Category_Model;
		}

		public String getSub_Category_Model() {
			return Sub_Category_Model;
		}

		public void setSub_Category_Model(String sub_Category_Model) {
			Sub_Category_Model = sub_Category_Model;
		}

		public String getField_Model() {
			return Field_Model;
		}

		public void setField_Model(String field_Model) {
			Field_Model = field_Model;
		}

		public String getValue_Model() {
			return Value_Model;
		}

		public void setValue_Model(String value_Model) {
			Value_Model = value_Model;
		}

	}

}
