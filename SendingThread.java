public class SendingThread extends Thread{
	String input;
	MyConnection m;
	
	public SendingThread(MyConnection m){
		this.m = m;
	}
	
	public void run(){
		try{
			while(true){
				input = "Something";
				if(input != null){	
					boolean sent = m.sendMessage(input);
				}
			}
		}
		catch(Exception e){
			System.out.println("Client: Something went wrong... :(");
			e.printStackTrace();
		}	
	}
}