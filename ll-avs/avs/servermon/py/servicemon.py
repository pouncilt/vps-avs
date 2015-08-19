#!/usr/bin/python2.7

from subprocess import Popen, PIPE
from urllib2 import urlopen, URLError
from socket import timeout
from time import sleep, time, clock
from datetime import datetime
import logging

LOGIN_URL = 'http://localhost/avs/w/login/LoginController?institution=%s&userDuz=%s'
APP_REQUEST_URL = ('http://localhost/avs/w/s/avs/avs.action?patientDfn=%s&datetimes=%s&locationIens=%s' +
    '&locationNames=%s&comments=&fontClass=normalFont&language=en&labDateRange=-1&sections=%s&printAllServiceDescriptions=false' +
    '&selectedServiceDescriptions=&locked=false&charts=&remoteVaMedicationsHtml=&remoteNonVaMedicationsHtml=&initialRequest=true'
)

STATION_NO = '605'
USER_DUZ = '42043'
PATIENT_DFN = '10190057'
DATETIMES = '3140402.13'
LOCATION_IENS = '5782'
LOCATION_NAMES = 'LL/BHOST/VASH'
SECTIONS = ('clinicsVisited,providers,diagnoses,vitals,immunizations,' +
  	'orders,appointments,comments,pcp,primaryCareTeam,allergies,medications'
)    

LOGIN_TIMEOUT = 5
AVS_REQUEST_TIMEOUT = 60
CHECK_DELAY = 60
RESTART_DELAY = 60 * 5

ttr_log = open('./ttr.log', 'wb')
current_milli_time = lambda: int(round(time() * 1000))

def get_timestamp():
    return datetime.fromtimestamp(time()).strftime('%Y-%m-%d %H:%M:%S')

def restart_jboss():
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
    # sleep for a few minutes
    sleep(RESTART_DELAY)

def write_ttr(t, status):
    ttr_log.write("%s,%d,%s\n" % (get_timestamp(), t, status))
    ttr_log.flush()
  
def check_server():
    d = current_milli_time()
    logging.info("Checking server status: %s" % (get_timestamp()))
    try:
        results = urlopen(APP_REQUEST_URL % (PATIENT_DFN, DATETIMES, LOCATION_IENS, LOCATION_NAMES, SECTIONS), None, AVS_REQUEST_TIMEOUT).read()        
        print results
        d = current_milli_time() - d
        logging.info("AVS response received in %4d msec" % d)
        write_ttr(d, "")
    except URLError, e:
        write_ttr(d, "error encountered")
        logging.error("Error encountered: %s" % e)
        restart_jboss()
    except timeout, e:
        write_ttr(d, "timeout occurred")
        logging.error("Timeout occurred: %s" % e)
        restart_jboss()

def login_server():
    d = current_milli_time()
    logging.info("Login: %s" % (get_timestamp()))
    try:
        results = urlopen(LOGIN_URL % (STATION_NO, USER_DUZ), None, LOGIN_TIMEOUT).read()
        print results
        d = current_milli_time() - d
        logging.info("Login response received in %4d msec" % d)
        write_ttr(d, "")
        #check_server()
    except URLError, e:
        write_ttr(d, "error encountered")
        logging.error("Error encountered: %s" % e)
        restart_jboss()
    except timeout, e:
        write_ttr(d, "timeout occurred")
        logging.error("Timeout occurred: %s" % e)
        restart_jboss()
        
if __name__ == '__main__':
    logging.basicConfig(filename='sm.log', level=logging.DEBUG)
    logging.info("******************************************************")
    logging.info("AVS Server Monitor Started")
    logging.info(get_timestamp())
    logging.info("******************************************************")
    while 1:
      login_server()
      sleep(CHECK_DELAY)

