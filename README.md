SimpleChat Simple Chatting Program (java)

Client Compile 방법 javac ChatClient.java

Broadcast method
Inputthread method
Server Compile 방법 javac ChatServer.java

Test 방법 Terminal #1 java ChatServer

Terminal #2 java ChatClient

Terminal #3 java ChatClient
좋은코드 분석을 지속적으로



Simple Chat Upgrade!!
1.현재 접속한 사용자 목록 보기 기능
  "/userlist"를 사용자가 채팅창에 입력하면 현재 접속한 사용자들의 id 및 총 사용자 수를 보여준다.(method : send_userlist())
2.자신이 보낸 채팅 문장은 자신에게는 나타나지 않도록 할 것 boardcast 수정
3.금지어 경고 기능 boardcast, print 기능 수정 및 추가
  서버에 금지어 목록을 미리 등록(5개 이상)
  채팅문장에 금지어가 포함되어 있으면 다른 사용자에게 전송하지 않고, 해당 사용자에게만 적절한 경고 메시지를 보낸다.
  
  
  
  이해해야 할 중요 class method
  
  <class>
  ServerSocket
  Socket
  PrintWriter
  BufferedReader - 어딘가로부터 읽어낼 때 (한줄씩)
  ChatThread
  ChatClient
  InputThread
  HashMap
  Set
  Iterator
  
  broadcast(String msg)
  
  
  
