#!/usr/bin/python2.7

from SocketServer import BaseRequestHandler, TCPServer
from subprocess import Popen, PIPE
from time import time
from datetime import datetime
import logging

def get_timestamp():
    return datetime.fromtimestamp(time()).strftime('%Y-%m-%d %H:%M:%S')

class ServerRestartHandler(BaseRequestHandler):

    def handle(self):
        logging.info("Received connection from %s" % self.client_address[0])
        msg = self.request.recv(1024)
        logging.info(msg);                
        self.request.send('HTTP/1.1 200 OK\nContent-type: text/plain\n\n')
        if msg.find('action=restart') >= 0:            
            self.request.send('success')
            self.request.close()
            self.restart_jboss()
        else:
            self.request.sendall('fail')
            self.request.close()

    def restart_jboss(self):
        logging.warning("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        logging.warning("Restarting JBoss")
        logging.warning(get_timestamp())
        logging.warning("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        # find the running jboss process and kill it
        p1 = Popen(["ps waux | grep org.jboss.Main | grep -v 'grep'"], shell=True, stdout=PIPE)
        p2 = Popen(["awk \'{print $2}\'"], shell=True, stdin=p1.stdout, stdout=PIPE)
        Popen(["xargs kill -9"], shell=True, stdin=p2.stdout)
        p1.stdout.close()
        p2.stdout.close()
        # run jboss
        logging.debug("run jboss")
        Popen(["sudo /opt/jboss/bin/run.sh -b 0.0.0.0 &"], shell=True)

        
if __name__ == '__main__':
    logging.basicConfig(filename='smservice.log', level=logging.DEBUG)
    logging.info("******************************************************")
    logging.info("AVS Monitor Server Started")
    logging.info(get_timestamp())
    logging.info("******************************************************")
    serv = TCPServer(('', 20000), ServerRestartHandler)
    serv.serve_forever()
