import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class JsEncrypter {

    public static String deal(String rawPayload) throws ScriptException, NoSuchMethodException {

        String jsPath = System.getProperty("user.home") + "/burp/jsFile/";

        String encryptedPayload = "error";

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        //遍历Js文件并依次加载进java
        File jsDir = new File(jsPath);
        String[] children = jsDir.list();
        if (children == null) {
            jsDir.mkdir();
        }
        else {
            for (String fileName : children) {
                if (fileName.endsWith(".js")) {
                    //System.out.println();
                    FileReader jsFile = null;
                    try {
                        jsFile = new FileReader(jsPath + fileName);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        engine.eval(jsFile);
                    } catch (ScriptException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        //确认OK就调用js文件中的函数
        if (engine instanceof Invocable) {
            Invocable in = (Invocable) engine;
            encryptedPayload = (String) in.invokeFunction("burpJsEncrypter",rawPayload);
            return encryptedPayload;
        }else {
            System.out.println("Js load error,pls check...");
        }
        return encryptedPayload;
    }
}
