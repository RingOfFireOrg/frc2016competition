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
        private static final String HIWORLD = "whatSpeed\n";
	public static void main(String[] args) {

		byte[] localIP = new byte[] { 10,99,99,22 }; // Fix this address to
														// SELF - TODO
		byte[] remoteIP = new byte[] { 10,99,99,22 };
		try {
			InetAddress localAddress = InetAddress.getByAddress(localIP);
			InetAddress remoteAddress = InetAddress.getByAddress(remoteIP);

			Socket socket = new Socket(remoteAddress, 5801, localAddress, 2000);

			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			output.write(HIWORLD.getBytes());
			output.flush();
			InputStreamReader reader = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(reader);
			
			String answer = br.readLine();
                        if (answer.contains("H")) {
			    System.out.println("Contains H");
                        }
			System.out.println(answer);
			socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
