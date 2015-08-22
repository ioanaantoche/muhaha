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
        self.StiffnessOn()
        
    def StiffnessOn(self):
        pNames = "Body"
        pStiffnessLists = 1.0
        pTimeLists = 0.1
        self.postureProxy.goToPosture("Stand", 0.3)
        self.motionProxy.stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)

    def StiffnessOff(self):
        self.postureProxy.goToPosture("LyingBack", 0.3)
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
        self.postureProxy.goToPosture("Stand", 0.03)
        executeGenericMove(self.moveInfo)

    def executeGenericMoveList(self, moveInfoList):
        self.postureProxy.goToPosture("Stand", 0.05)
        for moveInfo in moveInfoList:
            executeGenericMove(moveInfo)
        
    def turnRight(self):
        self.postureProxy.goToPosture("Standt", 0.03)
        legName = ["LLeg", "RLeg", "LLeg", "RLeg"]
        X       = 0.1
        Y       = 0.1
        Theta   = 0.45
        footSteps = [[X, Y, Theta], [X, -Y, Theta], [X, -Y, Theta], [X, -Y, Theta]]
        fractionMaxSpeed = [1.0, 1.0, 1.0, 1.0]
        clearExisting = False
        self.motionProxy.setFootStepsWithSpeed(legName, footSteps, fractionMaxSpeed, clearExisting)
        self.postureProxy.goToPosture("Stand", 0.03)

    def turnLeft(self):
        self.postureProxy.goToPosture("StandInit", 0.03)
        legName = ["LLeg", "RLeg", "LLeg", "RLeg"]
        X       = 0.1
        Y       = 0.1
        Theta   = -0.45
        footSteps = [[X, Y, Theta], [X, -Y, Theta], [X, -Y, Theta], [X, -Y, Theta]]
        fractionMaxSpeed = [1.0, 1.0, 1.0, 1.0]
        clearExisting = False
        self.motionProxy.setFootStepsWithSpeed(legName, footSteps, fractionMaxSpeed, clearExisting)
        self.postureProxy.goToPosture("Stand", 0.03)

    def moveLeft(self, nrSteps):
        self.postureProxy.goToPosture("Stand", 0.03)
        footStepsList = []
        # 0)Step forward & left with your right foot
        footStepsList.append([["LLeg"], [[0.00, 0.16, 0.0]]])
        # 1)Step forward & right with your left foot
        footStepsList.append([["RLeg"], [[0.00, 0.16, 0.0]]])
        stepFrequency = 0.05
        clearExisting = False
        for i in range(nrSteps):
            self.motionProxy.setFootStepsWithSpeed(footStepsList[0][0],footStepsList[0][1],
                [stepFrequency],clearExisting)
            self.motionProxy.setFootStepsWithSpeed(footStepsList[1][0],footStepsList[1][1],
                [stepFrequency],clearExisting)
        self.postureProxy.goToPosture("Stand", 0.03)

    def moveRight(self, nrSteps):
        self.postureProxy.goToPosture("Stand", 0.03)
        footStepsList = []
        # 0)Step forward & right with your right foot
        footStepsList.append([["RLeg"], [[0.00, -0.16, 0.0]]])
        # 1)Step forward & right with your left foot
        footStepsList.append([["LLeg"], [[0.00, -0.16, 0.0]]])
        stepFrequency = 0.05
        clearExisting = False
        for i in range(nrSteps):
            self.motionProxy.setFootStepsWithSpeed(footStepsList[0][0],footStepsList[0][1],
                [stepFrequency],clearExisting)
            self.motionProxy.setFootStepsWithSpeed(footStepsList[1][0],footStepsList[1][1],
                [stepFrequency],clearExisting)
        self.postureProxy.goToPosture("Stand", 0.03)
        
    def moveForward(self,nrSteps):
        self.postureProxy.goToPosture("Stand", 0.03)
        footStepsList = []
        # 0) Step forward with your left foot
        footStepsList.append([["LLeg"], [[0.06, 0.1, 0.0]]])
        # 1) Step forward with your right foot
        footStepsList.append([["RLeg"], [[0.06, 0.1, 0.0]]])
        stepFrequency = 0.05
        clearExisting = False
        for i in range(nrSteps):
            self.motionProxy.setFootStepsWithSpeed(footStepsList[0][0],footStepsList[0][1],
                [stepFrequency],clearExisting)
            self.motionProxy.setFootStepsWithSpeed(footStepsList[1][0],footStepsList[1][1],
                [stepFrequency],clearExisting)
        self.postureProxy.goToPosture("Stand", 0.03)

    def moveBackward(self, nrSteps):
        self.postureProxy.goToPosture("Stand", 0.03)
        footStepsList = []
        # 0) Step backward with your left foot
        footStepsList.append([["LLeg"], [[-0.06, -0.1, 0.0]]])
        # 1) Step backward with your right foot
        footStepsList.append([["RLeg"], [[-0.06, -0.1, 0.0]]])
        stepFrequency = 0.05
        clearExisting = False
        for i in range(nrSteps):
            self.motionProxy.setFootStepsWithSpeed(footStepsList[0][0],footStepsList[0][1],
                [stepFrequency],False)
            self.motionProxy.setFootStepsWithSpeed(footStepsList[1][0],footStepsList[1][1],
                [stepFrequency],False)
        self.postureProxy.goToPosture("Stand", 0.03)



if __name__=="__main__":
    move = Move()