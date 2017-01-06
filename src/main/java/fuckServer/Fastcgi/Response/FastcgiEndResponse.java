package fuckServer.Fastcgi.Response;

/**
 * Created by zuston on 17/1/6.
 */
public class FastcgiEndResponse {
    public int appStatus;
    public int protocolStatus;

    public FastcgiEndResponse(byte[] header) {
        if (header.length != 8) {
            throw new IllegalArgumentException("header length must be 8 !");
        }

        appStatus = (header[0] << 16) + (header[1] << 8) + header[2];
    }

    @Override
    public String toString() {
        return "FCGIEndResponse{" +
                "appStatus=" + appStatus +
                ", protocolStatus=" + protocolStatus +
                '}';
    }
}
