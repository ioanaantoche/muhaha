import time
import random

from naoqi import ALProxy

def getJointValue(jointName):
        print '[NAO] getJointValue', jointName
        memoryProxy = ALProxy('ALMemory', 'nao.local', 9559)

        aux = "Device/SubDeviceList/" + jointName + "/Position/Sensor/Value"
        val = memoryProxy.getData(aux)
        return val

def rightHandInit(motion):
	print "[NAO] rightHandInit"
	isAbsolute = True
	names = ["RShoulderRoll", "RShoulderPitch", "RElbowYaw", "RHand", "HeadYaw", "HeadPitch", "RElbowRoll"]
	angleLists = [[getJointValue("RShoulderRoll")], [getJointValue("RShoulderPitch")], [getJointValue("RElbowYaw")], [getJointValue("RHand")], [getJointValue("HeadYaw")], [getJointValue("HeadPitch")], [getJointValue("RElbowRoll")]]
	times = [[2.0],[2.0], [2.0], [1.0], [2.0], [2.0], [1.0]]

	motion.angleInterpolation(names, angleLists, times, isAbsolute)

motion = ALProxy('ALMotion', "nao.local", 9559);
motion.setStiffnesses("Head", 1.0);

isAbsolute = True
tp = 0.5
stiff = 1.0

motion.stiffnessInterpolation(["RShoulderRoll", "RShoulderPitch", "RElbowRoll", "RElbowYaw", "RHand"],
                              [stiff,stiff, stiff, stiff, stiff],
                              [tp, tp, tp, tp, tp])

names = ["RShoulderRoll", "RShoulderPitch", "RElbowRoll","RElbowYaw", "RHand", "HeadYaw", "HeadPitch"]
angleLists = [[1.5],[0.2],[1.5],[1.3], [0.3], [-0.5], [1.0]]
times = [[1.0],[2.0],[2.0], [1.0], [1.0], [2.0], [ 2.0]]
motion.angleInterpolation(names, angleLists, times, isAbsolute)

time.sleep(1.0)

motion.stiffnessInterpolation(["RShoulderRoll", "RShoulderPitch", "RElbowRoll", "RElbowYaw", "RHand"].reverse(),
                              [stiff,stiff, stiff, stiff, stiff],
                              [tp, tp, tp, tp, tp])
motion.angleInterpolation(names.reverse(), angleLists.reverse(), times.reverse(), isAbsolute.reverse())


stiff = 0.0;

motion.stiffnessInterpolation(["RShoulderRoll", "RShoulderPitch", "RElbowRoll", "RElbowYaw", "RHand"], [stiff,stiff, stiff, stiff, stiff], [tp, tp, tp, tp, tp])


