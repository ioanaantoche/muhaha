import select 
from socket import *

port = 50000  # where do you expect to get a msg?
bufferSize = 1024 # whatever you need

s = socket(AF_INET, SOCK_DGRAM)
s.bind(('<broadcast>', port))
s.setblocking(0)


send_socket = socket(AF_INET, SOCK_DGRAM)
send_socket.bind(('', 0))
send_socket.setsockopt(SOL_SOCKET, SO_BROADCAST, 1)

while True:
    result = select.select([s],[],[])
    msg = result[0][0].recv(bufferSize) 
    print result[0][0].getsockname()

    data = 'Yes\n'
    send_socket.sendto(data, ('<broadcast>', 50001))