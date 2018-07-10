#/ ====================================================================== BEGIN FILE =====
#/ **                                  M A K E F I L E                                  **
#/ =======================================================================================
#/ **                                                                                   **
#/ **  Copyright (c) 2017, L3 Technologies Advanced Programs                            **
#/ **                      One Wall Street #1, Burlington, MA 01803                     **
#/ **                                                                                   **
#/ **  -------------------------------------------------------------------------------  **
#/ **                                                                                   **
#/ **  This file, and associated source code, is not free software; you may not         **
#/ **  redistribute it and/or modify it. This file is part of a research project        **
#/ **  that is in a development phase. No part of this research has been publicly       **
#/ **  distributed. Research and development for this project has been at the sole      **
#/ **  cost in both time and funding by L3 Technologies Advanced Programs.              **
#/ **                                                                                   **
#/ **  Any reproduction of computer software or portions thereof marked with this       **
#/ **  legend must also reproduce the markings.  Any person who has been provided       **
#/ **  access to such software must promptly notify L3 Technologies Advanced Programs.  **
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
	make -C tran-comp $@

fullclean: clean
	rm -f *~
	make -C tran-comp $@

distclean: fullclean
	make -C tran-comp $@

#/ =======================================================================================
#/ **                                  M A K E F I L E                                  **
#/ ======================================================================== END FILE =====
