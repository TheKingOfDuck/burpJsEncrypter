package burp;

import java.io.File;

public class test {
    public static void main(String[] args) {
        String encryptedPayload;
        encryptedPayload = JsEncrypter.deal("sss");
        System.out.println(encryptedPayload);
        String path = System.getProperty("java.class.path");
        System.out.println();

    }
}
