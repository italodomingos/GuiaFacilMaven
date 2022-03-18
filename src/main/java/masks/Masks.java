package masks;

import java.text.ParseException;
import javax.swing.text.MaskFormatter;

public class Masks {
  private MaskFormatter cpfMask;
  
  public MaskFormatter cpfMask() throws ParseException {
    if (this.cpfMask == null) {
      this.cpfMask = new MaskFormatter("###.###.###-##");
      this.cpfMask.setPlaceholderCharacter('_');
    } 
    return this.cpfMask;
  }
  
  public MaskFormatter cnpjMask() throws ParseException {
    if (this.cpfMask == null) {
      this.cpfMask = new MaskFormatter("##.###.###/####-##");
      this.cpfMask.setPlaceholderCharacter('_');
    } 
    return this.cpfMask;
  }
  
  public void removeMask() {}
}
