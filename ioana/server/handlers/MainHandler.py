import BaseHTTPServer
import thread
import urlparse
import string
from move import Move
from move import MoveInfo
from battery import BatteryStatus

move = Move()

def sendResponse(s, code, message):
  print "... ", s.path
  s.send_response(code)
  s.send_header("Content-type", "text/html")
  s.end_headers()
  m = "<html><body><p>" +message +"</p></body></html>"
  s.wfile.write(m)

def hello_handler():
    pass
def handlerMoveBackward(move, nrSteps):
    move.moveBackward(nrSteps)

def handlerMoveForward(move, nrSteps):
    move.moveForward(nrSteps)

def handlerMoveRight(move, nrSteps):
    move.moveRight(nrSteps)

def handlerMoveLeft(move, nrSteps):
    move.moveLeft(nrSteps)

def handlerTurnLeft(move):
    move.turnLeft()    

def handlerTurnRight(move):
    move.turnRight()

def handlerExecute(move):
    move.turnRight()

def executeGenericMove(move, moveInfo):
    move.executeGenericMove(moveInfo)

def executeGenericTurn(move, moveInfo):
    move.executeGenericTurn(moveInfo)
  

class MainHandler(BaseHTTPServer.BaseHTTPRequestHandler):
    def do_HEAD(s):
        s.send_response(200)
    def do_GET(s):
        """Respond to a GET request."""
        global move
        if s.path == "/hello":
            try:
                thread.start_new_thread(hello_handler,())
                s.send_response(200)
            except:
                print "Error: cannot start the thread"
        url = s.path
        parsed = urlparse.urlparse(url)
        if string.find(s.path,"/moveBackward") != -1:
            nrSteps = 0
            nrSteps = int(urlparse.parse_qs(parsed.query)['nrSteps'][0])
            thread.start_new_thread(handlerMoveBackward, (move, nrSteps))
            sendResponse(s, 200, "handlerMoveBackward")
            return
        if string.find(s.path,"/moveForward") != -1:
            nrSteps = 0
            nrSteps = int(urlparse.parse_qs(parsed.query)['nrSteps'][0])
            thread.start_new_thread(handlerMoveForward, (move, nrSteps))
            sendResponse(s, 200, "")
            return
        if string.find(s.path,"/moveRight") != -1:
            nrSteps = 0
            nrSteps = int(urlparse.parse_qs(parsed.query)['nrSteps'][0])
            thread.start_new_thread(handlerMoveRight, (move, nrSteps))
            sendResponse(s, 200, "")
            return
        if string.find(s.path,"/moveLeft") != -1:
            nrSteps = 0
            nrSteps = int(urlparse.parse_qs(parsed.query)['nrSteps'][0])
            thread.start_new_thread(handlerMoveLeft, (move, nrSteps))
            sendResponse(s, 200, "")
            return
        if string.find(s.path,"/turnLeft") != -1:
            thread.start_new_thread(handlerTurnLeft, (move))
            sendResponse(s, 200, "")
            return
        if string.find(s.path,"/turnRight") != -1:
            thread.start_new_thread(handlerTurnRight, (move))
            sendResponse(s, 200, "")
            return
        if string.find(s.path,"/executeMove") != -1:    
            nrSteps = int(urlparse.parse_qs(parsed.query)['nrSteps'][0])
            x = float(urlparse.parse_qs(parsed.query)['x'][0])
            y = float(urlparse.parse_qs(parsed.query)['y'][0])
            tetha = float(urlparse.parse_qs(parsed.query)['tetha'][0])
            speed = float(urlparse.parse_qs(parsed.query)['speed'][0])
            component = urlparse.parse_qs(parsed.query)['component'][0]
            moveInfo = MoveInfo(component, x, y, tetha, speed, nrSteps)
            thread.start_new_thread(executeGenericMove, (move, moveInfo))
            sendResponse(s, 200, "")
            return
        if string.find(s.path,"/motorsOff") != -1:
            print "motorsOff"
            move.StiffnessOff()
            sendResponse(s, 200, "")
            return
        if string.find(s.path,"/motorsOn") != -1:
            print "motorsOn"
            move.StiffnessOn()
            sendResponse(s, 200, "")
            return
        if string.find(s.path,"/batteryStatus") != -1:
            print "batteryStatus"
            sendResponse(s, 200, "")
            return
        move.StiffnessOn()
        if string.find(s.path,"/turn") != -1:
            print "turn"
            x = float(urlparse.parse_qs(parsed.query)['x'][0])
            y = float(urlparse.parse_qs(parsed.query)['y'][0])
            tetha = float(urlparse.parse_qs(parsed.query)['tetha'][0])
            moveInfo = MoveInfo(None, x, y, tetha, None, None)
            thread.start_new_thread(executeGenericTurn, (move, moveInfo))
            sendResponse(s, 200, "")
            return


