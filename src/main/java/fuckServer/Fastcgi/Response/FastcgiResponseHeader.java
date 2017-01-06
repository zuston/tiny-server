package fuckServer.Fastcgi.Response;

/**
 * Created by zuston on 17/1/6.
 */
public class FastcgiResponseHeader {
    public int version;
    public int type;
    public int requestID;
    public int contentLength;
    public int paddingLength;
    public int reserverd;

    public FastcgiResponseHeader(byte[] header) {
        if (header.length != 8) {
            throw new IllegalArgumentException("header length must be 8 !");
        }
        version = header[0];
        type = header[1];
        int requestIdB1 = (header[2] << 8) & 0xFF00;
        int requestIdB0 = header[3] & 0xFF;
        requestID = requestIdB1 + requestIdB0;
        int contentLengthB1 = (header[4] << 8) & 0x00FF00;
        int contentLengthB0 = header[5] & 0xFF;
        contentLength = contentLengthB0 + contentLengthB1;
        paddingLength = header[6];
        reserverd = header[7];
    }

    @Override
    public String toString() {
        return "FCGIResponseHeaderHandler{" +
                "version=" + version +
                ", type=" + type +
                ", requestID=" + requestID +
                ", contentLength=" + contentLength +
                ", paddingLength=" + paddingLength +
                ", reserverd=" + reserverd +
                '}';
    }
}
