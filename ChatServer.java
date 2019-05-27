import java.net.*;
import java.io.*;
import java.util.*;

public class ChatServer {

	public static void main(String[] args) {
		try{
			ServerSocket server = new ServerSocket(10001);
			System.out.println("Waiting connection...");
			HashMap hm = new HashMap();
			while(true){
				Socket sock = server.accept();
				ChatThread chatthread = new ChatThread(sock, hm);
				chatthread.start();
			} // while
		}catch(Exception e){
			System.out.println(e);
		}
	} // main
}

class ChatThread extends Thread{
	private Socket sock;
	private String id;
	private BufferedReader br;
	private HashMap hm;
	private String slang[] = {"wow", "as", "fufu", "je", "su"};
	private boolean initFlag = false;
	public ChatThread(Socket sock, HashMap hm){
		this.sock = sock;
		this.hm = hm;
		try{
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			id = br.readLine();
			broadcast(id + " entered.");
			System.out.println("[Server] User (" + id + ") entered.");
			synchronized(hm){
				hm.put(this.id, pw);
			}
			initFlag = true;
		}catch(Exception ex){
			System.out.println(ex);
		}
	} // construcor
	public void run(){
		try{
			String line = null;
			// 금지어 목록을 포함시킨다. if문을 이용해서 금지어 목록을 만들고 사용자에게 경고의 메시지를 보낸다.
			while((line = br.readLine()) != null){
				int slangSize = slang.length;
				for(int i=0; i<slangSize; i++) {
					if(line.contains(slang[i])) {
						send_warning();
					}
				}
				if(line.equals("/quit"))
					break;
				//"userlist"를 입력하면 현재 접속한 사용자들의 id와 총 사용자 수를 보여준다.
				if(line.equals("/userlist"))
					send_userlist();
				if(line.indexOf("/to ") == 0){
					sendmsg(line);
				}else
					broadcast(id + " : " + line);
			}
		}catch(Exception ex){
			System.out.println(ex);
		}finally{
			synchronized(hm){
				hm.remove(id);
			}
			broadcast(id + " exited.");
			try{
				if(sock != null)
					sock.close();
			}catch(Exception ex){}
		}
	} // run
	public void send_warning(){
		synchronized(hm){
			Set<String> keys = hm.keySet();
			Iterator<String> it = keys.iterator();
			Object obj = hm.get(id);
			PrintWriter pw = (PrintWriter)obj;
			pw.println("Don't use that words");
			pw.flush();
			
			//System.out.println("<user id list>");
			//System.out.println("user id :" +  key;
		}
		}
	public void send_userlist(){
		synchronized(hm){
			Set<String> keys = hm.keySet();
			Iterator<String> it = keys.iterator();
			Object obj = hm.get(id);
			PrintWriter pw = (PrintWriter)obj;
			while (it.hasNext()) {
				String key = it.next(); // Set의 key 값을 하나씩 key에 대입
				pw.println("users id : e" + key);
			
			//System.out.println("<user id list>");
			//System.out.println("user id :" +  key;
		}
		
		pw.println("The number of users : " + hm.size());
		pw.flush();
	}
	}
	public void sendmsg(String msg){
		int start = msg.indexOf(" ") +1;
		int end = msg.indexOf(" ", start);
		if(end != -1){
			String to = msg.substring(start, end);
			String msg2 = msg.substring(end+1);
			Object obj = hm.get(to);
			if(obj != null){
				PrintWriter pw = (PrintWriter)obj;
				pw.println(id + " whisphered. : " + msg2);
				pw.flush();
			} // if
		}
	} // sendmsg
	public void broadcast(String msg){
		synchronized(hm){
			PrintWriter myId = (PrintWriter)hm.get(id);
			Set<String> keys = hm.keySet();
			Iterator<String> it = keys.iterator();
			Collection collection = hm.values();
			Iterator iter = collection.iterator();
			// 메시지를 보낸 ID를 if을 통해서 메시지 보내는 작업을 수행하지 않게 만든다.
			while(iter.hasNext()){
				PrintWriter pw = (PrintWriter)iter.next();
				if(pw.equals(myId)) continue;
				pw.println(msg);
				pw.flush();

			}
		}
	} // broadcast
}
