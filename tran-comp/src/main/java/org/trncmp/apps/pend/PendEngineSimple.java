// ====================================================================== BEGIN FILE =====
// **                          P E N D E N G I N E S I M P L E                          **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2019, Stephen W. Soliday                                           **
// **                      stephen.soliday@trncmp.org                                   **
// **                      http://research.trncmp.org                                   **
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
/**
 * @brief   Simple inverted pendulum.
 * @file    PendEngineSimple.java
 *
 * @details Provides the interface and procedures for a cart with
 *          a bob on a thin massless rod.
 *
 * @author  Stephen W. Soliday
 * @date    2019-01-01
 */
// =======================================================================================

package org.trncmp.apps.pend;

import org.trncmp.lib.Math2;
import org.trncmp.lib.RK4;

import java.awt.Graphics2D;
import java.awt.Color;

// =======================================================================================
public class PendEngineSimple extends RK4 {
  // -------------------------------------------------------------------------------------

  protected final Color  BobColor   = new Color( 255, 0, 0 );
  protected final Color  TrackColor = new Color( 0, 255, 0 );
  protected final Color  StopColor  = new Color( 0, 0, 255 );
  protected final Color  CarColor   = new Color( 0, 255, 255 );
  protected final Color  ArmColor   = new Color( 0, 0, 0 );

  protected final double car_mass     = 10.0; // kg
  protected final double car_width    =  0.4; // m
  protected final double car_height   =  0.2; // m

  protected final double bob_mass     =  1.0; // kg
  protected final double bob_radius   =  0.1; // m

  protected final double arm_length   =  2.0; // m
  protected final double arm_width    =  0.2; // m
  
  protected final double track_length =  6.0; // m
  protected final double track_height =  0.1; // m

  protected final double stop_width   =  0.2; // m
  protected final double stop_height  =  0.2; // m

  protected final double pad_width    =  0.5; // m
  protected final double pad_height   =  2.0; // m
  
  protected final double world_width  =  track_length + 2.0 * ( pad_width + stop_width );
  protected final double world_height =
      car_height + track_height + arm_length + bob_radius + ( 2.0 * pad_height );

  protected int track_x, track_y, track_w, track_h;
  protected int lstop_x, lstop_y, lstop_w, lstop_h;
  protected int rstop_x, rstop_y, rstop_w, rstop_h;
  protected int car_x,   car_y,   car_w,   car_h;
  protected int pivot_x, pivot_y;

  protected int bob_x, bob_y, bob_rad;

  protected double pos = 1.0;
  protected double ang = 20.0 * Math2.DEG2RAD;

  protected double[] param  = new double[7];
  protected double[] state  = new double[4];

  // =====================================================================================
  public PendEngineSimple() {
    // -----------------------------------------------------------------------------------
    super(4);
    param[0] = 9.81;                  // Local acceleration due  to gravity
    param[1] = 0.0;                   // Force applied to cart
    param[2] = car_mass;              // Mass of cart
    param[3] = bob_mass;              // Mass of pendulum
    param[4] = arm_length;            // Length of pendulum
    param[5] = (track_length - car_width)/ 2.0;    // Maximum cart position
    param[6] = Math2.DEG2RAD * 90.0;  // Maximum pendulum angle

    reset();
  }

  
  // =====================================================================================
  public void reset() {
    // -----------------------------------------------------------------------------------
    state[0] = 0.1;    // Linear  velocity
    state[1] = 0.0;    // Angular velocity
    state[2] = 0.0;    // Position of cart
    state[3] = 0.02;  // Angle of pendulum
  }


  // =====================================================================================
  public void step( double delta_time, int istep, double force ) {
    // -----------------------------------------------------------------------------------
    param[1] = force;
    integrate( state, 0.0, delta_time, istep, param );
  }

  
  // =====================================================================================
  public void step( double delta_time, int istep ) {
    // -----------------------------------------------------------------------------------
    param[1] = 0.0;
    integrate( state, 0.0, delta_time, istep, param );
  }

  
  // =====================================================================================
  protected void setScale( int w, int h, double X, double A ) {
    // -----------------------------------------------------------------------------------
    double scr_rat = (double) w / (double) h;
    double env_rat = world_width / world_height;

    double EW = 0;
    double EH = 0;
    
    if ( scr_rat > env_rat ) { // fit height
      EW = world_height * scr_rat;
      EH = world_height;
    } else {                   // fit width
      EW = world_width;
      EH = world_width / scr_rat;
    }

    double scaleX = (double) w / EW;
    double scaleY = (double) h / EH;

    double offset = (arm_length + bob_radius - (car_height + track_height)) / 2.0;

    int centerCol =  w / 2;
    int centerRow = (h / 2) + (int)(scaleY * offset);

    track_x = centerCol - (int) (scaleX * track_length / 2.0);
    track_y = centerRow + (int) (scaleY * car_height);
    
    track_w = (int) (scaleX * track_length);
    track_h = (int) (scaleY * track_height);

    lstop_w = (int) (scaleX * stop_width);
    lstop_h = (int) (scaleY * stop_height);
    lstop_x = track_x - lstop_w;
    lstop_y = track_y + track_h - lstop_h;

    rstop_w = lstop_w;
    rstop_h = lstop_h;
    rstop_x = track_x + track_w;
    rstop_y = track_y + track_h - rstop_h;

    pivot_x = centerCol + (int)( scaleX * X );
    pivot_y = centerRow;

    bob_x = pivot_x + (int) ( scaleX * arm_length * Math.sin( A ) );
    bob_y = pivot_y - (int) ( scaleX * arm_length * Math.cos( A ) );

    car_w = (int) (scaleX * car_width);
    car_h = (int) (scaleY * car_height);

    car_x = centerCol + (int) (scaleX * X) - (car_w/2);
    car_y = centerRow;
  }

  

  // =====================================================================================
  public void draw( Graphics2D G, int w, int h ) {
    // -----------------------------------------------------------------------------------
    //double V = state[0];  // Linear  velocity
    //double w = state[1];  // Angular velocity
    //double X = state[2];  // Position of cart
    //double O = state[3];  // Angle of pendulum

    setScale( w, h, state[2], state[3] );

    // draw track

    G.setColor( TrackColor );
    G.fillRect( track_x, track_y, track_w, track_h );

    // draw stops

    G.setColor( StopColor );
    G.fillRect( lstop_x, lstop_y, lstop_w, lstop_h );
    G.fillRect( rstop_x, rstop_y, rstop_w, rstop_h );

    // draw arm
    G.setColor( ArmColor );
    G.drawLine( pivot_x, pivot_y, bob_x, bob_y );

    // draw car
    G.setColor( CarColor );
    G.fillRect( car_x, car_y, car_w, car_h );

    // draw bob

    G.setColor( BobColor );
    G.fillOval( bob_x-5, bob_y-5, 10, 10 );

  }

  // =====================================================================================
  /** Check State.
   *
   *  @param Q current state vector.
   *  @param t current time.
   *  @param P parameter vector.
   *  @return 0=continue, non-zero=stop
   */
  // -------------------------------------------------------------------------------------
  public int CHECK( double[] Q, double t, double[] P ) {
    // -----------------------------------------------------------------------------------
    double V     = Q[0];  // Linear  velocity
    //double w     = Q[1];  // Angular velocity
    double X     = Q[2];  // Position of cart
    
    double O     = Q[3];  // Angle of pendulum

    //double g     = P[0];  // Local acceleration due  to gravity
    //double Fx    = P[1];  // Force applied to cart
    //double M     = P[2];  // Mass of cart
    //double m     = P[3];  // Mass of pendulum
    //double R     = P[4];  // Length of pendulum
    double max_X = P[5];  // Maximum cart position
    
    double max_O = P[6];  // Maximum pendulum angle

    //if ( O < -max_O ) { return 1; }
    //if ( O >  max_O ) { return 2; }

    if ( X < -max_X ) {
      Q[0] = -0.9*V;
      Q[2] = -(2.0*max_X + X);
    }
      
    if ( X >  max_X ) {
      Q[0] = -0.9*V;
      Q[2] = 2.0*max_X - X;
    }
    

    return 0;
  }

  // =====================================================================================
  /** Differential equations.
   *
   *  @param Qd first time derivative of the current state vector.
   *  @param Q  current state vector.
   *  @param t  current time.
   *  @param P  parameter vector.
   */
  // -------------------------------------------------------------------------------------
  public void DIFEQ( double[] Qd, double[] Q, double t, double[] P ) {
    // -----------------------------------------------------------------------------------
    double V     = Q[0];  // Linear  velocity
    double w     = Q[1];  // Angular velocity
    double X     = Q[2];  // Position of cart
    double O     = Q[3];  // Angle of pendulum

    double g     = P[0];  // Local acceleration due  to gravity
    double Fx    = P[1];  // Force applied to cart
    double M     = P[2];  // Mass of cart
    double m     = P[3];  // Mass of pendulum
    double R     = P[4];  // Length of pendulum
    double max_X = P[5];  // Maximum cart position
    //double min_O = P[6];  // Maximum pendulum angle
    
    double St = Math.sin(O);
    double Ct = Math.cos(O);
    // -----------------------------------------------------------------------------------


    // ----- dV/dt ----- Linear Acceleration ---------------
    Qd[0] = (R*St*m*w*w - Ct*St*g*m + Fx) / (St*St*m - M);
    
    // ----- dw/dt ----- Angular Acceleration --------------
    Qd[1] = (Ct*Fx - Ct*R*St*m*w*w + M*St*g + St*g*m) / (R*St*St*m + M*R);
 
    // ----- dV/dt ----- Linear Velocity -------------------
  
    Qd[2] = V;
    
    // ----- dV/dt ----- Angular Velocity ------------------
    Qd[3] = w;
  }

} // end class PendEngine

// =======================================================================================
// **                          P E N D E N G I N E S I M P L E                          **
// ======================================================================== END FILE =====
