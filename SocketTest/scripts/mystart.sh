#!/bin/bash
mount /home/pi/ramdisk
touch ~/test.txt
export VIRTUALENVWAPPER_PYTHON=/usr/bin/python2.7
export WORKON_HOME=$HOME/.virtualenvs
source /usr/local/bin/virtualenvwrapper.sh
workon cv3
python ~/Github/frc2016competition/SocketTest/scripts/vam.py 2>&1 >> ~/test.txt &
python ~/Github/frc2016competition/SocketTest/scripts/video_server.py 2>&1 >> ~/test.txt &
python ~/Github/frc2016competition/SocketTest/scripts/decision_maker.py 2>&1 >> ~/test.txt &
