package control.captcha;

import com.DeathByCaptcha.AccessDeniedException;
import com.DeathByCaptcha.Exception;
import com.DeathByCaptcha.SocketClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import logs.Logs;

public class CaptchaBreaker {
  private final String dbc_user = "bambui.ti";
  
  private final String dbc_password = "fLDeCf2nGJWV0PcesOA9";
  
  private Logs log;
  
  private com.DeathByCaptcha.Captcha captcha;
  
  public CaptchaBreaker() {}
  
  public CaptchaBreaker(JTextArea jta) throws IOException {
    this.log = new Logs(jta);
  }
  
  public com.DeathByCaptcha.Captcha solve(File captcha_file) throws IOException, Exception, FileNotFoundException, InterruptedException {
    SocketClient socketClient = new SocketClient("bambui.ti", "fLDeCf2nGJWV0PcesOA9");
    try {
      double balance = socketClient.getBalance();
      while (!captcha_file.exists() || !captcha_file.canExecute())
        Thread.sleep(500L); 
      this.captcha = socketClient.decode(captcha_file, 60);
      if (null != this.captcha) {
        System.out.println("CAPTCHA " + this.captcha.id + " solved: " + this.captcha.text);
        captcha_file.delete();
        return this.captcha;
      } 
    } catch (AccessDeniedException e) {
      e.printStackTrace();
    } 
    captcha_file.delete();
    return null;
  }
  
  public void report_wrong() throws IOException {
    SocketClient socketClient = new SocketClient("bambui.ti", "fLDeCf2nGJWV0PcesOA9");
    try {
      socketClient.report(this.captcha);
    } catch (Exception e) {
      this.log.setLog("Problema ao reportar erro de Captcha");
    } catch (IOException ex) {
      Logger.getLogger(CaptchaBreaker.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
  }
}
