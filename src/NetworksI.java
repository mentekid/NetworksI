import ithakimodem.*;


public class NetworksI {

	private static String getIncomingString(Modem modem){
		StringBuilder stringBuilder = new StringBuilder();
		int incomingChar = modem.read();
		while(incomingChar > 0) {
			stringBuilder.append((char)incomingChar);
			incomingChar = modem.read();
		}
		return stringBuilder.toString();
	}
	
	private static boolean writeRequest(Modem modem, String request){
		if(modem.write(request.getBytes())){
			return true;
		}
		return false;
	}
	
	private static void runTest(Modem modem, int Num_iters, String request){
		for (int i=0; i < Num_iters; i++){
			if(!writeRequest(modem, request)){
				System.out.println("Error in write in position "+request+" "+i);
				modem.close();
				return;
			}
			
			System.out.println(getIncomingString(modem));
		}
	}
	
	
	public static void main(String[] args) {
		//setting up modem speed and connection timeout
		int speed = 48000;
		int timeout = 1000;
		Modem modem = new Modem(speed);
		modem.setTimeout(timeout);
		
		
		//To connect, request ATD2310ITHAKI<CR> must be written to buffer
		String connect = "ATD2310ITHAKI\r";
		if(!writeRequest(modem, connect)) {
			System.out.println("Error in write in position connect");
			modem.close();
			return;
		}
		//Print the respond to the connection request
		System.out.println(getIncomingString(modem));
		
		//Run command "TEST<CR>" a few times to test connection
		
		String request = "TEST\r";
		int Num_iters = 10;
		runTest(modem, Num_iters, request);
		
		request = "E7957\r";
		Num_iters = 1000;
		runTest(modem, Num_iters, request);
		
		System.out.println("Connection Terminated Successfully :)");
		modem.close();
		
	}
	

}
