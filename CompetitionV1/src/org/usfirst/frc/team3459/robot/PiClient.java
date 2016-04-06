package org.usfirst.frc.team3459.robot;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;


public class PiClient {
	
	final int PORT = 5801;
	
    final byte[] LOCAL_IP = new byte[] {10, 34, 59, 20}; 
    final byte[] REMOTE_IP = new byte[] {10, 34, 59, 10};
    final long INTERVAL = 1000;
    int port = 5801;
    long lastQuery = 0;
	
    File f;
	BufferedWriter bw;
	FileWriter fw;
	
	String answer;
	
	PiClient(){
	
	}

    
	private String readLine(InputStreamReader reader) throws IOException {
        // http://stackoverflow.com/questions/8473382/reading-content-from-file-in-j2me
        int readChar = reader.read();
        StringBuffer string = new StringBuffer("");
        // Read until end of file or new line
               
        while (readChar != '\n') {
            if (readChar != '\r' && readChar != -1) {
               string.append((char) readChar);
            }
            // Read the next character
            readChar = reader.read();
        }
        return string.toString();
	}

	
	
	
	public boolean retrieveTargetingState(){
		 Socket s = null;
		 long now = System.currentTimeMillis();
		 if ((now - lastQuery) < INTERVAL) {
			 return (false);
		 } else {
			 lastQuery = now;
		 }
		 try {
			
	//		InetAddress localAddress = InetAddress.getByAddress(LOCAL_IP);
			InetAddress remoteAddress = InetAddress.getByAddress(REMOTE_IP);

	//		int localPort = port;
			port += 1;
			if (port > 5810) {
				port = 5801;
			}
		//	s = new Socket(remoteAddress, 5801, localAddress, localPort);
			s = new Socket(remoteAddress, 5801);

			InputStream input = s.getInputStream();
			OutputStream output = s.getOutputStream();
			output.write("isOnTarget".getBytes());
			output.flush();
			InputStreamReader reader = new InputStreamReader(input);
			
			answer = this.readLine(reader);
			System.out.print(answer);
			if(answer.contains("H") ){
				answer = "";
				return true;
			} else {
				answer = "";
				return false;
			}

		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		finally {
			if (s != null) {
				try {
			        s.close();
				} catch (Exception e) {
					return false;
				}
			}
		}
	}
}
