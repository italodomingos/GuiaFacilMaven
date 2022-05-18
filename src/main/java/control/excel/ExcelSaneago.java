package control.excel;

import control.tools.Tools;
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

public class ExcelSaneago {

    public List<Empresa> getCompaniesSaneago(JTextArea jta, String filePath) throws FileNotFoundException, IOException {
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workBook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workBook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.iterator();
            //Empresas[] empresas = new Empresas[sheet.getLastRowNum()];
            List<Empresa> empresas = new ArrayList<Empresa>();
            //int i = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Empresa empresa = new Empresa();
                if (row.getRowNum() != 0) {
                    Iterator<Cell> cellIterator = row.iterator();

                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (cell != null && cell.getCellType() != CellType.BLANK) {
                            switch (cell.getColumnIndex()) {
                                case 0:
                                    try {
                                    empresa.setContaDv((new BigDecimal(cell.getNumericCellValue())).toPlainString());

                                } catch (Exception e) {
                                    empresa.setContaDv((new BigDecimal(cell.getStringCellValue())).toPlainString());

                                }
                                case 1:
                                    try {

                                    empresa.setCnpj(Tools.refactorCpfCnpj((new BigDecimal(cell.getNumericCellValue())).toPlainString()));

                                } catch (Exception e) {
                                    empresa.setCnpj(Tools.refactorCpfCnpj((new BigDecimal(cell.getStringCellValue())).toPlainString()));

                                }

                            }

                        }
                    }

                    //i++;
                    if(empresa.getCnpj() != null){
                        empresas.add(empresa);
                    }
                    

                }
            }
            fis.close();
            return empresas;
        } catch (Exception ex) {
            (new Logs(jta)).setLog(ex);
            return null;
        }
    }
}
