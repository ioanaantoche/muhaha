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
tts    = ALProxy("ALTextToSpeech", "nao.local", 9559)
postureProxy = ALProxy("ALRobotPosture", "nao.local", 9559)
StiffnessOn(motion)
postureProxy.goToPosture("StandInit", 0.5)
motion.moveInit()
motion.post.moveTo(0.5, 0, 0)
tts.post.say("Hello, I am Nao and I'm walking. How are you today?")
motion.waitUntilMoveIsFinished()
postureProxy.goToPosture("LyingBack", 0.5)
StiffnessOff(motion)