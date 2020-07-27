package com.xebia.happix.CSVUtil;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.xebia.happix.dto.EmployeeMoodResponse;
import com.xebia.happix.model.Constants;
import com.xebia.happix.model.EmployeeMood;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSVDownloader {

	public static void beanToCSV(List<EmployeeMoodResponse> empMoodList, String csvFileLocation,
			final HttpServletResponse response) throws Exception {
		response.setContentType("text/csv");
		String downloadFileName = (csvFileLocation != null ? csvFileLocation : "DownloadFile") + "." + "csv";
		response.setHeader(Constants.HEADER_CONTENT_DISPOSITION, 
				Constants.HEADER_FILE_NAME + downloadFileName);
		PrintWriter writer = response.getWriter();

		ColumnPositionMappingStrategy<EmployeeMoodResponse> mappingStrategy = new ColumnPositionMappingStrategy<EmployeeMoodResponse>();
		mappingStrategy.setType(EmployeeMoodResponse.class);

		StatefulBeanToCsvBuilder<EmployeeMoodResponse> builder = new StatefulBeanToCsvBuilder<EmployeeMoodResponse>(
				response.getWriter());
		StatefulBeanToCsv<EmployeeMoodResponse> beanWriter = builder.withMappingStrategy(mappingStrategy).build();

		log.info("Total Count to write in CSV : " + empMoodList.size());
		try {
			writer.append(buildHeader(EmployeeMood.class));
			beanWriter.write(empMoodList);
		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			log.error("Error to write bean into CSV");
			throw new Exception("Error to write bean into CSV");
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private static String buildHeader(Class<EmployeeMood> clazz) {
		return Arrays.stream(clazz.getDeclaredFields())
				.filter(f -> f.getAnnotation(CsvBindByPosition.class) != null
						&& f.getAnnotation(CsvBindByName.class) != null)
				.sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
				.map(f -> f.getAnnotation(CsvBindByName.class).column())
				.collect(Collectors.joining(Constants.CSV_SEPERATOR)) + "\n";
	}
}
