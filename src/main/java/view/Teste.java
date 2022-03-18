package view;

import control.tools.Tools;



public class Teste {
  public static void main(String[] args) throws InterruptedException {
    /*System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
    ChromeDriver chromeDriver = new ChromeDriver();
    chromeDriver.get("https://www.eneldistribuicao.com.br/go/LoginAcessoRapidoPagamento.aspx");
    chromeDriver.manage().window().maximize();
    chromeDriver.findElement(By.id("CONTENT_Formulario_NumeroCliente")).sendKeys(new CharSequence[] { "10602288" });
    chromeDriver.findElement(By.id("CONTENT_Formulario_Documento")).sendKeys(new CharSequence[] { "22299682000118" });
    chromeDriver.findElement(By.id("CONTENT_Formulario_Acessar")).click();
    WebDriverWait wait = new WebDriverWait((WebDriver)chromeDriver, 8L);
    wait.until((Function)ExpectedConditions.visibilityOfElementLocated(By.className("bootstrap-dialog-footer-buttons")));
    String winHandleBefore = chromeDriver.getWindowHandle();
    chromeDriver.switchTo().activeElement().findElement(By.className("bootstrap-dialog-footer-buttons")).findElement(By.tagName("button")).click();
    wait.until((Function)ExpectedConditions.elementToBeClickable(By.id("CONTENT_Formulario_GridViewSegVia")));
    System.out.println(chromeDriver.findElement(By.id("CONTENT_Formulario_GridViewSegVia")).getText());*/
    Tools tools = new Tools();
    

  }
}
