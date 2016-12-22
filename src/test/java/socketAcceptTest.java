import fuckServer.Server;

import java.io.IOException;

/**
 * Created by zuston on 16-12-22.
 */
public class socketAcceptTest {
    public static void main(String[] args) throws IOException {
        Server s = new Server(1999);
        s.init();
    }
}
