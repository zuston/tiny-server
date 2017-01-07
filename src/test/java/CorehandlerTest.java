import fuckServer.Http.Handler.CoreHandler;

import java.util.HashMap;

/**
 * Created by zuston on 17/1/6.
 */
public class CorehandlerTest {
    public static void main(String[] args) {
        CoreHandler ch = new CoreHandler();
        HashMap<String,String> hn = new HashMap<String, String>();
        hn.put("name","zistpm");
        hn.put("age","23");
        System.out.println(ch.contentGenerate(hn));

    }
}
