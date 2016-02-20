import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
			InputStreamReader reader = new InputStreamReader(input);
            BufferedReader br = new BufferedReader(reader);
            String in = br.readLine();
            
			OutputStream output = s.getOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(output);
			
            if (in.equals("isOnTarget")) {
            	double foo = Math.random();
            	if (foo < 0.5) {
                    System.out.println("yes: " + in);
                    writer.write("yes\n");
                } else {
                    System.out.println("no: " + in);
                    writer.write("no\n");
                }
            } else if (in.equals("whatSpeed")) {
                	double bar = Math.random();
                	if (bar < 0.5) {
                        System.out.println(".75: " +  in);
                        writer.write(".75\n");
                    } else {
                        System.out.println(".25: " + in);
                        writer.write(".25\n");
                    }
            }
            
			writer.flush();
			s.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}