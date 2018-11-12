#/ ====================================================================== BEGIN FILE =====
#/ **                                  M A K E F I L E                                  **
#/ =======================================================================================
#/ **                                                                                   **
#/ **  Copyright (c) 2013, Stephen W. Soliday                                           **
#/ **                      stephen.soliday@trncmp.org                                   **
#/ **                      http://research.trncmp.org                                   **
#/ **                                                                                   **
#/ **  -------------------------------------------------------------------------------  **
#/ **                                                                                   **
#/ **  This program is free software: you can redistribute it and/or modify it under    **
#/ **  the terms of the GNU General Public License as published by the Free Software    **
#/ **  Foundation, either version 3 of the License, or (at your option)                 **
#/ **  any later version.                                                               **
#/ **                                                                                   **
#/ **  This program is distributed in the hope that it will be useful, but WITHOUT      **
#/ **  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS    **
#/ **  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.   **
#/ **                                                                                   **
#/ **  You should have received a copy of the GNU General Public License along with     **
#/ **  this program. If not, see <http://www.gnu.org/licenses/>.                        **
#/ **                                                                                   **
#/ ----- Modification History ------------------------------------------------------------
#/
#/  @file Makefile
#/
#/   Provides the top level build.
#/
#/  @date 2018-02-10
#/
#/ =======================================================================================

#EXTRAS = ${CODEBUILD_OPTS} -DcompilerArgument=-Xlint
EXTRAS = ${CODEBUILD_OPTS} -DcompilerArgument=-Xlint:unchecked

all:
	mvn $(EXTRAS) compile
	cat ALIAS

jar:
	@echo $(JAVA_HOME)
	@echo $(PATH)
	mvn $(EXTRAS) package
	@zip -d target/uber-lib-*.jar 'META-INF/*.SF' 'META-INF/*SF'
	cat ALIAS

assembly:
	mvn $(EXTRAS) assembly:assembly

docs:
	mvn $(EXTRAS) javadoc:javadoc

clean:
	mvn clean
	rm -f *.tmp

fullclean: clean
	./clean.sh
	rm -f *.pyc
	rm -f *.pnm *.mod *.ps *.tle

distclean: fullclean
	rm -f test.*.err test.*.out
	rm -f logs/*.log
	rm -rf trncmp trncmp.tar

test:
	mvn test

check:
	mvn test

install:
	mvn install

#/ =======================================================================================
#/ **                                  M A K E F I L E                                  **
#/ ======================================================================== END FILE =====
