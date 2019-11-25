
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;


public class Server {

	public static void main(String[] args){

		try{
			ServerSocket server = new ServerSocket(12345);
			Socket client = server.accept();

			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(
						client.getInputStream()));

			String data;

			while(true){

				data=buffer.readLine();

				if(data==null){
					System.out.println("상대방과 연결이 끊겼습니다.");
					break;
				}else{
					System.out.println("상대방 : "+data);
				}



			}

			buffer.close();

		}catch(IOException e){
		}


	}}
