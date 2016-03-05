How to make this all work?

Launch "python find_game.py"
Launch "python pserver.py"
Launch a webbrowser on the drive station and point it at this 
         machine's ip address at port 12000 ->   http://10.34.59.22:12000 
         or whatever ip it currently has
Launch a client that wants to know if the robot is on target
       "python pclient.py" or "java ../src/SocketClient" (after doing
       "javac SocketClient.java" in that directory or the robot code
       once it gets there.

