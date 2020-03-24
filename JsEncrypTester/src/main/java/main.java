import javax.script.ScriptException;

public class main {
    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        String rawPayload = "CoolCat";
        String encryptedPayload;
        if (args.length > 0){
            rawPayload = args[0];
            encryptedPayload = JsEncrypter.deal(rawPayload);
        }else {
            encryptedPayload = JsEncrypter.deal(rawPayload);
        }
        System.out.println(rawPayload + ":" + encryptedPayload);
    }
}
