package org.trncmp.apps.pool;

import java.util.concurrent.CountDownLatch;

public class TestTask implements Runnable {

  protected final TestModel      model;
  protected       TestMember     member  = null;
  protected       CountDownLatch counter = null;
  
  public      TestTask   ( TestModel  M )     { model   = M; }
  public void setMember  ( TestMember M )     { member  = M; }
  public void setCounter ( CountDownLatch C ) { counter = C; }

  public void run() {
    if ( null != member ) {
      model.execute( member );
      counter.countDown();
      System.out.format( "    Completed task: %d\n", member.getID() );
    }
  }

  
} // end class TestTask
