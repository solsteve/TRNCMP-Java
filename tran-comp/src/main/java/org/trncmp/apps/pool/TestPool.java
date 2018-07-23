package org.trncmp.apps.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;

public class TestPool {
  static final int  NUM_TASKS   = 64;
  static final int  NUM_THREADS = 16;
  static final long TASK_LOAD   = 2000;

  protected final ExecutorService pool;
  protected TestTask[] tasks = null;
  protected int        nTask = 0;

  public TestPool( final TestModel m, final int n ) {
    nTask = n;
    tasks = new TestTask[ nTask ];
    for ( int i=0; i<nTask; i++ ) {
      tasks[i] = new TestTask( m );
    }
    pool = Executors.newFixedThreadPool( NUM_THREADS );
  }


  public void execute( TestMember[] members ) {

    CountDownLatch counter = new CountDownLatch(NUM_TASKS);
    
    System.out.format( "\nStart Loop\n\n" );
    for ( int i=0; i<nTask; i++ ) {
      System.out.format( "  Requesting execution of task: %d\n",members[i].getID() );
      tasks[i].setMember(members[i]);
      tasks[i].setCounter(counter);
      pool.execute( tasks[i] );
    }
    System.out.format( "\nEnd Loop\n\n" );

    //System.out.format( "\nShutdown pool\n\n" );
    //pool.shutdown(); 

    System.out.format( "\nWait for tasks\n\n" );
    //while( ! pool.isTerminated() ) {}
    try {
      counter.await();
      System.out.format( "\nAll tasks completed\n\n" );
    } catch( InterruptedException e ) {
      System.err.println( e.toString() );
    }
  }

  public void close() { pool.shutdown(); }


  

  public static void main( String[] args ) {

    TestMember[] pop1 = new TestMember[NUM_TASKS];
    TestMember[] pop2 = new TestMember[NUM_TASKS];
    
    TestModel model = new TestModel(TASK_LOAD);
    
    for ( int i=0; i<NUM_TASKS; i++ ) {
      pop1[i] = new TestMember( 1000+i );
    }

    for ( int i=0; i<NUM_TASKS; i++ ) {
      pop2[i] = new TestMember( 2000+i );
    }

    TestPool test = new TestPool( model, NUM_TASKS );

    
    System.out.format( "\n** START GEN 1\n\n" );
    test.execute( pop1 );
    System.out.format( "\n** END   GEN 1\n\n" );
    
    System.out.format( "\n** START GEN 2\n\n" );
    test.execute( pop2 );
    System.out.format( "\n** END   GEN 2\n\n" );
    
    test.close();
  }
}
