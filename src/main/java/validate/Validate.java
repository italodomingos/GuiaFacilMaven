package validate;

public class Validate {
  public static boolean isCpf(String cpf) {
    int op1 = 10;
    int op2 = 11;
    int sum = 0;
    char[] value = cpf.toCharArray();
    for (int i = 0; i < value.length - 2; i++) {
      int j = Character.getNumericValue(value[i]) * op1;
      sum = j + sum;
      op1--;
    } 
    int firstValidate = sum * 10 % 11;
    int firstVerificationNumber = Character.getNumericValue(value[value.length - 2]);
    sum = 0;
    int aux = 0;
    if (firstValidate == firstVerificationNumber) {
      for (int j = 0; j < value.length - 1; j++) {
        aux = Character.getNumericValue(value[j]) * op2;
        sum = aux + sum;
        op2--;
      } 
      int secondValidate = sum * 10 % 11;
      int secondVerificationNumber = Character.getNumericValue(value[value.length - 1]);
      if (secondValidate == secondVerificationNumber)
        return true; 
    } 
    return false;
  }
  
  public static boolean isCnpj(String cnpj) {
    int firstVerificationNumber, secondVerificationNumber, var1 = 5;
    int var2 = 9;
    int sum = 0;
    char[] value = cnpj.toCharArray();
    int i;
    for (i = 0; i < value.length - 1; i++) {
      if (var1 >= 2) {
        int j = Character.getNumericValue(value[i]) * var1;
        sum += j;
        var1--;
      } else if (var2 >= 2) {
        int j = Character.getNumericValue(value[i]) * var2;
        sum += j;
        var2--;
      } 
    } 
    int validation1step1 = sum % 11;
    if (validation1step1 < 2) {
      firstVerificationNumber = 0;
    } else {
      firstVerificationNumber = 11 - validation1step1;
    } 
    var1 = 6;
    var2 = 9;
    sum = 0;
    int aux = 0;
    for (i = 0; i < value.length; i++) {
      if (var1 >= 2) {
        aux = Character.getNumericValue(value[i]) * var1;
        sum += aux;
        var1--;
      } else if (var2 >= 2) {
        aux = Character.getNumericValue(value[i]) * var2;
        sum += aux;
        var2--;
      } 
    } 
    int validation2step1 = sum % 11;
    if (validation2step1 < 2) {
      secondVerificationNumber = 0;
    } else {
      secondVerificationNumber = 11 - validation2step1;
    } 
    int firstRealNumber = Character.getNumericValue(value[value.length - 2]);
    int secondRealNumber = Character.getNumericValue(value[value.length - 1]);
    if (firstRealNumber == firstVerificationNumber && secondRealNumber == secondVerificationNumber)
      return true; 
    return false;
  }
}
