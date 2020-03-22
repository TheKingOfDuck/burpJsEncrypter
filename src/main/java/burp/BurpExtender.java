package burp;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;

public class BurpExtender implements IBurpExtender,IIntruderPayloadProcessor {
    public final static String extensionName = "burpJsEncrypter";
    public final static String version ="0.1";
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;
    public static PrintWriter stderr;

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        BurpExtender.callbacks = callbacks;
        helpers = callbacks.getHelpers();
        stdout = new PrintWriter(callbacks.getStdout(),true);
        stderr = new PrintWriter(callbacks.getStderr(),true);

        callbacks.setExtensionName(extensionName+" "+version);
        callbacks.registerIntruderPayloadProcessor(this);
        callbacks.registerContextMenuFactory(new Menu());

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                String bannerInfo =
                        "[+] " + BurpExtender.extensionName + " is loaded\n"
                                + "[+]\n"
                                + "[+] #####################################\n"
                                + "[+]    " + BurpExtender.extensionName + " v" + BurpExtender.version +"\n"
                                + "[+]    Anthor: 米斯特burp生态项目组CoolCat\n"
                                + "[+]    Github: https://github.com/TheKingOfDuck\n"
                                + "[+] ######################################";
                stdout.println(bannerInfo);
                String jsPath = System.getProperty("user.home") + "/burp/jsFile/";
                //System.out.println(jsPath);
                File init = new File(jsPath);
                if (!init.exists()){
                    init.mkdirs();
                }

                JOptionPane.showMessageDialog(null, "Pls copy main.js and encrypt js to " + jsPath, "TheKingOfDuck", JOptionPane.PLAIN_MESSAGE);
            }
        });

    }

    //IIntruderPayloadProcessor
    @Override
    public String getProcessorName() {
        return extensionName;
    }

    @Override
    public byte[] processPayload(byte[] currentPayload, byte[] originalPayload, byte[] baseValue) {
        String rawPayload = new String(currentPayload);
        String encryptedPayload = JsEncrypter.deal(rawPayload);
        stdout.println(rawPayload + "\t" +encryptedPayload);
        return encryptedPayload.getBytes();
    }
}
