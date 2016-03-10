#!/bin/bash
export VIRTUALENVWAPPER_PYTHON=/usr/bin/python2.7
export WORKON_HOME=$HOME/.virtualenvs
source /usr/local/bin/virtualenvwrapper.sh
workon cv3
python ~/Github/frc2016competition/SocketTest/scripts/vam.py
python ~/Github/frc2016competition/SocketTest/scripts/video_server.py
python ~/Github/frc2016competition/SocketTest/scripts/decision_maker.py
