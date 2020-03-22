package burp;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;

public class JsEncrypter {
    public static String deal(String rawPayload){

        String encryptedPayload = null;

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        try{
            //遍历Js文件并依次加载进内存
            File jsDir = new File("./js");
            String[] children = jsDir.list();
            if (children == null) {
                jsDir.mkdir();
            }
            else {
                for (int i=0; i< children.length; i++) {
                    String fileName = children[i];
                    if (fileName.endsWith(".js")){
                        System.out.println("Found:" + fileName);
                        FileReader jsFile = new FileReader("./js/" + fileName);
                        engine.eval(jsFile);
                    }
                }
            }

            //测试
            //String rawPayload = "CoolCat";

            //确认OK就调用js文件中的函数
            if (engine instanceof Invocable) {
                Invocable in = (Invocable) engine;
                encryptedPayload = (String) in.invokeFunction("burpJsEncrypter",rawPayload);
                System.out.println(rawPayload + ":" + encryptedPayload);
            }else {
                System.out.println("Js load error,pls check...");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return encryptedPayload;
    }
}
