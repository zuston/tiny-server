package fuckServer.Fastcgi.Response;

import java.io.ByteArrayOutputStream;

/**
 * Created by zuston on 17/1/6.
 */
public class FastcgiResponse {
    private int state;
    private FastcgiEndResponse endResponse;
    private ByteArrayOutputStream outputStream;

    public FastcgiResponse() {
        outputStream = new ByteArrayOutputStream();
    }

    public FastcgiEndResponse getEndResponse() {
        return endResponse;
    }

    public void setEndResponse(FastcgiEndResponse endResponse) {
        this.endResponse = endResponse;
    }

    public void appendRespContent(byte[] contentData) {
        if (contentData != null) {
            outputStream.write(contentData, 0, contentData.length);
        }
    }

    public byte[] getResponseContent() {
        return outputStream.toByteArray();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "FCGIResponse{" +
                "\r\n       state=" + state +
                "\r\n       endResponse=" + endResponse +
                "\r\n       responseContent = " + new String(outputStream.toByteArray()) +
                "\r\n}";
    }
}
