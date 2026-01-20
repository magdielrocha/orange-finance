package br.mag.dev.orange_finance.infra;

import br.mag.dev.orange_finance.domain.dto.report.IncomeExcelRowDto;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class IncomeExcelExporter {

    public byte[] export(List<IncomeExcelRowDto> data) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Income");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Source");
            header.createCell(1).setCellValue("Total");

            int rowIdx = 1;
            for (IncomeExcelRowDto dto : data) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.source());
                row.createCell(1).setCellValue(dto.total().doubleValue());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar o arquivo Excel", e);
        }
    }
}
