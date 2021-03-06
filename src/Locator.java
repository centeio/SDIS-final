import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Locator extends Thread {
	private Client client;
	private boolean closed;

	public Locator(Client client) {
		this.client = client;	
		this.closed = false;
	}

	public void run() {
		Scanner in = new Scanner(System.in);
		
		do{
			System.out.println("Para sair escreva \"sair\"\nPara dar like escreva\"like\"\nLocalizacao: ");

			String input = in.nextLine();
			if(input.toLowerCase().equals("sair")){
				closed = true;
				try {
					client.getSocket().close();
				} catch (IOException e) {
					System.out.println("Could not close socket from " + client.getUsername());
					e.printStackTrace();
				}

			}else if(input.toLowerCase().equals("like")){
				ArrayList<String> action = new ArrayList<String>();
				action.addAll(Arrays.asList("like","filename"));
				client.addAction(action);
			}else{
				client.setlocation(input.toLowerCase());
			}
		}while(!closed);
		
		in.close();

		client.executor.shutdown();
		while (!client.executor.isTerminated()) {}
	}

	public boolean isClosed() {
		return closed;
	}


}
