package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

import au.com.bytecode.opencsv.CSVReader;

public class GenerateSQLFromCSVSupport {
	public static final String CSVL_FOLDER = "import-data-csv";
	public static final String OUTPUT_SQL = "D:/git/next/src/main/resources/generate/import-data.sql";
	public static final String INSERT_TEMPLATE = "INSERT INTO {0} ({1}) VALUES ({2});";
	public static final String COMMENT_TEMPLATE = "-- table {0} rows count {1}";
	public static final String DEFAULT_FK = "SET FOREIGN_KEY_CHECKS=0;";

	public void generateSQLFromExcel() {
		System.out.println(new File("test").getAbsolutePath());
	}

	public static final File[] getSortedCSVFiles() {
		File f = new File(GenerateSQLFromCSVSupport.class.getClassLoader().getResource(CSVL_FOLDER).getPath());
		File[] csvFiles = f.listFiles();
		if (csvFiles == null) {
			return null;
		}
		Arrays.sort(csvFiles, new Comparator<File>() {
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return csvFiles;
	}

	public static final boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static final void main(String[] args) throws IOException, Exception {

		List<Integer> li1 = Lists.newArrayList(1, 2, 3);
		List<Integer> li2 = Lists.newArrayList(1, 2, 3);
		List<Integer> li = Lists.newArrayList(li1);
		li.addAll(li2);

		File[] csvFiles = getSortedCSVFiles();
		List<String> sqls = new ArrayList<String>();
		sqls.add(DEFAULT_FK);

		for (int i = 0; i < csvFiles.length; i++) {
			File csvFile = csvFiles[i];
			String fileName = csvFile.getName();
			String name = fileName.substring(3, fileName.lastIndexOf("."));
			System.out.println("find csv file " + fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "GBK"));
			CSVReader csvReader = new CSVReader(br);

			String[] fields = csvReader.readNext();
			List<String> tempFields = new ArrayList<String>(Arrays.asList(fields));
			tempFields.removeAll(Arrays.asList(""));
			fields = tempFields.toArray(new String[0]);

			String[] fieldValues = null;
			List<List<String>> rowsValues = new ArrayList<List<String>>();

			while ((fieldValues = csvReader.readNext()) != null) {
				if (fieldValues != null) {
					String cellStr = fieldValues[0];
					if (cellStr != null && cellStr.startsWith("##")) {
						rowsValues.add(Lists.asList(cellStr, new String[] {}));
						continue;
					}
				}

				List<String> values = new ArrayList<String>();

				for (int j = 0; j < fields.length; ++j) {
					String next = fieldValues[j];
					if (next.isEmpty()) {
						values.add("NULL");
						continue;
					}
					if (isNumeric(next)) {
						values.add(next);
						continue;
					}
					if (next.startsWith("'") && next.endsWith("'")) {
						values.add(next);
					} else {
						values.add("'" + StringEscapeUtils.escapeSql(next.replace("\n", "")) + "'");
					}
				}
				if (StringUtils.isBlank(StringUtils.join(values, ", ").trim()) || values.size() < fields.length) {
					continue;
				}
				rowsValues.add(values);
			}
			sqls.add(MessageFormat.format(COMMENT_TEMPLATE, name, String.valueOf(rowsValues.size())));
			for (List<String> values : rowsValues) {
				if (values.size() == 1 && values.get(0).startsWith("##")) {
					sqls.add("--" + values.get(0));
				} else {
					sqls.add(MessageFormat.format(INSERT_TEMPLATE, name, StringUtils.join(fields, ", "),
							StringUtils.join(values, ", ")));
				}
			}
			sqls.add("");
			csvReader.close();
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
