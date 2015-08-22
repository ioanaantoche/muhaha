import sys
import time
from naoqi import ALProxy

IP = "nao.local"
PORT = 9559

if (len(sys.argv) < 2):
    print "Usage: 'python RecordAudio.py nume'"
    sys.exit(1)

fileName = "/home/nao/" + sys.argv[1] + ".wav"

aur = ALProxy("ALAudioRecorder", IP, PORT)

channels = [0,0,1,0]
aur.startMicrophonesRecording(fileName, "wav", 160000, channels)
c=raw_input("Sfarsit?")
aur.stopMicrophonesRecording()

c=raw_input("play?")
aup = ALProxy("ALAudioPlayer", IP, PORT)
#Launchs the playing of a file
aup.playFile(fileName,0.5,-1.0)
c=raw_input("gata?")
#Launchs the playing of a file
#aup.playFile("/usr/share/naoqi/wav/random.wav")

#Launchs the playing of a file on the left speaker to a volume of 50%
#aup.playFile("/usr/share/naoqi/wav/random.wav",0.5,-1.0)