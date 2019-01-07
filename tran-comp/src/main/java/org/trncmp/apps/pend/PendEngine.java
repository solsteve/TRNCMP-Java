// ====================================================================== BEGIN FILE =====
// **                                P E N D E N G I N E                                **
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
 * @brief   Physical inverted pendulum.
 * @file    PendEngine.java
 *
 * @details Provides the interface and procedures for cart and pole.
 *
 * @author  Stephen W. Soliday
 * @date    2019-01-01
 */
// =======================================================================================

package org.trncmp.apps.pend;

import org.trncmp.lib.Math2;
import org.trncmp.lib.RK4;
import org.trncmp.lib.StringTool;
import org.trncmp.lib.ConfigDB;

import java.awt.Graphics2D;
import java.awt.Color;

// =======================================================================================
public class PendEngine extends RK4 {
  // -------------------------------------------------------------------------------------

  protected final Color  ArmColor   = new Color( 0, 0, 0 );
  protected final Color  CarColor   = new Color( 27,  99,  41 );
  protected final Color  TrackColor = new Color( 95, 146, 157 );
  protected final Color  StopColor  = new Color( 67,  79, 133 );

  protected double car_mass     = 0.0;
  protected double car_width    = 0.0;
  protected double car_height   = 0.0;

  protected double arm_mass     = 0.0;
  protected double arm_length   = 0.0;
  protected double arm_width    = 0.0;
  
  protected double track_length = 0.0;
  protected double track_height = 0.0;

  protected double stop_width   = 0.0;
  protected double stop_height  = 0.0;

  protected double world_width  = 0.0;
  protected double world_height = 0.0;

  protected double init_V = 0.0;
  protected double init_w = 0.0;
  protected double init_X = 0.0;
  protected double init_O = 0.0;

  protected int arm_tip_x, arm_tip_y;
  protected int pivot_x,   pivot_y;
  protected int car_x,     car_y,   car_w,   car_h;

  protected int track_x, track_y, track_w, track_h;
  protected int lstop_x, lstop_y, lstop_w, lstop_h;
  protected int rstop_x, rstop_y, rstop_w, rstop_h;
  
  protected double pos = 1.0;
  protected double ang = 20.0 * Math2.DEG2RAD;

  protected double[] param  = new double[7];
  protected double[] state  = new double[4];

  // =====================================================================================
  public PendEngine( ConfigDB.Section sec ) {
    // -----------------------------------------------------------------------------------
    super(4);

    try {

      car_mass     = StringTool.asReal8( sec.get( "cart_mass" ) );
      car_width    = StringTool.asReal8( sec.get( "cart_width" ) );
      car_height   = StringTool.asReal8( sec.get( "cart_height" ) );

      arm_mass     = StringTool.asReal8( sec.get( "arm_mass" ) );
      arm_length   = StringTool.asReal8( sec.get( "arm_length" ) );
      arm_width    = StringTool.asReal8( sec.get( "arm_width" ) );
  
      track_length = StringTool.asReal8( sec.get( "track_length" ) );
      track_height = StringTool.asReal8( sec.get( "track_height" ) );

      stop_width   = StringTool.asReal8( sec.get( "stop_width" ) );
      stop_height  = StringTool.asReal8( sec.get( "stop_height" ) );

      param[0]      = StringTool.asReal8( sec.get( "grav" ) );

      init_V = StringTool.asReal8( sec.get( "init_V" ) );
      init_w = StringTool.asReal8( sec.get( "init_w" ) ) * Math2.DEG2RAD;
      init_X = StringTool.asReal8( sec.get( "init_X" ) );
      init_O = StringTool.asReal8( sec.get( "init_O" ) ) * Math2.DEG2RAD;

    } catch( ConfigDB.NoSuchKey e ) {
      System.err.println( e.toString() );
    }

    world_width  = 2.0 * arm_length + track_length;
    world_height = 2.0 * arm_length;

    param[1] = 0.0;                              // Force applied to cart
    param[2] = car_mass;                         // Mass of cart
    param[3] = arm_mass;                         // Mass of Arm
    param[4] = arm_length;                       // Length of pendulum
    param[5] = (track_length - car_width) / 2.0; // Maximum cart position
    param[6] = Math2.DEG2RAD * 90.0;             // Maximum pendulum angle

    reset();
  }

  // =====================================================================================
  public double[] getState() {
    // -----------------------------------------------------------------------------------
    return state;
  }
  
  // =====================================================================================
  public void reset() {
    // -----------------------------------------------------------------------------------
    state[0] = init_V;    // Linear  velocity
    state[1] = init_w;    // Angular velocity
    state[2] = init_X;    // Position of cart
    state[3] = init_O;   // Angle of pendulum (radians)
  }


  // =====================================================================================
  public void step( double delta_time, int istep ) {
    // -----------------------------------------------------------------------------------
    param[1] = 0.0;
    integrate( state, 0.0, delta_time, istep, param );
  }

  
  // =====================================================================================
  public void step( double delta_time, int istep, double force ) {
    // -----------------------------------------------------------------------------------
    param[1] = force;
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

    double offset = (arm_length - (car_height + track_height)) / 2.0;

    int centerCol = w / 2;
    int centerRow = h / 2;

    track_w = (int) (scaleX * track_length);
    track_h = (int) (scaleY * track_height);
    track_x = centerCol - track_w/2;
    track_y = centerRow - track_h/2;


    lstop_w = (int) (scaleX * stop_width);
    lstop_h = (int) (scaleY * stop_height);
    lstop_x = track_x - lstop_w;
    lstop_y = centerRow - lstop_h/2;

    rstop_w = lstop_w;
    rstop_h = lstop_h;
    rstop_x = track_x + track_w;
    rstop_y = lstop_y;

    pivot_x = centerCol + (int)( scaleX * X );
    pivot_y = centerRow;

    arm_tip_x = pivot_x + (int) ( scaleX * arm_length * Math.sin( A ) );
    arm_tip_y = pivot_y - (int) ( scaleX * arm_length * Math.cos( A ) );

    car_w = (int) (scaleX * car_width);
    car_h = (int) (scaleY * car_height);

    car_x = pivot_x - car_w/2;
    car_y = pivot_y - car_h/2;
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

    // draw car
    G.setColor( CarColor );
    G.fillRect( car_x, car_y, car_w, car_h );

    // draw arm
    G.setColor( ArmColor );
    G.drawLine( pivot_x, pivot_y, arm_tip_x, arm_tip_y );

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
    //double L     = P[4];  // Length of pendulum
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
    double L     = P[4];  // Length of pendulum
    double max_X = P[5];  // Maximum cart position
    //double min_O = P[6];  // Maximum pendulum angle
    
    double St = Math.sin(O);
    double Ct = Math.cos(O);
    // -----------------------------------------------------------------------------------


    // ----- dV/dt ----- Linear Acceleration ---------------
    Qd[0] = (L*St*m*w*w - 2.0*Ct*St*g*m + 2.0*Fx)/(2.0*(St*St*m + M));
    
    // ----- dw/dt ----- Angular Acceleration --------------
    Qd[1] = -(Ct*L*St*m*w*w - 2.0*M*St*g - 2.0*St*g*m + 2.0*Ct*Fx)/(L*St*St*m + L*M);
 
    // ----- dV/dt ----- Linear Velocity -------------------
  
    Qd[2] = V;
    
    // ----- dV/dt ----- Angular Velocity ------------------
    Qd[3] = w;
  }

} // end class PendEngine

// =======================================================================================
// **                                P E N D E N G I N E                                **
// ======================================================================== END FILE =====
