#!/c/Python27/python
import socket
import sys

# Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Bind the socket to the port
server_address = ('10.99.99.22', 5801)
print >>sys.stderr, 'starting up on %s port %s' % server_address
sock.bind(server_address)

# Listen for incoming connections
sock.listen(1)

while True:
    # Wait for a connection
    print >>sys.stderr, 'waiting for a connection'
    connection, client_address = sock.accept()
    
    try:
        print >>sys.stderr, 'connection from', client_address

        # Receive the data in small chunks and retransmit it
        while True:
            data = connection.recv(16)
            print >>sys.stderr, 'received "%s"' % data
            
            fo = open("rdfile.txt", "r")
            msg = fo.read()
            print "msg:", msg
            # Close opend file
            fo.close()
            
            if data:
                 if data == 'isOnTarget\n':
                     print >>sys.stderr, 'matched isOnTarget'
                     data = msg
                 elif data == 'whatSpeed\n':
                     print >>sys.stderr, 'matched whatSpeed' 
                     data = msg  
                 else:                  
                     print >>sys.stderr, 'sending data back to the client'
                     data = 'Not Supported\n'
                 connection.sendall(data)            
            else:
                print >>sys.stderr, 'no more data from', client_address
                break
            
            
    finally:
        # Clean up the connection
        connection.close()
