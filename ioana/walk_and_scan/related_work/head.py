import math
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

for i in xrange(5):
    if i % 2 == 0:
        x = 1
    else: 
        x = -1
    print i
    #motion.post.moveTo(0.5, 0.0, 0)
    motion.moveTo(0.1, 0, 0,
        [ ["MaxStepX", 0.02],         # step of 2 cm in front
          ["MaxStepY", 0.04],         # default value
          ["MaxStepTheta", 0.4],      # default value
          ["MaxStepFrequency", 0.0],  # low frequency
          ["StepHeight", 0.01],       # step height of 1 cm
          ["TorsoWx", 0.0],           # default value
          ["TorsoWy", 0.1] ])         # torso bend 0.1 rad in front


    motion.angleInterpolation(
        ["HeadYaw", ],
        [math.radians(90*x)],
        [15],
        True)


#motion.angleInterpolation(
#    ["HeadYaw", "HeadPitch"],
#    [math.radians(45), math.radians(-45)],
#    [5, 5],
#    False)

#motion.angleInterpolation(
#    ["HeadYaw", "HeadPitch"],
#    [math.radians(-45), math.radians(45)],
#    [5, 5],
#    False)
postureProxy.goToPosture("Crouch", 0.5)

StiffnessOff(motion)