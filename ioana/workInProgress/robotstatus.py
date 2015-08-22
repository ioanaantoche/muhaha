from naoqi import ALProxy
from naoqi import ALBroker
from naoqi import ALModule
import time

class HeadYawData:
    def __init__(self, mem):
        self.mem = mem
        self.keys = [
            "Device/SubDeviceList/HeadYaw/Position/Actuator/Value", #0: position_actuator
            "Device/SubDeviceList/HeadYaw/Position/Sensor/Value", #1: position_sensor
            "Device/SubDeviceList/HeadYaw/ElectricCurrent/Sensor/Value", #2: current
            "Device/SubDeviceList/HeadYaw/Temperature/Sensor/Value", #3: temperature
            "Device/SubDeviceList/HeadYaw/Temperature/Sensor/Status", #4: temperature_status
            "Device/SubDeviceList/HeadYaw/Hardness/Actuator/Value", #5: stiffness
        ]

    def getPositionActuator(self):
        return self.mem.getListData(self.keys)[0]

    def getPositionSensor(self):
        return self.mem.getListData(self.keys)[1]

    def getCurrent(self):
        return self.mem.getCurrent(self.keys)[2]

    def getTemperature(self):
        return self.mem.getListData(self.keys)[3]

    def getTemperatureStatus(self):
        return self.mem.getListData(self.keys)[4]

    def getStiffness(self):
        return self.mem.getListData(self.keys)[5]

    def getAllData(self):
        return self.mem.getListData(self.keys)

class Status:
    def __init__(self):
        self.mem = ALProxy("ALMemory", "nao.local", 9559)
        self.batteryListData = [
            "Device/SubDeviceList/Battery/Current/Sensor/Value",
            "Device/SubDeviceList/Battery/Charge/Sensor/Status",
            "Device/SubDeviceList/Battery/Charge/Sensor/Value",]
        self.temperatureListData = [
            "Device/SubDeviceList/Head/Temperature/Sensor/Value", #0: CPU temperature
            ]
        self.audioPlayerProxy = ALProxy("ALAudioPlayer" , "nao.local", 9559)
        self.audioDeviceProxy = ALProxy("ALAudioDevice" , "nao.local", 9559)

    def getBatteryStatus(self):
        return self.mem.getListData(self.batteryListData)[2]*100

    def getCPUTemperature(self):
        return self.mem.getListData(self.temperatureListData)[0]

    def getSpeakerVolume(self):
        print self.audioDeviceProxy.getOutputVolume()
        return self.audioDeviceProxy.getOutputVolume()

    def setSpeakerVolume(self, value):
        self.audioDeviceProxy.setOutputVolume(value)
        fileId = self.audioPlayerProxy.loadFile("/usr/share/naoqi/wav/random.wav")
        time.sleep(5)
        self.audioPlayerProxy.play(fileId)

    def printAllSensors(self):
        for key in self.mem.getDataListName():
            print key

