package control.httpManipulating;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import org.apache.commons.io.FileUtils;

public class HttpFiles {
  public static String encodeImage(String input) throws IOException {
    byte[] fileToEncode = FileUtils.readFileToByteArray(new File(input));
    String encodedString = Base64.getEncoder().withoutPadding().encodeToString(fileToEncode);
    return encodedString;
  }
  
  public static void decodeString(String encodedString, String outputPath) throws IOException {
    byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
    FileUtils.writeByteArrayToFile(new File(outputPath), decodedBytes);
  }
}
