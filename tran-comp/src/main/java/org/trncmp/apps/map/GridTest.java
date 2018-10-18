package org.trncmp.apps.map;

import java.awt.EventQueue;

import org.trncmp.lib.Dice;

import org.trncmp.lib.inter.GridDisplay;

public class GridTest {

  static Dice dd = Dice.getInstance();
  
  static void randomize( GridDisplay gd ) {
    int nr = gd.nRow();
    int nc = gd.nCol();
    for ( int r=0; r<nr; r++ ) {
      for ( int c=0; c<nc; c++ ) {
        int t = ((0.5 < dd.uniform()) ? (0) : (1));
        gd.set( r, c, t );
      }
    }
  }


  static class Checkers implements GridDisplay.EventListener {

    GridDisplay grid = null;
    
    public Checkers( GridDisplay g ) {
      grid = g;
      grid.addEventListener( this );
    }

    public void mouseClicked( int r, int c, int button ) {      
      System.out.format( "Mouse(%d,%d) = %d\n", r, c, button );
    }
    
    public void keyPressed( int key ) {
      System.out.format( "Key = %d\n", key );
    }

  }
  


  public static void main( String[] args ) {

    dd.seed_set();

    EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {

          GridDisplay grid = new GridDisplay.Builder( 10, 10 )
              .width(640)
              .height(480)
              .title("Test")
              .build();

          randomize( grid );

          Checkers C = new Checkers( grid );

          grid.setVisible(true);
        }
      });
  }

} // end class MapTest
