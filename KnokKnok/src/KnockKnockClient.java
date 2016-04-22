import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import java.io.*;
import java.net.UnknownHostException;

public class KnockKnockClient {
    public static void main(String[] arstring) {
        try {
            SSLSocket sslsocket = createClientSocket();
            BufferedReader bufferedreader = readFromConsole();
            sendDataToServer(sslsocket, bufferedreader);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

	private static SSLSocket createClientSocket() throws IOException,
			UnknownHostException {
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 9999);
		return sslsocket;
	}

	private static void sendDataToServer(SSLSocket sslsocket, BufferedReader bufferedreader) throws IOException {
        OutputStream outputstream = sslsocket.getOutputStream();
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputstreamwriter);
		String string = null;
		while ((string = bufferedreader.readLine()) != null) {
		    bufferedWriter.write(string + '\n');
		    bufferedWriter.flush();
		}
	}

	private static BufferedReader readFromConsole() {
		InputStream inputstream = System.in;
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
		return bufferedreader;
	}
}
  