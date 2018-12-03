#!/bin/bash

MXGEN=1000
if [ "" != "$1" ]; then
    MXGEN=$1
fi


rm -f /tmp/stderr.log

LOG="2>> /tmp/stderr.log"


echo ""
echo "===== EUCLIDEAN DISTANCE METRIC ========================================="
echo ""

JRun cluster.Train tp=E dat=data/Iris/iris.labeled   mod=/tmp/sup-test-e.cfg
JRun cluster.Learn tp=E dat=data/Iris/iris.unlabeled mod=/tmp/unsup-test-e.cfg nc=3 seed=1 gen=${MXGEN}

echo ""
JRun cluster.Evaluate tp=E mod=/tmp/sup-test-e.cfg dat=data/Iris/iris.labeled
echo ""

echo ""
JRun cluster.Relabel  tp=E smod=/tmp/sup-test-e.cfg umod=/tmp/unsup-test-e.cfg mod=/tmp/unsup-relabeled-e.cfg
echo ""

JRun cluster.Evaluate tp=E mod=/tmp/unsup-relabeled-e.cfg dat=data/Iris/iris.labeled

echo ""
echo "===== GAUSSIAN DISTANCE METRIC =========================================="
echo ""

JRun cluster.Train tp=G dat=data/Iris/iris.labeled   mod=/tmp/sup-test-g.cfg
JRun cluster.Learn tp=G dat=data/Iris/iris.unlabeled mod=/tmp/unsup-test-g.cfg nc=3 seed=1 gen=${MXGEN}

echo ""
JRun cluster.Evaluate tp=G mod=/tmp/sup-test-g.cfg dat=data/Iris/iris.labeled
echo ""

echo ""
JRun cluster.Relabel  tp=G smod=/tmp/sup-test-g.cfg umod=/tmp/unsup-test-g.cfg mod=/tmp/unsup-relabeled-g.cfg
echo ""

JRun cluster.Evaluate tp=G mod=/tmp/unsup-relabeled-g.cfg dat=data/Iris/iris.labeled

if [ "" == "$1" ]; then
    echo ""
    echo "Next time specify a max gen:  $0 10000"
    echo ""
fi

exit 0
