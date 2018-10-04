#!/bin/bash

echo "========================" >> /tmp/xxxx

    echo $0
    echo $0 >> /tmp/xxxx

for x in $*; do
    echo $x
    echo $x >> /tmp/xxxx
done

exit 0
