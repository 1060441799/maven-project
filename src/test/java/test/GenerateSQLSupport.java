package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Lists;

public class GenerateSQLSupport {
	public static final String EXCEL_FILE = "sql/import-data.xlsx";
	public static final String OUTPUT_SQL = "sql/generate/import-data.sql";
	public static final String INSERT_TEMPLATE = "INSERT INTO {0} ({1}) VALUES ({2});";
	public static final String COMMENT_TEMPLATE = "-- table {0} rows count {1}";

	public void generateSQLFromExcel() {
		System.out.println(new File("test").getAbsolutePath());
	}

	public static final void main(String[] args) throws IOException, Exception {
		System.out.println("Opening " + new File(EXCEL_FILE).getAbsolutePath());
		XSSFWorkbook book = new XSSFWorkbook(new FileInputStream(new File(EXCEL_FILE)));
		int sheetNumber = book.getNumberOfSheets();
		Date curDate = new Date();
		List<String> sqls = new ArrayList<String>();
		for (int i = 0; i < sheetNumber; i++) {
			XSSFSheet sheet = book.getSheetAt(i);
			String name = sheet.getSheetName();

			if (name.startsWith("Sheet")) {
				System.out.println("ignore sheet " + name);
				continue;
			}

			System.out.println("find sheet " + name);

			int rownum = 0;
			XSSFRow headerRow = sheet.getRow(rownum++);
			List<String> fields = new ArrayList<String>();
			Iterator<Cell> cellIter = headerRow.cellIterator();
			while (cellIter.hasNext()) {
				Cell next = cellIter.next();
				fields.add(next.getStringCellValue());
			}

			XSSFRow row = null;
			List<List<String>> rowsValues = Lists.newArrayList();
			while ((row = sheet.getRow(rownum++)) != null) {
				List<String> values = Lists.newArrayList();
				if (row.getCell(0) != null && row.getCell(0).getCellType() == XSSFCell.CELL_TYPE_STRING) {
					String cellStr = row.getCell(0).getStringCellValue();
					if (cellStr != null && cellStr.startsWith("##")) {
						rowsValues.add(Lists.asList(cellStr, new String[] {}));
						continue;
					}
				}
				for (int j = 0; j < fields.size(); j++) {
					Cell next = row.getCell(j);
					if (next == null) {
						values.add("NULL");
					} else {
						switch (next.getCellType()) {
						case XSSFCell.CELL_TYPE_STRING:
						case XSSFCell.CELL_TYPE_ERROR:
						case XSSFCell.CELL_TYPE_FORMULA:
							values.add("'" + StringEscapeUtils.escapeSql(next.getStringCellValue().replace("\n", ""))
									+ "'");
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							values.add(next.getBooleanCellValue() ? "TRUE" : "FALSE");
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							values.add(String.valueOf(next.getNumericCellValue()));
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							values.add("NULL");
							break;
						default:
							values.add("'" + StringEscapeUtils.escapeSql(next.getStringCellValue().replace("\n", ""))
									+ "'");
							break;
						}
					}
				}
				if (StringUtils.isBlank(StringUtils.join(values, ", ").trim()) || values.size() < fields.size()) {
					continue;
				}
				rowsValues.add(values);
			}
			sqls.add(MessageFormat.format(COMMENT_TEMPLATE, name, String.valueOf(rowsValues.size()),
					curDate.toString()));
			for (List<String> values : rowsValues) {
				if (values.size() == 1 && values.get(0).startsWith("##")) {
					sqls.add("--" + values.get(0));
				} else {
					sqls.add(MessageFormat.format(INSERT_TEMPLATE, name, StringUtils.join(fields, ", "),
							StringUtils.join(values, ", ")));
				}
			}
			sqls.add("");
		}
		File sqlFile = new File(OUTPUT_SQL);
		if (sqlFile.exists() && !sqlFile.canWrite()) {
			sqlFile.delete();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_SQL));
		writer.write(StringUtils.join(sqls, "\r\n"));
		writer.close();
		System.out.println("Finished." + new File(OUTPUT_SQL).getAbsolutePath());
	}
}
