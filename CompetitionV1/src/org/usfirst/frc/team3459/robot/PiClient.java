package org.usfirst.frc.team3459.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class PiClient {
	
	final int PORT = 5801;
	
	final byte[] LOCAL_IP = new byte[] { 127, 0, 0, 1 }; 
    final byte[] REMOTE_IP = new byte[] { 10, 34, 59, 22 };
	
	PiClient(){
	
		
	}
	
	public boolean retrieveTargetingState(){
		try {
			
			InetAddress localAddress = InetAddress.getByAddress(LOCAL_IP);
			InetAddress remoteAddress = InetAddress.getByAddress(REMOTE_IP);

			Socket s = new Socket(remoteAddress, 3000, localAddress, 2000);

			InputStream input = s.getInputStream();
			OutputStream output = s.getOutputStream();
			output.write("isOnTarget".getBytes());
			output.flush();
			InputStreamReader reader = new InputStreamReader(input);
			BufferedReader br = new BufferedReader(reader);
			
			String answer = br.readLine();
			s.close();
			
			if(answer.contains("Hit")){
				return true;
			} else {
				return false;
			}

		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
}
