package control.comands;

import control.excel.ExcelEnel;
import control.tools.Tools;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import logs.Logs;
import model.Empresa;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EnelComands {

    private final ExcelEnel em = new ExcelEnel();

    private final Tools tools = new Tools();

    private WebDriver wd;

    public void faturaEnel(File baseDadosExcel, JTextArea jta) throws IOException {
        Logs log = new Logs(jta);
        int rowCount = 1;
        String caminhoExcel = baseDadosExcel.getAbsoluteFile().getParent() + "/Relatorio Enel.xlsx";
        try ( FileOutputStream outputStream = new FileOutputStream(caminhoExcel)) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = inicializeWorkbook(workbook);
            log.beginLog();
            List<Empresa> empresas = this.em.getCompaniesEnel(jta, baseDadosExcel.getAbsolutePath());
            this.wd = Tools.setPrefs();
            //this.wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            try {
                for (int i = 0; i < empresas.size(); i++) {
                    this.wd.get("https://www.eneldistribuicao.com.br/go/LoginAcessoRapidoPagamento.aspx");
                    this.wd.manage().window().maximize();
                    this.wd.findElement(By.id("CONTENT_Formulario_NumeroCliente")).sendKeys(empresas.get(i).getUnidadeConsumidora());
                    this.wd.findElement(By.id("CONTENT_Formulario_Documento")).sendKeys(empresas.get(i).getCnpj());
                    this.wd.findElement(By.id("CONTENT_Formulario_Acessar")).click();
                    Thread.sleep(1000);
                    try {
                        WebDriverWait wait = new WebDriverWait(this.wd, Duration.ofSeconds(10));
                        try {

                            wait.until((Function) ExpectedConditions.visibilityOfElementLocated(By.className("bootstrap-dialog-footer-buttons")));

                        } catch (Exception e) {

                            log.setLog("Cliente: " + empresas.get(i).getCnpj() + " não encontrado.");
                            continue;

                        }
                        this.wd.switchTo().activeElement().findElement(By.className("bootstrap-dialog-footer-buttons")).findElement(By.tagName("button")).click();
                        wait.until((Function) ExpectedConditions.elementToBeClickable(By.id("CONTENT_Formulario_GridViewSegVia")));
                        WebElement table = this.wd.findElement(By.id("CONTENT_Formulario_GridViewSegVia"));
                        List<WebElement> allRows = table.findElements(By.tagName("tr"));

                        int sheetRow = 0;
                        for (int r = 0; r < allRows.size(); r++) {
                            XSSFRow xSSFRow = sheet.createRow(rowCount);
                            Cell sheetCell = xSSFRow.createCell(0);
                            sheetCell.setCellValue(empresas.get(i).getCnpj());
                            List<WebElement> cells = allRows.get(r).findElements(By.tagName("td"));
                            int columnCount = 1;

                            for (WebElement cell : cells) {
                                if (cell.getAttribute("data-label") == null) {
                                    sheetCell = xSSFRow.createCell(columnCount++);
                                    sheetCell.setCellValue(cell.getText());
                                    rowCount++;

                                } else if (cell.getAttribute("data-label").equals("2ª VIA")) {
                                    String idElementoCheckbox = "CONTENT_Formulario_GridViewSegVia_CheckFatura_" + (sheetRow++);
                                    ((JavascriptExecutor) this.wd).executeScript("document.getElementById('" + idElementoCheckbox + "').click()");
                                    Thread.sleep(1500);
                                    this.wd.findElement(By.id("CONTENT_Formulario_CodigoBarras")).click();
                                    wait.until((Function) ExpectedConditions.visibilityOfElementLocated(By.id("CONTENT_Formulario_BarCode")));
                                    String codigoBarras = this.wd.findElement(By.id("CONTENT_Formulario_BarCode")).getText();
                                    System.out.println("<<<<<<<COD:" + codigoBarras);
                                    sheetCell = xSSFRow.createCell(columnCount++);
                                    String ajustarCodigoBarras = codigoBarras.substring(codigoBarras.indexOf(":") + 1, codigoBarras.length());
                                    System.out.println("<<<<<<<Ajuste:" + ajustarCodigoBarras);

                                    sheetCell.setCellValue(ajustarCodigoBarras);
                                    ((JavascriptExecutor) this.wd).executeScript("document.getElementById('" + idElementoCheckbox + "').click()");
                                    Thread.sleep(1500);
                                    table = this.wd.findElement(By.id("CONTENT_Formulario_GridViewSegVia"));
                                    allRows = table.findElements(By.tagName("tr"));
                                    rowCount++;

                                } else {
                                    sheetCell = xSSFRow.createCell(columnCount++);
                                    sheetCell.setCellValue(cell.getText());

                                }

                            }

                        }
                        log.setLog("Cliente: " + empresas.get(i).getCnpj() + " Verificado.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.wd.close();
                        log.notDownloadedLog(empresas.get(i).getCnpj());
                    }

                }
            } catch (NoSuchElementException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "ELEMENTO NENCONTRADO!", "ERROR", 0);
                this.wd.quit();
            } catch (UnhandledAlertException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "DADOS INCORRETOS", "ERROR", 0);
                this.wd.quit();
            } catch (Exception ex) {
                ex.printStackTrace();
                log.setLog(ex);
                this.wd.quit();
            }
            workbook.write(outputStream);
            log.endLog();
            this.wd.quit();
            JOptionPane.showMessageDialog(null, "Relatório salvo em:" + caminhoExcel);
        } catch (Exception e) {
            e.printStackTrace();
            log.setLog(e);
            this.wd.quit();
        }
        log.endLog();
    }

    public void autoWait() throws InterruptedException {
        Thread.sleep(500);
    }

    public XSSFSheet inicializeWorkbook(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("Relatorio");
        XSSFRow xSSFRow = sheet.createRow(0);
        xSSFRow.createCell(0).setCellValue("CPF/CNPJ");
        xSSFRow.createCell(1).setCellValue("Referencia");
        xSSFRow.createCell(2).setCellValue("Vencimento");
        xSSFRow.createCell(3).setCellValue("Valor");
        xSSFRow.createCell(4).setCellValue("Situacao");
        xSSFRow.createCell(5).setCellValue("Codigo de barras");
        return sheet;
    }
}
