#!/usr/bin/python3
#/ ====================================================================== BEGIN FILE =====
#/ **                                 F U Z Z Y P E N D                                 **
#/ =======================================================================================
#/ **                                                                                   **
#/ **  Copyright (c) 2019, Stephen W. Soliday                                           **
#/ **                      stephen.soliday@trncmp.org                                   **
#/ **                      http:#/research.trncmp.org                                   **
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
#/ **  this program. If not, see <http:#/www.gnu.org/licenses/>.                        **
#/ **                                                                                   **
#/ =======================================================================================

import numpy as np
import numpy.random as rnd
import sys











#/ =======================================================================================
def main( argc, argv ):
    #/ -----------------------------------------------------------------------------------
    if ( 2 != argc ):
        sys.stderr.write("""
USAGE: %s fuzzy.cfg
  fuzzy.cfg - configuration file

""" % (argv[0],) )
        return 1
    #/ -----------------------------------------------------------------------------------

    fp = open( argv[1], 'w' )

    fp.write( '4\n' )

    fp.write( '5  -0.700  -0.158 0.000  0.158  0.700' ) # (m/s)   Linear Velocity
    fp.write( '5 -24.000 -12.000 0.000 12.000 24.000' ) # (deg/s) Angular Velocity
    fp.write( '5  -1.000  -0.050 0.000  0.050  1.000' ) # (m)     Position
    fp.write( '5 -12.000  -2.000 0.000  2.000 12.000' ) # (deg)   Rotation

    
        
    return 0

#/ =======================================================================================
if ( '__main__' == __name__ ): sys.exit( main( len(sys.argv), sys.argv ) )
#/ =======================================================================================
#/ **                                 F U Z Z Y P E N D                                 **
#/ ======================================================================== END FILE =====
