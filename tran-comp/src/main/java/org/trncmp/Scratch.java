package org.trncmp;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class Scratch {


    // ===================================================================================
    static void sequentialMatrixMultiply( double[][] C, double[][] A, double[][] B ) {
	// -------------------------------------------------------------------------------

	int n = A.length;
	int p = A[0].length;
	int m = B[0].length;
	
	for ( int i=0; i<n; i++ ) {
	    for ( int j=0; j<m; j++ ) {
		C[i][j] = 0.0e0;
		for ( int k=0; k<p; k++ ) {
		    C[i][j] += A[i][k] * B[k][j];
		}
	    }
	}
    }

    
    // ===================================================================================
    static void parMatrixMultiply( double[][] C, double[][] A, double[][] B ) {
	// -------------------------------------------------------------------------------

	int n = A.length;
	int p = A[0].length;
	int m = B[0].length;
	
	IntStream.range(0,n).parallel().forEach( i -> {
		IntStream.range(0,m).parallel().forEach( j -> {
			C[i][j] = 0.0e0;
			for ( int k=0; k<p; k++ ) {
			    C[i][j] += A[i][k] * B[k][j];
			}
		    });
	    });
    }

    // ===================================================================================
    private static class forkMulTask implements Callable<Integer> {
	// -------------------------------------------------------------------------------
	private int lo;
	private int hi;
	private int p;
	private int m;
	private double[][] C;
	private double[][] A;
	private double[][] B;

	// ===============================================================================
	forkMulTask( double[][] C, double[][] A, double[][] B,
		     int start_inclusive_row, int end_exclusive_row ) {
	    // ---------------------------------------------------------------------------
	    this.lo   = start_inclusive_row;
	    this.hi   = end_exclusive_row;
	    this.p = A[0].length;
	    this.m = B[0].length;
	    this.C = C;
	    this.A = A;
	    this.B = B;
	}
	
	// ===============================================================================
	@Override
        public Integer call() {
	    // ---------------------------------------------------------------------------
	    for ( int i=this.lo; i<this.hi; i++ ) {
		for ( int j=0; j<m; j++ ) {
		    this.C[i][j] = 0.0e0;
		    for ( int k=0; k<p; k++ ) {
			this.C[i][j] += this.A[i][k] * this.B[k][j];
		    }
		}
	    }
	    return 1;
	}
    }


    

    // ===================================================================================
    static void forkMatrixMultiply( double[][] C, double[][] A, double[][] B,
				    int num_proc ) {
	// -------------------------------------------------------------------------------

	int n  = A.length;
	int dn = n / num_proc;
	int[] index = new int[ num_proc + 1];
	index[0] = 0;
	for ( int i=1; i<num_proc; i++ ) {
	    index[i] = index[i-1] + dn;
	}
	index[ num_proc ] = n;

	// -------------------------------------------------------------------------------

	int sum = 0;
	
	List<forkMulTask> task_list = new ArrayList<forkMulTask>();

	for ( int i=0; i<num_proc; i++ ) {
	    task_list.add( new forkMulTask( C, A, B, index[i], index[i+1] ) );
	}

	List<Future<Integer>> res = ForkJoinPool.commonPool().invokeAll(task_list);

	try {
	    for( Future<Integer> f : res ) {
		sum += f.get();
	    }
	} catch( java.util.concurrent.ExecutionException e ) {
	    System.err.println( e.toString() );
	} catch( java.lang.InterruptedException e ) {
	    System.err.println( e.toString() );
	}

	if ( sum != num_proc ) {
	    System.err.format( "\nOnly %d of %d threads completed.\n\n", sum, num_proc );
	}


    }

    
   
    // ===================================================================================
    static void fill( double[][] A ) {
	// -------------------------------------------------------------------------------
	int n = A.length;
	int m = A[0].length;
	for ( int i=0; i<n; i++ ) {
	    for ( int j=0; j<m; j++ ) {
		A[i][j] = ((double)i)*1.0e-3 + ((double)j)*1.0e-6;
	    }
	}
    }

    // ===================================================================================
    static double sumsq( double[][] A, double[][] B ) {
	// -------------------------------------------------------------------------------
	int n = A.length;
	int m = A[0].length;
	double sum = 0.0e0;
	for ( int i=0; i<n; i++ ) {
	    for ( int j=0; j<m; j++ ) {
		double d = A[i][j] - B[i][j];
		sum += d*d;
	    }
	}
	return sum;
    }

    
    // ===================================================================================
    /** Program entry point.
     * @param args program arguments (unused here)
     */
    // -----------------------------------------------------------------------------------
    public static void main(String[] args) {
	// -------------------------------------------------------------------------------

	if ( args.length != 3 ) {
	    System.err.println( "\nUSAGE: mul n p m");
	    System.err.println( "    C(n,m) = A(n,p) * B(p,m)\n\n");
	    System.exit(2);
	}

	final int N = Integer.parseInt( args[0] );
	final int P = Integer.parseInt( args[1] );
	final int M = Integer.parseInt( args[2] );

	long am_ops = (long)2 * (long)N  * (long)P  * (long)M;

	double[][] Cf = new double[N][M];
	double[][] Cs = new double[N][M];
	double[][] Cp = new double[N][M];
	double[][] A  = new double[N][P];
	double[][] B  = new double[P][M];
	
	fill( A );
	fill( B );

	// -------------------------------------------------------------------------------
	long st = System.nanoTime();

	sequentialMatrixMultiply( Cs, A, B );

	double seq_et = ((double)(System.nanoTime() - st))*1.0e-9;

	double flops = ((double)am_ops) / seq_et;

	System.out.format( "\nSeq: %d add/mul in %f milliseconds (%f Mflops)\n",
			   am_ops, seq_et*1.0e3, flops*1.0e-6 );

	// -------------------------------------------------------------------------------
	
	st = System.nanoTime();

	parMatrixMultiply( Cp, A, B );

	double par_et = ((double)(System.nanoTime() - st))*1.0e-9;

	flops = ((double)am_ops) / par_et;

	System.out.format( "Par: %d add/mul in %f milliseconds (%f Mflops)\n",
			   am_ops, par_et*1.0e3, flops*1.0e-6 );
	// -------------------------------------------------------------------------------

	int num_proc = Runtime.getRuntime().availableProcessors();

	System.out.format( "Num proc = %d\n", num_proc );


	st = System.nanoTime();

	forkMatrixMultiply( Cf, A, B, 16 );

	double fork_et = ((double)(System.nanoTime() - st))*1.0e-9;

	flops = ((double)am_ops) / fork_et;

	System.out.format( "Frk: %d add/mul in %f milliseconds (%f Mflops)\n",
			   am_ops, fork_et*1.0e3, flops*1.0e-6 );

	// -------------------------------------------------------------------------------

	System.out.format( "Speedup s->p = %f\n",   seq_et/par_et );
	System.out.format( "Speedup s->f = %f\n\n", seq_et/fork_et );
	System.out.format( "Speedup p->f = %f\n\n", par_et/fork_et );
	
	System.out.format( "MSE     s->p = %f\n", sumsq( Cs, Cp ) );
	System.out.format( "MSE     s->f = %f\n\n", sumsq( Cs, Cf ) );
	
    }


}
