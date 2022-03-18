package model;

public class Empresa {
  private String razaoSocial;
  
  private String unidadeConsumidora;
  
  private String cnpj;
  
  private String contaDv;
  
  private String incricaoCadastral;
  
  public Empresa() {}
  
  public Empresa(String razaoSocial, String unidadeConsumidora, String cnpj, String contaDv, String incricaoCadastral) {
    this.razaoSocial = razaoSocial;
    this.unidadeConsumidora = unidadeConsumidora;
    this.cnpj = cnpj;
    this.contaDv = contaDv;
    this.incricaoCadastral = incricaoCadastral;
  }
  
  public String getIncricaoCadastral() {
    return this.incricaoCadastral;
  }
  
  public void setIncricaoCadastral(String incricaoCadastral) {
    this.incricaoCadastral = incricaoCadastral;
  }
  
  public String getRazaoSocial() {
    return this.razaoSocial;
  }
  
  public void setRazaoSocial(String razaoSocial) {
    this.razaoSocial = razaoSocial;
  }
  
  public String getUnidadeConsumidora() {
    return this.unidadeConsumidora;
  }
  
  public void setUnidadeConsumidora(String unidadeConsumidora) {
    this.unidadeConsumidora = unidadeConsumidora;
  }
  
  public String getCnpj() {
    return this.cnpj;
  }
  
  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }
  
  public String getContaDv() {
    return this.contaDv;
  }
  
  public void setContaDv(String contaDv) {
    this.contaDv = contaDv;
  }
}
