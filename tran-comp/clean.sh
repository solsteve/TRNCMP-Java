#!/bin/bash

find . -name "*~" | xargs rm -f
rm -f src/main/java/org/trncmp/Version.java

exit 0
