import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;



public class Client {

	public static void main(String[] args){

		try{
			Socket client = new Socket("localhost", 12345);
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(System.in));

			PrintWriter sendWriter = new PrintWriter(
					client.getOutputStream());

			String data;

			while(true){

				data=buffer.readLine();

				if(data.equals("exit")){
				
					break;
			
			
			
				}


				sendWriter.println(data);
				sendWriter.flush();

			}
 
			sendWriter.close();
			buffer.close();
			client.close();
		
		}catch(IOException e){



		}

	}}
