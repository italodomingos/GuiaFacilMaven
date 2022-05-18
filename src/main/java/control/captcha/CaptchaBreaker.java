package control.captcha;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import logs.Logs;
import model.DBCresponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class CaptchaBreaker {

    private final String dbc_user = "bambui.ti";

    private final String dbc_password = "fLDeCf2nGJWV0PcesOA9";

    private Logs log;

    //private com.DeathByCaptcha.Captcha captcha;
    public CaptchaBreaker() {
    }

    public CaptchaBreaker(JTextArea jta) throws IOException {
        this.log = new Logs(jta);
    }

//    public com.DeathByCaptcha.Captcha solve(File captcha_file) throws IOException, Exception, FileNotFoundException, InterruptedException {
//        SocketClient socketClient = new SocketClient("bambui.ti", "fLDeCf2nGJWV0PcesOA9");
//        try {
//            double balance = socketClient.getBalance();
//            while (!captcha_file.exists() || !captcha_file.canExecute()) {
//                Thread.sleep(500);
//            }
//            this.captcha = socketClient.decode(captcha_file, 60);
//            if (null != this.captcha) {
//                System.out.println("CAPTCHA " + this.captcha.id + " solved: " + this.captcha.text);
//                captcha_file.delete();
//                return this.captcha;
//            }
//        } catch (AccessDeniedException e) {
//            e.printStackTrace();
//        }
//        captcha_file.delete();
//        return null;
//    }
//    public void report_wrong() throws IOException {
//        SocketClient socketClient = new SocketClient("bambui.ti", "fLDeCf2nGJWV0PcesOA9");
//        try {
//            socketClient.report(this.captcha);
//        } catch (Exception e) {
//            this.log.setLog("Problema ao reportar erro de Captcha");
//        } catch (IOException ex) {
//            Logger.getLogger(CaptchaBreaker.class.getName()).log(Level.SEVERE, (String) null, ex);
//        }
//    }
    public DBCresponse DBCApiSolver(String googleToken, String pageUrl) {
        try {
            log.setLog("Resolvendo o Captcha");
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://api.dbcapi.me/api/captcha");
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("username", dbc_user));
            nvps.add(new BasicNameValuePair("password", dbc_password));
            nvps.add(new BasicNameValuePair("type", "4"));
            nvps.add(new BasicNameValuePair("token_params", "{ \"googlekey\" : \"" + googleToken + "\" , \"pageurl\" : \"" + pageUrl + "\"}"));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            try ( CloseableHttpResponse response2 = httpclient.execute(httpPost)) {

                String jsonString = EntityUtils.toString(response2.getEntity());

                DBCresponse captcha = getDbcResponse(refactorResponse(jsonString));

                return captcha;

            } catch (IOException ex) {
                Logger.getLogger(CaptchaBreaker.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CaptchaBreaker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CaptchaBreaker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public DBCresponse refactorResponse(String response) {

        DBCresponse dbcResponse = new DBCresponse();
        String[] parts = response.split("&");
        dbcResponse.setCaptcha(parts[0].substring(parts[0].indexOf("=") + 1, parts[0].length()));
        dbcResponse.setText(parts[1].substring(parts[1].indexOf("=") + 1, parts[1].length()));
        dbcResponse.setIs_correct(parts[2].substring(parts[2].indexOf("=") + 1, parts[2].length()));
        dbcResponse.setStatus(parts[3].substring(parts[3].indexOf("=") + 1, parts[3].length()));

        return dbcResponse;
    }

    public DBCresponse getDbcResponse(DBCresponse dbcResponse) throws IOException {

        try {
            for (int i = 0; i < 16; i++) {
                Thread.sleep(5000);
                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet("http://api.dbcapi.me/api/captcha/" + dbcResponse.getCaptcha());
                CloseableHttpResponse response = httpclient.execute(httpGet);

                String jsonString = EntityUtils.toString(response.getEntity());
                System.out.println(jsonString);
                dbcResponse = refactorResponse(jsonString);
                if (dbcResponse.getIs_correct().equals("1") && !dbcResponse.getText().isEmpty()) {
                    return dbcResponse;
                } else if (dbcResponse.getIs_correct().equals("0")) {
                    log.setLog("Não foi possível resolver o captcha");
                } else {
                    System.out.println("Captcha ainda não encontrado: " + dbcResponse.getText());

                }

            }

        } catch (InterruptedException ex) {
            this.log.setLog(ex);
        } catch (IOException ex) {
            this.log.setLog(ex);
        }

        return null;
    }

    public void reportWrongCaptcha(String captcha) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://api.dbcapi.me/api/captcha/" + captcha + "/report");
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("username", dbc_user));
        nvps.add(new BasicNameValuePair("password", dbc_password));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CaptchaBreaker.class.getName()).log(Level.SEVERE, null, ex);
        }

        try ( CloseableHttpResponse response2 = httpclient.execute(httpPost)) {

            String jsonString = EntityUtils.toString(response2.getEntity());

            System.out.println(response2.getStatusLine() + "-----" + jsonString);

        } catch (IOException ex) {
            Logger.getLogger(CaptchaBreaker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
