#!/usr/bin/python3
#/ ====================================================================== BEGIN FILE =====
#/ **                                 H A R M O N I C S                                 **
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

import matplotlib.pyplot as plt
import numpy as np
import numpy.random as rnd
import sys
from numpy.fft import fft, ifft


#/ =======================================================================================
def GenParams( M ):
    #/ -----------------------------------------------------------------------------------

    par = []

    sum = 0.0e0
    for i in range( M ):
        A = 100.0*rnd.uniform()
        N = float(rnd.randint(1,256))
        P = rnd.uniform()
        par.append( [A,N,P] )
        sum += A
        
    for i in range( M ):
        par[i][0] /= sum

    return par
    
#/ =======================================================================================
def BuildTable( M, N ):
    #/ -----------------------------------------------------------------------------------

    param = GenParams( M )
    
    N_2PI = np.pi * 2.0e0

    x  = 0.0e0
    dx = 1.0e0/float(N-1)

    table = []
    for i in range(N):

        y = 0.0e0
        
        for j in range(M):
            A0  = param[j][0]
            k   = param[j][1]
            phi = param[j][2]
            y += A0 * np.sin( N_2PI*(x*k - phi) )
            
        table.append([ x, y ])
        x += dx
        
    return table

#/ =======================================================================================
def run( fspc, M, N ):
    #/ -----------------------------------------------------------------------------------

    p = int( np.log( float(N) ) / np.log(2.0e0) )

    if ( N != int(2**p) ):
        N = int(2**(p+1))
    
    table = BuildTable( M, N )
    if ( 0 == len(table) ):
        sys.stderr.write( 'Error building table\n' )
        return 1
    
    plt.plot( [row[1] for row in table] )
    plt.show()

    X = np.zeros((N),dtype=complex)

    for i in range( N ):
        X[i] = table[i][1]

    F = fft(X)
    R = ifft(F)

    mse = 0.0e0
    for i in range( N ):
        d = X[i].real - R[i].real
        mse += (d*d)

    print( 'MSE = %g' %(mse,) )
   
    fp = open( fspc, 'w' )
    for i in range( N ):
        fp.write( '%23.16e %23.16e %23.16e %23.16e\n' % (table[i][0],table[i][1],F[i].real,F[i].imag,) )
    fp.close()
    
    return 0
    
#/ =======================================================================================
def main( argc, argv ):
    #/ -----------------------------------------------------------------------------------
    if ( 4 != argc ):
        sys.stderr.write("""
USAGE: %s output.dat M N
  output.dat - output data file
  M          - number of harmonics
  N          - number of samples

""" % (argv[0],) )
        return 1
    #/ -----------------------------------------------------------------------------------

    rnd.seed()
    
    try:
        fspc = argv[1]
        M    = int(argv[2])
        N    = int(argv[3])
        return run( fspc, M, N )
    except ValueError:
        sys.stderr.write( 'Error %s and %s must both be integers\n' % (argv[2],argv[3],) )

    #/ -----------------------------------------------------------------------------------
    return 1

#/ =======================================================================================
if ( '__main__' == __name__ ): sys.exit( main( len(sys.argv), sys.argv ) )
#/ =======================================================================================
#/ **                                 H A R M O N I C S                                 **
#/ ======================================================================== END FILE =====
