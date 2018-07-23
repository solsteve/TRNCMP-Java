package org.trncmp.apps.pool;

public class TestModel {

  protected final long delay;

  public TestModel( long d ) {
    delay = d;
  }

  public void execute( TestMember M ) {
    try {
      Thread.sleep( delay );
    } catch( InterruptedException e ) {
      System.out.format( "      thread %d interrupted", M.getID() );
    }
    System.out.format( "    Completed task: %d\n", M.getID() );
  }

} // end class TestTask
