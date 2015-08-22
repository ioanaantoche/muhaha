import time
import sys
import codecs

from naoqi import ALProxy
from constants import *

IP = "nao.local"
PORT = 9559
constants = Constants()

class PresentImage:
    def __init__(self, tts=ALProxy("ALTextToSpeech", IP, PORT)):
        self.tts = tts
        self.fileRoot = constants.root

    def present(self, filename, encoding, language):
        self.tts.setLanguage(language)
        print "Now I am talking"
        try:
            fp = codecs.open(self.fileRoot+filename, encoding=encoding)
            contents = fp.read()
            to_say = contents.encode(encoding)
            self.tts.say(to_say)
            print "I said: ", to_say
        except IOError:
            self.tts.say("I cannot find the input file")


#if __name__ == '__main__':
#    if (len(sys.argv) < 2):
#        print "Usage: 'python RecordAudio.py nume'"
#        sys.exit(1)
#    if (sys.argv[1] not in ['English', 'French']):
#        print "Usage: 'python RecordAudio.py English|French'"
#        sys.exit(1)
#    language = sys.argv[1]
#    tts = ALProxy("ALTextToSpeech", IP, PORT)
#    f = FaceRecognitionHandler(ALProxy("ALFaceDetection", IP, PORT), ALProxy("ALMemory", IP, PORT))
#    pi = PresentImage(tts)
#    while True:
#        faces = f.faceDetection()
#        print faces
#        for face in faces:
#            fileName = language + "/" + str(constants.peopleFaces[face]) + ".txt"
#            pi.present(fileName, 'utf-8', language)
#        time.sleep(5)

