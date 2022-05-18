package logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextArea;

public final class Logs {
  private FileWriter fw = null;
  
  private Date data;
  
  private BufferedWriter bw = null;
  
  private final SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
  
  private final String notepad = System.getProperty("user.dir") + "\\logs\\logs.txt";
  
  private final File dir = new File(this.notepad.substring(0, this.notepad.indexOf("\\logs.txt")));
  
  private final File log = new File(this.notepad);
  
  private final JTextArea jta;
  
  public Logs(JTextArea jta) throws IOException {
    logsVerification();
    this.jta = jta;
    this.bw = new BufferedWriter(this.fw);
  }
  
  public void logsVerification() throws IOException {
    if (!this.log.exists()) {
      this.dir.mkdir();
      this.log.createNewFile();
      this.fw = new FileWriter(this.log, true);
    } else {
      this.fw = new FileWriter(this.log, true);
    } 
  }
  
  public void beginLog() throws IOException {
    this.data = new Date();
    this.bw.write("[" + this.dt.format(this.data) + "] Programa iniciado... ");
    this.bw.newLine();
    this.jta.append("[" + this.dt.format(this.data) + "] Programa iniciado... \n");
  }
  
  public void endLog() throws IOException {
    this.data = new Date();
    this.bw.write("[" + this.dt.format(this.data) + "] Programa finalizado.");
    this.bw.newLine();
    this.jta.append("[" + this.dt.format(this.data) + "] Programa finalizado.\n");
    this.bw.close();
  }
  
  public void setLog(String text) throws IOException {
    this.data = new Date();
    this.bw.write("[" + this.dt.format(this.data) + "] " + text);
    this.bw.newLine();
    this.jta.append("[" + this.dt.format(this.data) + "] " + text + "\n");
  }
  
  public void setLog(Exception ex) throws IOException {
    ex.printStackTrace();
    this.data = new Date();
    this.bw.write("[" + this.dt.format(this.data) + "] Error: " + ex.getMessage());
    this.bw.newLine();
    this.jta.append("[" + this.dt.format(this.data) + "] Error: " + ex.getMessage() + "\n");
    this.bw.close();
  }
  
  public void downloadedLog(String cpfCnpj) throws IOException {
    this.data = new Date();
    this.bw.write("[" + this.dt.format(this.data) + "] Cadastro " + cpfCnpj + " baixado");
    this.bw.newLine();
    this.jta.append("[" + this.dt.format(this.data) + "] Cadastro " + cpfCnpj + " baixado\n");
  }
  
  public void notDownloadedLog(String cpfCnpj) throws IOException {
    this.data = new Date();
    this.bw.write("[" + this.dt.format(this.data) + "] Cadastro " + cpfCnpj + " não possui fatura pendente");
    this.bw.newLine();
    this.jta.append("[" + this.dt.format(this.data) + "] Cadastro " + cpfCnpj + " não possui fatura pendente\n");
  }
}
