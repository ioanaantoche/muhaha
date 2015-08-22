from naoqi import ALProxy
from naoqi import ALBroker
from naoqi import ALModule
import time
import math

class MoveInfo:
    def __init__(self, component, x, y, tetha, speed, nrSteps):
        self.component = component
        self.x = x
        self.y = y
        self.tetha = tetha
        self.speed = speed
        self.nrSteps = nrSteps

class Move:
    motionProxy = None
    postureProxy = None
    def __init__(self):
        try:
            self.motionProxy = ALProxy("ALMotion", "nao.local", 9559)
            self.postureProxy = ALProxy("ALRobotPosture", "nao.local", 9559)
        except Exception, e:
            print "Error was: ", e
        
    def StiffnessOn(self):
        pNames = "Body"
        pStiffnessLists = 1.0
        pTimeLists = 0.1
        self.postureProxy.goToPosture("Stand", 0.5)
        self.motionProxy.stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)

    def StiffnessOff(self):
        self.postureProxy.goToPosture("Crouch", 0.5)
        pNames = "Body"
        pStiffnessLists = 0.0
        pTimeLists = 0.1
        self.motionProxy.stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)

    def executeGenericMove(self, moveInfo):
        componentName = [moveInfo.component]
        footSteps = [[moveInfo.x, moveInfo.y, moveInfo.tetha]]
        fractionMaxSpeed = [moveInfo.speed]
        clearExisting = False
        for i in xrange(moveInfo.nrSteps):
            self.motionProxy.setFootStepsWithSpeed(componentName, footSteps, fractionMaxSpeed, clearExisting)

    def executeGenericTurn(self, moveInfo):
        self.motionProxy.moveTo(0, 0, moveInfo.tetha)
        self.motionProxy.moveTo(moveInfo.x, moveInfo.y, 0)

    def executeStandInitAndGenericMove(self, moveInfo):
        executeGenericMove(self.moveInfo)

    def executeGenericMoveList(self, moveInfoList):
        for moveInfo in moveInfoList:
            executeGenericMove(moveInfo)
        
    def turnRight(self):
        x = 0
        y = 0
        theta = -math.pi/2
        self.motionProxy.moveTo(x, y, theta)

    def turnLeft(self):
        x = 0
        y = 0
        theta = math.pi/2
        self.motionProxy.moveTo(x, y, theta)

    def moveLeft(self, nrSteps):
        x = 0
        y = -0.05
        theta = 0
        for i in range(nrSteps):
            self.motionProxy.moveTo(x, y, theta)

    def moveRight(self, nrSteps):
        x = 0
        y = 0.05
        theta = 0
        for i in range(nrSteps):
            self.motionProxy.moveTo(x, y, theta)
        
    def moveForward(self,nrSteps):
        x = 0.05
        y = 0
        theta = 0
        for i in range(nrSteps):
            self.motionProxy.moveTo(x, y, theta)
        

    def moveBackward(self, nrSteps):
        x = -0.05
        y = 0
        theta = 0
        for i in range(nrSteps):
            self.motionProxy.moveTo(x, y, theta)

    def goToPosture(self, posture):
        self.postureProxy.goToPosture(posture, 0.3)

if __name__=="__main__":
    move = Move()
