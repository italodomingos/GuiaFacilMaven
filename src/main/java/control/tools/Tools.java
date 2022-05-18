package control.tools;

import com.itextpdf.text.DocumentException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Tools {

    public static WebDriver setPrefs() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments(new String[]{"start-maximized"});
        options.setCapability("acceptSslCerts", true);
        options.setCapability("goog:chromeOptions", options);
        return (WebDriver) new ChromeDriver(options);
    }

    public static void saveFilesIptu(String savePath, WebDriver wd) throws IOException, InterruptedException {
        File tempHtml = new File(System.getProperty("user.dir") + "\\temp\\tempHtml.html");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempHtml), "UTF-8"));
        bw.write(wd.getPageSource());
        bw.close();
        File saveFile = new File(savePath);
        if (saveFile.exists() == true) {
            saveFile.delete();
        }
        while (!tempHtml.exists() || !tempHtml.canExecute()) {
            System.out.println("Arquivo html ainda não baixado");
            Thread.sleep(500);
        }

        String command = "\"" + System.getProperty("user.dir") + "\\wkhtmltopdf.exe\" --encoding utf-8 \"" + tempHtml.getPath() + "\" \"" + savePath + "\"";
        Runtime.getRuntime().exec(command);
        while (!saveFile.canExecute()) {
            System.out.println("Arquivo PDF de destino ainda nescrito");
            Thread.sleep(500);
        }

        tempHtml.delete();
    }

    public void saveFiles(File from, File to) throws InterruptedException, IOException {
        while (!from.canExecute()) {
            Thread.sleep(1000);
        }
        if (to.exists() == true) {
            to.delete();
        } else {
            FileUtils.moveFile(from, to);
        }
    }

    public static void printScreen(WebDriver wd, String savePrintScreenPath) throws IOException, DocumentException {
        File screenShootFile = ((TakesScreenshot) wd).getScreenshotAs(OutputType.FILE);
        //String imagePath = System.getProperty("user.dir") + "temp/screenshot.png";
        FileUtils.copyFile(screenShootFile, new File(savePrintScreenPath));
        /*Document document = new Document(PageSize.A4, PageSize.A4.getWidth(), PageSize.A4.getHeight(), PageSize.A4.getWidth(), PageSize.A4.getHeight());
        PdfWriter.getInstance(document, new FileOutputStream(savePrintScreenPath));
        document.open();
        Image image = Image.getInstance(imagePath);
        document.add(image);
        document.close();*/
    }

    public static String refactorCpfCnpj(String cpfCnpj) {
        return cpfCnpj.replace("-", "").replace(".", "").replace("/", "");
    }

}
