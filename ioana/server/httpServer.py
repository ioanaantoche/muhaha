import time
import sys
import BaseHTTPServer
from move import Move
from naoqi import ALProxy
from naoqi import ALBroker
from naoqi import ALModule
from MainHandler import MainHandler


nao_ip = "192.168.0.192"
nao_port = 9559        
HOST_NAME = '192.168.0.192' # !!!REMEMBER TO CHANGE THIS!!!
PORT_NUMBER = 9000 # Maybe set this to 9000.

def config_nao():
    global nao_ip
    global nao_port

    if len(sys.argv) > 1:
        nao_ip = sys.argv[1]

    if len(sys.argv) > 2:
        nao_port = int(sys.argv[2])

if __name__ == '__main__':
    config_nao()
    server_class = BaseHTTPServer.HTTPServer
    httpd = server_class((HOST_NAME, PORT_NUMBER), MainHandler)
    print time.asctime(), "Server Starts - %s:%s" % (HOST_NAME, PORT_NUMBER)
    try:
        mainBroker = ALBroker("mainBroker", "0.0.0.0", 0, nao_ip, nao_port)
        httpd.serve_forever()
    except KeyboardInterrupt:
        mainBroker.shutdown()
    httpd.server_close()
    print time.asctime(), "Server Stops - %s:%s" % (HOST_NAME, PORT_NUMBER)