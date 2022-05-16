package control.comands;

import com.DeathByCaptcha.Captcha;
import com.DeathByCaptcha.Exception;
import com.itextpdf.text.DocumentException;
import control.captcha.CaptchaBreaker;
import control.excel.ExcelIptu;
import control.tools.Tools;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import logs.Logs;
import model.DBCresponse;
import model.Empresa;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IptuComands {

    private final String IPTU_URL = "https://www.goiania.go.gov.br/sistemas/scarr/asp/scarr33000f0.asp";

    private final Tools tools = new Tools();

    private WebDriver wd;

    private JTextArea jta;

    private int helper = 0;

    public IptuComands(JTextArea jta) {
        this.jta = jta;
    }

    public void faturaIptu(File arquivoExcel, int indexDueDate, Date preferDueDate, int selectedFuntion) throws IOException, InterruptedException, ParseException, AWTException, DocumentException {
        Logs logs = new Logs(this.jta);
        CaptchaBreaker captcha = new CaptchaBreaker(this.jta);
        DBCresponse dbcResponse = new DBCresponse();
        
        logs.beginLog();
        
        this.wd = Tools.setPrefs();
        
        List<Empresa> empresas = new ExcelIptu().getCompaniesIptu(this.jta, arquivoExcel);
        
        int i;
        try {
            for (int j = 0; j < empresas.size(); j++) {
                this.wd.get("https://www.goiania.go.gov.br/sistemas/scarr/asp/scarr33000f0.asp");
                String inscricaoCadastral = empresas.get(j).getIncricaoCadastral();
                do {
                    i = 0;
                    String windowBefore = this.wd.getWindowHandle();
                    try {
                        (new WebDriverWait(this.wd, 10L)).until(webDriver -> Boolean.valueOf(((JavascriptExecutor) webDriver).executeScript("return document.readyState", new Object[0]).equals("complete")));
                        List<WebElement> options = this.wd.findElements(By.tagName("option"));
                        ((WebElement) options.get(indexDueDate)).click();
                        dbcResponse = captchaSolver(captcha);
                        this.wd.findElement(By.id("g-recaptcha-response")).sendKeys(new CharSequence[]{(dbcResponse.getText())});
                        
                        if (!inscricaoCadastral.equals("0")) {
                            List<WebElement> expDates;
                            DateFormat dateFormat;
                            Date[] dueDate;
                            List<WebElement> buttons;
                            int x;
                            this.wd.findElement(By.name("insc")).sendKeys(new CharSequence[]{inscricaoCadastral});
                            this.wd.findElement(By.xpath("/html/body/center/font/form/table[1]/tbody/tr/td[2]/table/tbody/tr[8]/td/input")).click();
                            (new WebDriverWait(this.wd, 10)).until(webDriver -> Boolean.valueOf(((JavascriptExecutor) webDriver).executeScript("return document.readyState", new Object[0]).equals("complete")));
                            switch (selectedFuntion) {
                                case 0:
                                    Tools.printScreen(this.wd, arquivoExcel.getAbsoluteFile().getParent() + "\\AnaliseIPTU" + inscricaoCadastral + ".png");
                                    closeExtraWindows();
                                    break;
                                case 1:
                                    expDates = this.wd.findElements(By.xpath("//*[contains(text(),'/')]"));
                                    dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    dueDate = new Date[expDates.size()];
                                    buttons = this.wd.findElements(By.xpath("//*[@type = 'radio']"));
                                    for (x = 1; x < expDates.size(); x++) {
                                        dueDate[x] = dateFormat.parse(((WebElement) expDates.get(x)).getText());
                                        if (dueDate[x].compareTo(preferDueDate) < 0) {
                                            this.helper = 2;
                                            ((WebElement) buttons.get(x - 1)).click();
                                            this.wd.findElement(By.xpath("//*[@value = 'Gerar Guia (DUAM) para impressão")).click();
                                            for (String winHandle : this.wd.getWindowHandles()) {
                                                this.wd.switchTo().window(winHandle);
                                            }
                                            (new WebDriverWait(this.wd, 10)).until(webDriver -> Boolean.valueOf(((JavascriptExecutor) webDriver).executeScript("return document.readyState", new Object[0]).equals("complete")));
                                            String pdfPath = arquivoExcel.getAbsoluteFile().getParent() + "\\GuiaIPTU" + inscricaoCadastral + "-" + x + ".pdf";
                                            Tools.saveFilesIptu(pdfPath, this.wd);
                                            logs.downloadedLog(inscricaoCadastral + "-" + x);
                                            this.wd.close();
                                            this.wd.switchTo().window(windowBefore);
                                            (new WebDriverWait(this.wd, 10L)).until(webDriver -> Boolean.valueOf(((JavascriptExecutor) webDriver).executeScript("return document.readyState", new Object[0]).equals("complete")));
                                        }
                                    }
                                    if (this.helper == 0) {
                                        ((WebElement) buttons.get(0)).click();
                                        this.wd.findElement(By.xpath("//*[@value = 'Gerar Guia (DUAM) para impressão")).click();
                                        this.wd.close();
                                        for (String winHandle : this.wd.getWindowHandles()) {
                                            this.wd.switchTo().window(winHandle);
                                        }
                                        (new WebDriverWait(this.wd, 10)).until(webDriver -> Boolean.valueOf(((JavascriptExecutor) webDriver).executeScript("return document.readyState", new Object[0]).equals("complete")));
                                        String pdfPath = arquivoExcel.getAbsoluteFile().getParent() + "\\GuiaIPTU" + inscricaoCadastral + ".pdf";
                                        Tools.saveFilesIptu(pdfPath, this.wd);
                                        logs.downloadedLog(inscricaoCadastral);
                                    }
                                    break;
                            }
                        } else {
                            break;
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                        logs.notDownloadedLog(inscricaoCadastral);
                        for (String winHandle : this.wd.getWindowHandles()) {
                            this.wd.switchTo().window(winHandle);
                        }
                        this.wd.close();
                        this.wd.switchTo().window(windowBefore);
                        this.wd.findElement(By.xpath("//*[contains(text(),'Nova Consulta')]")).click();
                    } catch (UnhandledAlertException ex) {
                        ex.printStackTrace();
                        logs.setLog("Captcha incorreto");
                        captcha.reportWrongCaptcha(dbcResponse.getCaptcha());
                        i = 1;
                        this.wd.findElement(By.name("insc")).clear();
                    }
                } while (i == 1);
            }
        } catch (Exception ex) {
            logs.setLog(ex);
            Logger.getLogger(IptuComands.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        logs.endLog();
        this.wd.quit();
    }

    public DBCresponse captchaSolver(CaptchaBreaker captcha) throws InterruptedException, AWTException, IOException, Exception {
//        WebElement imageElement = this.wd.findElement(By.id("id_img_captcha"));
//        File captchaFile = (File) imageElement.getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(captchaFile, new File(System.getProperty("user.dir") + "/captcha/captcha.bmp"));
          String siteUrl = this.wd.getCurrentUrl();
          String googleToken = this.wd.findElement(By.className("g-recaptcha")).getAttribute("data-sitekey");
        return captcha.DBCApiSolver(googleToken, siteUrl);
    }

    public void closeExtraWindows() {
        String windowBefore = this.wd.getWindowHandle();
        if (this.wd.getWindowHandles().size() > 1) {
            for (String winHandle : this.wd.getWindowHandles()) {
                this.wd.switchTo().window(winHandle);
            }
            this.wd.close();
            this.wd.switchTo().window(windowBefore);
        }
    }

}
