package burp;

public class BurpExtender implements IBurpExtender,IIntruderPayloadProcessor {
    public final static String extensionName = "burpJsEncrypter";
    public final static String version ="0.1";
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;

    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        BurpExtender.callbacks = callbacks;
        helpers = callbacks.getHelpers();
        callbacks.setExtensionName(extensionName+" "+version);
        callbacks.registerIntruderPayloadProcessor(this);

    }

    //IIntruderPayloadProcessor
    public String getProcessorName() {
        return extensionName;
    }

    public byte[] processPayload(byte[] currentPayload, byte[] originalPayload, byte[] baseValue) {
        String rawPayload = new String(currentPayload);
        System.out.println(rawPayload);
        String encryptedPayload = JsEncrypter.deal(rawPayload);
        System.out.println(encryptedPayload);
        return helpers.stringToBytes(encryptedPayload);
    }

}
