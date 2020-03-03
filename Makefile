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
#/  @date 2018-07-09
#/
#/ =======================================================================================

help:
	@echo ""
	@echo "type:"
	@echo "   make help"
	@echo "   make install"
	@echo "   make deploy"
	@echo "   make clean"
	@echo "   make fullclean"
	@echo "   make distclean"
	@echo ""
	@echo ""

install:
	make -C tran-comp install
	mvn install

deploy: install
	mvn deploy

clean:
	make -C tran-comp-apps  $@
	make -C tran-comp-lib   $@
	make -C tran-comp-mllib $@

fullclean: clean
	rm -f *~
	make -C tran-comp-apps  $@
	make -C tran-comp-lib   $@
	make -C tran-comp-mllib $@

distclean: fullclean
	make -C tran-comp-apps  $@
	make -C tran-comp-lib   $@
	make -C tran-comp-mllib $@

#/ =======================================================================================
#/ **                                  M A K E F I L E                                  **
#/ ======================================================================== END FILE =====
