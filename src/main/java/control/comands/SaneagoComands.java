package control.comands;

import control.captcha.CaptchaBreaker;
import control.excel.ExcelSaneago;
import control.tools.Tools;
import dev.botcity.framework.bot.WebBot;
import dev.botcity.maestro_sdk.runner.BotExecution;
import dev.botcity.maestro_sdk.runner.RunnableAgent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import logs.Logs;
import model.Empresa;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SaneagoComands extends WebBot implements RunnableAgent {

    private JTextArea jta;

    private File excelFile;

    public SaneagoComands(JTextArea jta, File excelFile) {
        try {
            this.jta = jta;
            this.excelFile = excelFile;
            setResourceClassLoader(this.getClass().getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void action(BotExecution botExecution) {
        Logs log;
        try {
            log = new Logs(this.jta);
            log.beginLog();

            WebDriver wd = Tools.setPrefs();
            ExcelSaneago em = new ExcelSaneago();

            try {
                List<Empresa> empresas = em.getCompaniesSaneago(jta, excelFile.getAbsolutePath());
                wd.manage().window().maximize();
                
                for (Empresa empresa : empresas) {
                    wd.get("https://www.saneago.com.br/agencia-virtual/#/2a_via");
                    WebDriverWait wait = new WebDriverWait(wd, 20);
                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.id("iframeConteudo")));
                    wd.switchTo().frame("iframeConteudo");
                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class = 'z-intbox']")));
                    List<WebElement> box = wd.findElements(By.xpath("//*[@class = 'z-intbox']"));
                    ((WebElement) box.get(0)).sendKeys(empresa.getContaDv().substring(0, empresa.getContaDv().length() - 1));
                    ((WebElement) box.get(1)).sendKeys(empresa.getContaDv().substring(empresa.getContaDv().length() - 1));
                    List<WebElement> button = wd.findElements(By.xpath("//*[@class = 'z-button']"));
                    Thread.sleep(2000);
                    ((WebElement) button.get(0)).click();
                    Thread.sleep(1000);
                    try {
                        wd.findElements(By.className("z-textbox")).get(0).sendKeys(empresa.getCnpj());
                        Thread.sleep(2000);
                        ((WebElement) button.get(0)).click();
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }

                    wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.className("z-checkbox")));
                    List<WebElement> checkbox = wd.findElements(By.xpath("//*[@class = 'z-checkbox']"));
                    if (checkbox.size() <= 1) {
                        wait.until((Function) ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class = 'z-button']")));
                        //wd.findElement(By.xpath("//*[contains(text(),'Cancelar')]")).click();
                        log.notDownloadedLog(empresa.getContaDv());
                    } else {
                        ((WebElement) checkbox.get(0)).click();
                        Thread.sleep(2000);

                        ((JavascriptExecutor) wd).executeScript("document.getElementsByClassName(\"z-radio-content\")[1].click()");
                        Thread.sleep(2000);

//                  this.wd.findElement(By.xpath("//*[contains(text(),'Imprimir')]")).click();
                        ((JavascriptExecutor) wd).executeScript("document.evaluate(\"//*[contains(text(),'Imprimir')]\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click()");
                        Thread.sleep(2000);

                        List<WebElement> pdfFrames = wd.findElements(By.className("z-iframe"));
                        String pdfLink = pdfFrames.get(0).getAttribute("src");
                        wd.get(pdfLink);
                        Thread.sleep(2000);
                        Tools.printScreen(wd, excelFile.getParent() + "\\Saneago" + empresa.getContaDv() + ".jpeg");

//                        if (!find("download", 0.97, 10000)) {
//                            notFound("download");
//                            return;
//                        }
//                        click();
//                        paste(excelFile.getParent() + "\\Saneago" + empresa.getContaDv() + ".pdf");
//                        enter();
//
//                    this.wd.findElement(By.xpath("//*[@id=\"download\"]")).click();
//                    File fileFrom = new File(System.getProperty("user.home") + "\\Downloads\\relatorio.pdf");
//                    File fileTo = new File(excel_file.getAbsoluteFile() + "\\Saneago" + empresa.getContaDv() + ".pdf");
//                    this.tools.saveFiles(fileFrom, fileTo);
//                    log.downloadedLog(empresa.getContaDv());
//                    this.wd.switchTo().defaultContent();
//                    this.wd.findElement(By.xpath("//*[contains(@class,'close')]")).click();
//                    Thread.sleep(1000);
//                    wait.until((Function) ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'Cancelar')]")));
//                    this.wd.findElement(By.xpath("//*[contains(text(),'Cancelar')]")).click();
                    }
                }
            } catch (NoSuchElementException ex) {
                ex.printStackTrace();
                wd.quit();
            } catch (Exception ex) {
                log.setLog(ex);
                wd.quit();
            }
            log.endLog();
            wd.quit();
        } catch (IOException ex) {
            Logger.getLogger(SaneagoComands.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void requisicao(String link, String header) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(link);
        httpGet.setHeader("Cookie", "JSESSIONID=" + header);
        try ( CloseableHttpResponse response2 = httpclient.execute(httpGet)) {

            String jsonString = EntityUtils.toString(response2.getEntity());
            System.out.println(jsonString);

        } catch (IOException ex) {
            Logger.getLogger(CaptchaBreaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void notFound(String label) {
        System.out.println("Element not found: " + label);
    }
}
