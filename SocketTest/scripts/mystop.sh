#!/bin/bash
echo "killing vision.sh servcie" >> ~/test.txt

for ii in `ps auxww | grep vam.py | grep -v color | cut -c 8-16`; do echo "killing $i"; kill $ii; done

for ii in `ps auxww | grep video_server.py | grep -v color | cut -c 8-16`; do echo "killing $i"; kill $ii; done

for ii in `ps auxww | grep decision_maker.py | grep -v color | cut -c 8-16`; do echo "killing $i"; kill $ii; done

