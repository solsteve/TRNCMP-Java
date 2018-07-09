#!/bin/bash
#/ ====================================================================== BEGIN FILE =====
#/ **                                   I N S T A L L                                   **
#/ =======================================================================================
#/ **                                                                                   **
#/ **  Copyright (c) 2013, Stephen W. Soliday                                           **
#/ **                      stephen.soliday@trncmp.org                                   **
#/ **                      http://research.trncmp.org                                   **
#/ **                                                                                   **
#/ **  -------------------------------------------------------------------------------  **
#/ **                                                                                   **
#/ **  Perform the CodeBuild   make and install                                         **
#/ **                                                                                   **
#/ =======================================================================================

rm -rf trncmp ../trncmp.tar

mkdir -p trncmp/data
mkdir -p trncmp/scripts

cp ./scripts/* ./trncmp/scripts/
cp trncmp*.ini ./trncmp/
cp bin/trncmp  ./trncmp/
cp ./target/uber-trncmp-*.jar ./trncmp/trncmp.jar
chmod 755 ./trncmp/trncmp
chmod 644 ./trncmp/trncmp.jar
chmod 644 ./trncmp/trncmp*.ini

pushd trncmp/data
cp -ra ../../data/TLE .
popd

tar -cvf trncmp.tar ./trncmp
cp ./trncmp.tar ../trncmp.tar

exit 0



#/ =======================================================================================
#/ **                                   I N S T A L L                                   **
#/ ======================================================================== END FILE =====
