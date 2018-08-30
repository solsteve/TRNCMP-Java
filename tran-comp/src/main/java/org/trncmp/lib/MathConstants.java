// ====================================================================== BEGIN FILE =====
// **                        M A T H C O N S T A N T S . J A V A                        **
// =======================================================================================
// **                                                                                   **
// **  This file is part of the TRNCMP Research Library. (formerly SolLib)              **
// **                                                                                   **
// **  Copyright (c) 2008-2009, Stephen W. Soliday                                      **
// **                           stephen.soliday@trncmp.org                              **
// **                           http://research.trncmp.org                              **
// **                                                                                   **
// **  -------------------------------------------------------------------------------  **
// **                                                                                   **
// **  This program is free software: you can redistribute it and/or modify it under    **
// **  the terms of the GNU General Public License as published by the Free Software    **
// **  Foundation, either version 3 of the License, or (at your option)                 **
// **  any later version.                                                               **
// **                                                                                   **
// **  This program is distributed in the hope that it will be useful, but WITHOUT      **
// **  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS    **
// **  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.   **
// **                                                                                   **
// **  You should have received a copy of the GNU General Public License along with     **
// **  this program. If not, see <http://www.gnu.org/licenses/>.                        **
// **                                                                                   **
// ----- Modification History ------------------------------------------------------------
//
// @file MathConstants.java
// Provides scaling between integer row/col and double lon/lat.
// <p>
// Provides high precision constants.
// 
// $Log: MathConstants.java,v $
// Revision 1.2  2002/04/02 20:24:26  soliday
// Added Pi/8
// 
// Revision 1.1  1997/02/23 14:35:22  soliday
// Initial revision
//
// @author Stephen W. Soliday
// @date 2010-09-03
//
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
public interface MathConstants {
    // -----------------------------------------------------------------------------------

    final double  N_ZERO      = 0.0e0;                   //< 0
    final double  N_HALF      = 5.0e-1;                  //< 1/2
    final double  N_ONE       = 1.0e0;                   //< 1
    final double  N_TWO       = 2.0e0;                   //< 2
    final double  N_THREE     = 3.0e0;                   //< 3
    final double  N_FOUR      = 4.0e0;                   //< 4
    final double  N_FIVE      = 5.0e0;                   //< 5
    final double  N_SIX       = 6.0e0;                   //< 6
    final double  N_SEVEN     = 7.0e0;                   //< 7
    final double  N_EIGHT     = 8.0e1;                   //< 8
    final double  N_NINE      = 9.0e1;                   //< 9
    final double  N_TEN       = 1.0e1;                   //< 10
    final double  N_E         = Math.exp(N_ONE);         //< E (Napier)
    final double  N_PI        = Math.acos(-N_ONE);       //< Pi


    final double N_LN_2      = Math.log(N_TWO);          //<  Log_e(2)
    final double N_LN_10     = Math.log(N_TEN);          //<  Log_e(10)
    final double N_LN_PI     = Math.log(N_PI);           //<  Log_e(Pi)

    final double N_LOG_2     = Math.log10(N_TWO);        //<  Log_10(2)
    final double N_LOG_E     = Math.log10(N_E);          //<  Log_10(E)
    final double N_LOG_PI    = Math.log10(N_PI);         //<  Log_10(Pi)

    final double N_L2_10     = Math.log(N_TEN)/N_LN_2;   //<  Log_2(10)
    final double N_L2_E      = Math.log(N_E)/N_LN_2;     //<  Log_2(E)
    final double N_L2_PI     = Math.log(N_PI)/N_LN_2;    //<  Log_2(Pi)

    final double N_LPI_2     = Math.log(N_TWO)/N_LN_PI;  //<  Log_pi(2)
    final double N_LPI_10    = Math.log(N_TEN)/N_LN_PI;  //<  Log_pi(10)
    final double N_LPI_E     = Math.log(N_E)/N_LN_PI;    //<  Log_pi(E)

    final double N_2PI       = N_TWO*N_PI;               //<  2*Pi
    final double N_2PI_3     = N_TWO*N_PI/N_THREE;       //<  2*Pi
    final double N_3PI       = N_THREE*N_PI;             //<  3*Pi
    final double N_4PI       = N_FOUR*N_PI;              //<  4*Pi
    final double N_PI_2      = Math.acos(N_ZERO);        //<  Pi/2
    final double N_PI_3      = N_PI/N_THREE;             //<  Pi/3
    final double N_PI_4      = N_HALF*N_PI_2;            //<  Pi/4
    final double N_3PI_2     = N_THREE*N_PI_2;           //<  3*Pi/2
    final double N_3PI_4     = N_THREE*N_PI_4;           //<  3*Pi/4
    final double N_5PI_4     = N_FIVE*N_PI_4;            //<  5*Pi/4
    final double N_7PI_4     = N_SEVEN*N_PI_4;           //<  7*Pi/4

    final double N_PI2       = N_PI*N_PI;                //<  Pi**2
    final double N_1_PI      = N_ONE/N_PI;               //<  1/Pi
    final double N_2_PI      = N_TWO/N_PI;               //<  2/Pi

    final double N_SQRTPI    = Math.sqrt(N_PI);          //<  sqrt(Pi)
    final double N_SQRT2PI   = Math.sqrt(N_2PI);         //<  sqrt(2Pi)
    final double N_1_SQRTPI  = N_ONE/N_SQRTPI;           //<  1/sqrt(Pi)
    final double N_1_SQRT2PI = N_ONE/N_SQRT2PI;          //<  1/sqrt(2Pi)
    final double N_2_SQRTPI  = N_TWO/N_SQRTPI;           //<  2/sqrt(Pi)

    final double N_SQRT_PI_2 = Math.sqrt(N_PI/N_TWO);    //<  sqrt(Pi/2)
    final double N_SQRT_PI_4 = Math.sqrt(N_PI)/N_TWO;    //<  sqrt(Pi/4) = sqrt(Pi)/2

    final double N_SQRTE     = Math.exp(N_HALF);         //<  sqrt(E)
    final double N_1_SQRTE   = N_ONE/N_SQRTE;            //<  1/sqrt(E)

    final double N_SQRT2     = Math.sqrt(N_TWO);         //<  sqrt(2)
    final double N_1_SQRT2   = N_ONE/N_SQRT2;            //<  1/sqrt(2)
    final double N_2_SQRT2   = N_TWO/N_SQRT2;            //<  2/sqrt(2)

    final double N_SQRT3     = Math.sqrt(N_THREE);       //<  sqrt(3)
    final double N_1_SQRT3   = N_ONE/N_SQRT3;            //<  1/sqrt(3)
    final double N_2_SQRT3   = N_TWO/N_SQRT3;            //<  2/sqrt(3)

    final double N_180_PI    = 1.8e2/N_PI;               //<  180/Pi
    final double N_PI_180    = N_PI/1.8e2;               //<  Pi/180

    final double N_EULER     = 5.7721566490153286554942724e-01;      //<  Euler constant

    final double N_EPSILON   =  2.22044604925031308e-16;
    final double N_MAX_POS   =  Double.MAX_VALUE;        //<  Use this to init min_value
    final double N_MAX_NEG   = -N_MAX_POS;               //<  Use this to init max_value

    final double RAD2DEG     = 1.8e2 / N_PI;
    final double DEG2RAD     = N_PI  / 1.8e2;
}

// =======================================================================================
// **                        M A T H C O N S T A N T S . J A V A                        **
// ======================================================================== END FILE =====
