import java.io.*;
import java.net.*;

public class Photo{



	

    public static void main(String[] args) {

    	System.out.println("Wait..");
        try{
        	
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("Server..");
        
	
	while(true){	
	
	
	Socket client = serverSocket.accept();
        System.out.println("Accept..");
            InputStream inputStream = client.getInputStream();
            byte[] buffer = new byte[1024];
            ObjectInputStream ois = new ObjectInputStream(inputStream);
           
	   
	    try {
            	byte[] buf = (byte[])ois.readObject();
            	 FileOutputStream fileout = new FileOutputStream("/home/suram/photo2.jpg");
            	 fileout.write(buf);
           
	 	System.out.println("File Generated");	 
            }catch(Exception e) {}
                 
	   }
    }catch (Exception e){

    	e.printStackTrace();
        }


}}
