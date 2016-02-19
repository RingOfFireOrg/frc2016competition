import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketServer {

	public static void main(String[] args) {

		byte[] localIP = new byte[] { 127, 0, 0, 1 }; // Fix this address to
		// SELF - TODO
		byte[] remoteIP = new byte[] { 127, 0, 0, 1 };
		try {

			ServerSocket ss = new ServerSocket(3000);
			Socket s = ss.accept();

			InputStream input = s.getInputStream();
			OutputStream output = s.getOutputStream();

			OutputStreamWriter writer = new OutputStreamWriter(output);
			writer.write("Server Connected\n");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}