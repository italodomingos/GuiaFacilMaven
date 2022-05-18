package view;

import control.tools.Tools;
import control.captcha.CaptchaBreaker;
import model.DBCresponse;



public class Teste {
  public static void main(String[] args) throws InterruptedException {
      CaptchaBreaker cb = new CaptchaBreaker();
      DBCresponse res = cb.refactorResponse("captcha=481557707&text=&is_correct=1&status=0");
      res.getCaptcha();

  }
}
