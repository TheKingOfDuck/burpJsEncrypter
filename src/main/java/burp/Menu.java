package burp;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Menu implements IContextMenuFactory{
    private int[] selectRange;

    public List<JMenuItem> createMenuItems(final IContextMenuInvocation invocation) {
        List<JMenuItem> menus = new ArrayList();

        selectRange = invocation.getSelectionBounds();
        if(invocation.getInvocationContext() != IContextMenuInvocation.CONTEXT_MESSAGE_EDITOR_REQUEST || selectRange[0] == selectRange[1]){
            return menus;
        }

        JMenuItem encryptMenu = new JMenuItem("burpJsEncrypter");

        encryptMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IHttpRequestResponse reqRsp = invocation.getSelectedMessages()[0];
                byte[] byteReq = reqRsp.getRequest();
                try {
                    String strReq = new String(byteReq);
                    String strSelect = new String(byteReq, selectRange[0],(selectRange[1]-selectRange[0]), "UTF-8");
                    String encryptedStr = JsEncrypter.deal(strSelect);
                    StringBuffer sbReq = new StringBuffer(strReq);
                    sbReq.replace(selectRange[0],selectRange[1],encryptedStr);
                    byte[] newRequst = BurpExtender.helpers.stringToBytes(sbReq.toString());
                    reqRsp.setRequest(newRequst);
                } catch (UnsupportedEncodingException er) {
                    BurpExtender.stderr.println(er.getMessage());
                }
            }
        });

        menus.add(encryptMenu);

        return menus;
    }
}
