from naoqi import ALProxy

def StiffnessOn(proxy):
    # We use the "Body" name to signify the collection of all joints
    pNames = "Body"
    pStiffnessLists = 1.0
    pTimeLists = 1.0
    proxy.stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)

def StiffnessOff(proxy):
    # We use the "Body" name to signify the collection of all joints
    pNames = "Body"
    pStiffnessLists = 0.0
    pTimeLists = 1.0
    proxy.stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)

motion = ALProxy("ALMotion", "nao.local", 9559)
postureProxy = ALProxy("ALRobotPosture", "nao.local", 9559)
StiffnessOn(motion)
postureProxy.goToPosture("Crouch", 0.5)

#postureProxy.goToPosture("LyingBack", 0.5)
StiffnessOff(motion)