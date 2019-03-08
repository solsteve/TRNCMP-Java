// ====================================================================== BEGIN FILE =====
// **                                 P E N D P A N E L                                 **
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
 * @brief   Panel for the 
 * @file    PendPanel.java
 *
 * @details Provides the interface and procedures for 
 *
 * @author  Stephen W. Soliday
 * @date    2019-01-01
 */
// =======================================================================================

package org.trncmp.apps.pend;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import java.awt.Dimension;

// =======================================================================================
public class PendPanel extends JPanel {
  // -------------------------------------------------------------------------------------

  protected PendEngine engine = null;

    // =====================================================================================
  public PendPanel( PendEngine eng ) {
    // -----------------------------------------------------------------------------------
    engine = eng;
    setPreferredSize( new Dimension( 512, 512 ) );
    setMinimumSize(   new Dimension( 512, 512 ) );

  }

  public void update() { repaint(); }

  // =====================================================================================
  /** @brief Paint Component
   *  @param g reference to a graphics contect.
   *
   *  Render the grid.
   */
  // -------------------------------------------------------------------------------------
  @Override
  public void paintComponent( Graphics g ) {
    // -----------------------------------------------------------------------------------
    super.paintComponent(g);
    engine.draw( (Graphics2D) g, getWidth(), getHeight() );
  }


} // end class PendPanel


// =======================================================================================
// **                                 P E N D P A N E L                                 **
// ======================================================================== END FILE =====
