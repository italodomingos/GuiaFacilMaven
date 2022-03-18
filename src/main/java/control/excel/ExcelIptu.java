package control.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTextArea;
import logs.Logs;
import model.Empresa;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelIptu {

    public List<Empresa> getCompaniesIptu(JTextArea jta, File arquivoExcel) throws FileNotFoundException, IOException {
        try {
            FileInputStream fis = new FileInputStream(arquivoExcel);
            XSSFWorkbook workBook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workBook.getSheetAt(2);
            Iterator<Row> rowIterator = sheet.iterator();
            List<Empresa> empresas = new ArrayList<Empresa>(); 
            //Empresas[] empresas = new Empresas[sheet.getLastRowNum()];
            
            //int i = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() != 0) {
                    Iterator<Cell> cellIterator = row.iterator();
                    //empresas[i] = new Empresas();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();

                        if (cell != null && cell.getCellType() != CellType.BLANK && cell.getNumericCellValue() != 0) {
                            switch (cell.getColumnIndex()) {
                                case 0:
                                    Empresa empresa = new Empresa();
                                    empresa.setIncricaoCadastral((new BigDecimal(cell.getNumericCellValue())).toPlainString());
                                    empresas.add(empresa);
                                    //empresas[i].setIncricaoCadastral((new BigDecimal(cell.getNumericCellValue())).toPlainString());
                                    //i++;
                            }
                        }

                    }
                }
            }
            System.out.println("Quantidade de linhas na tabela: " + empresas.size());
            fis.close();
            return empresas;
            
        } catch (Exception ex) {
            (new Logs(jta)).setLog(ex);
            return null;
        }
    }
}
