import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {

	private static final Point UPPERLEFT = new Point(-1, 1);
	private static final Point UPPERRIGHT = new Point(1, 1);
	private static final Point LOWERRIGHT = new Point(1, -1);
	private static final Point LOWERLEFT = new Point(-1, -1);

	public static void main(String[] args) {

		byte[] localIP = new byte[] { 127, 0, 0, 1 }; // Fix this address to
														// SELF - TODO
		byte[] remoteIP = new byte[] { 127, 0, 0, 1 };
		try {
			InetAddress localAddress = InetAddress.getByAddress(localIP);
			InetAddress remoteAddress = InetAddress.getByAddress(remoteIP);
			// SocketAddress sa = new InetSocketAddress(remoteAddress, 2000);

			Socket s = new Socket(remoteAddress, 3000, localAddress, 2000);

			InputStream input = s.getInputStream();
			OutputStream output = s.getOutputStream();
			
			InputStreamReader reader = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(reader);
			
			System.out.println(br.readLine());

			s.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}