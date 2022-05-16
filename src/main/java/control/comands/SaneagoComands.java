package control.comands;

import control.excel.ExcelSaneago;
import control.tools.Tools;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import logs.Logs;
import model.Empresa;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SaneagoComands {
  private final ExcelSaneago em = new ExcelSaneago();
  
  private final Tools tools = new Tools();
  
  private WebDriver wd;
  
  public void faturaSaneago(File excel_file, JTextArea jta) throws IOException {
    Logs log = new Logs(jta);
    log.beginLog();
    this.wd = Tools.setPrefs();
    try {
      List<Empresa> empresas = this.em.getCompaniesSaneago(jta, excel_file.getAbsolutePath());
      this.wd.manage().window().maximize();
      this.wd.get("https://www.saneago.com.br/agencia-virtual/#/2a_via");
      for (Empresa empresa : empresas) {
        WebDriverWait wait = new WebDriverWait(this.wd, 20);
        wait.until((Function)ExpectedConditions.presenceOfElementLocated(By.id("iframeConteudo")));
        this.wd.switchTo().frame("iframeConteudo");
        wait.until((Function)ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class = 'z-intbox']")));
        List<WebElement> box = this.wd.findElements(By.xpath("//*[@class = 'z-intbox']"));
        ((WebElement)box.get(0)).sendKeys(new CharSequence[] { empresa.getContaDv().substring(0, empresa.getContaDv().length() - 1) });
        ((WebElement)box.get(1)).sendKeys(new CharSequence[] { empresa.getContaDv().substring(empresa.getContaDv().length() - 1) });
        List<WebElement> button = this.wd.findElements(By.xpath("//*[@class = 'z-button']"));
        ((WebElement)button.get(0)).click();
        wait.until((Function)ExpectedConditions.presenceOfElementLocated(By.className("z-checkbox")));
        List<WebElement> checkbox = this.wd.findElements(By.xpath("//*[@class = 'z-checkbox']"));
        if (checkbox.size() <= 1) {
          wait.until((Function)ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class = 'z-button']")));
          this.wd.findElement(By.xpath("//*[contains(text(),'Cancelar')]")).click();
          log.notDownloadedLog(empresa.getContaDv());
        } else {
          ((WebElement)checkbox.get(0)).click();
          this.wd.findElement(By.xpath("//*[@value = 'A']")).click();
          this.wd.findElement(By.xpath("//*[contains(text(),'Imprimir')]")).click();
          wait.until((Function)ExpectedConditions.presenceOfElementLocated(By.className("z-iframe")));
          this.wd.switchTo().frame(this.wd.findElement(By.className("z-iframe")));
          this.wd.findElement(By.id("open-button")).click();
          File fileFrom = new File(System.getProperty("user.home") + "\\Downloads\\relatorio.pdf");
          File fileTo = new File(excel_file.getAbsoluteFile() + "\\Saneago" + empresa.getContaDv() + ".pdf");
          this.tools.saveFiles(fileFrom, fileTo);
          log.downloadedLog(empresa.getContaDv());
          this.wd.switchTo().defaultContent();
          this.wd.findElement(By.xpath("//*[contains(@class,'close')]")).click();
          Thread.sleep(1000L);
          wait.until((Function)ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Cancelar')]")));
          this.wd.findElement(By.xpath("//*[contains(text(),'Cancelar')]")).click();
        } 
      } 
    } catch (NoSuchElementException ex) {
      JOptionPane.showMessageDialog(null, "ELEMENTO NENCONTRADO!", "ERROR", 0);
      this.wd.quit();
    } catch (Exception ex) {
      log.setLog(ex);
      this.wd.quit();
    } 
    log.endLog();
    this.wd.quit();
  }
}
