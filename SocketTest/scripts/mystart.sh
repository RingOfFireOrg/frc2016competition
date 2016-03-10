#!/bin/bash
export WORKON_HOME=$HOME/.virtualenvs
source /usr/local/bin/virtualenvwrapper.sh
workon cv
python ~/github/frc2016competition/SocketTest/scripts/vam.py
python ~/github/frc2016competition/SocketTest/scripts/video_server.py
python ~/github/frc2016competition/SocketTest/scripts/decision_maker.py
