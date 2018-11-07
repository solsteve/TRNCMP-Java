// ====================================================================== BEGIN FILE =====
// **                                     T A B L E                                     **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2017, Stephen W. Soliday                                           **
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
 * @brief   Table.
 * @file    Table.java
 *
 * @details Provides the interface and procedures for a Table Container with 
 *          rows and columns.
 *
 * @author  Stephen W. Soliday
 * @date    2017-09-25
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// =======================================================================================
public class Table {
    // -----------------------------------------------------------------------------------

    private static final Logger logger = LogManager.getLogger();
    
    protected double[][] buffer = null;
    protected int        n_row = 0;
    protected int        n_col = 0;
    
    // ===================================================================================
    /** @brief Get.
     *  @param r row index.
     *  @param c column index.
     *  @return the value at (r,c).
     */
    // -----------------------------------------------------------------------------------
    public double get( int r, int c ) {
	// -------------------------------------------------------------------------------
	try {
	    return this.buffer[r][c];
	} catch( java.lang.ArrayIndexOutOfBoundsException e ) {
	    if ( null != this.buffer ) {
		System.err.printf("\nBAD: get(%d,%d) on array(%d,%d) n_row=%d n_col=%d)\n\n",
				  r, c, this.buffer.length, this.buffer[0].length,
				  this.n_row, this.n_col );
	    } else {
		System.err.printf("\nBAD: get(%d,%d) on array(NULL) n_row=%d n_col=%d)\n\n",
				  r, c, this.n_row, this.n_col );
	    }
	    e.printStackTrace(System.err);
	    System.exit(1);
	}
	return 0.0;
    }


    // ===================================================================================
    /** @brief Set.
     *  @param r   row index.
     *  @param c   column index.
     *  @param val value to set.
     *  @return the value at (r,c).
     */
    // -----------------------------------------------------------------------------------
    public double set( int r, int c, double val ) {
	// -------------------------------------------------------------------------------
	try {
	    return ( this.buffer[r][c] = val );
	} catch( java.lang.ArrayIndexOutOfBoundsException e ) {
	    if ( null != this.buffer ) {
		System.err.printf("\nBAD: set(%d,%d,%g) on array(%d,%d) n_row=%d n_col=%d)\n\n",
				  r, c, val, this.buffer.length, this.buffer[0].length,
				  this.n_row, this.n_col );
	    } else {
		System.err.printf("\nBAD: set(%d,%d,%g) on array(NULL) n_row=%d n_col=%d)\n\n",
				  r, c, val, this.n_row, this.n_col );
	    }
	    e.printStackTrace(System.err);
	    System.exit(1);
	}
	return 0.0;
    }


    // ===================================================================================
    /** @brief Number of Rows.
     *  @return the number of rows.
     */
    // -----------------------------------------------------------------------------------
    public int rows( ) {
	// -------------------------------------------------------------------------------
	return n_row;
    }


    // ===================================================================================
    /** @brief Number of Columns.
     *  @return the number of columns.
     */
    // -----------------------------------------------------------------------------------
    public int cols( ) {
	// -------------------------------------------------------------------------------
	return n_col;
    }

    
    // ===================================================================================
    /** @brief Constructor.
     *
     *  Create an empty allocation.
     */
    // -----------------------------------------------------------------------------------
    public Table() {
	// -------------------------------------------------------------------------------
    }


    // ===================================================================================
    /** @brief Constructor.
     *  @param nr number of rows.
     *  @param nc number of cols.
     *
     *  Create a proper allocation.
     */
    // -----------------------------------------------------------------------------------
    public Table( int nr, int nc ) {
	// -------------------------------------------------------------------------------
	this.resize( nr, nc);
    }


    // ===================================================================================
    /** @brief Sub Copy Contructor.
     *  @param src reference to the parent Table.
     */
    // -----------------------------------------------------------------------------------
    public Table( Table src ) {
	// -------------------------------------------------------------------------------
	this.copy( src );
    }

    // ===================================================================================
    /** @brief Sub Copy Contructor.
     *  @param src reference to the parent Table.
     *  @param rzero starting row from the parent.
     *  @param czero starting column from the parent.
     *  @param nr    number of rows.
     *  @param nc    number of columns.
     */
    // -----------------------------------------------------------------------------------
    public Table( Table src, int rzero, int czero, int nr, int nc ) {
	// -------------------------------------------------------------------------------
	this.copy( src, rzero, czero, nr, nc );
    }


    // ===================================================================================
    /** @brief Resize.
     *  @param nr number of rows.
     *  @param nc number of columns.
     *  @return true if this allocation is changed.
     *
     *  Allocate new memory.
     */
    // -----------------------------------------------------------------------------------
    public boolean resize( int nr, int nc ) {
	// -------------------------------------------------------------------------------
	boolean RV = false;
	
	if ( ( nr != this.n_row ) || ( nc != this.n_col ) ) {
	    this.buffer = new double[nr][nc];
	    this.n_row = nr;
	    this.n_col = nc;
	    RV = true;
	}

	return RV;
    }


    // ===================================================================================
    /** @brief deallocate.
     *  @return true if allocation was freed.
     */
    // -----------------------------------------------------------------------------------
    public boolean destroy( ) {
	// -------------------------------------------------------------------------------
	boolean RV = false;

	if ( null != this.buffer ) {
	    this.buffer = null;
	    RV = true;
	}

	this.n_row = 0;
	this.n_col = 0;
	    
	return RV;
    }


    // ===================================================================================
    /** @brief Copy.
     *  @param src reference to the source table.
     *  @return true if allocation changed.
     *
     *  This call may result in a reallocation of this Table.
     */
    // -----------------------------------------------------------------------------------
    public boolean copy( Table src ) {
	// -------------------------------------------------------------------------------

	int nr = src.rows();
	int nc = src.cols();

	boolean RV = this.resize(nr,nc);

	for ( int r=0; r<nr; r++ ) {
	    for ( int c=0; c<nc; c++ ) {
		this.set(r, c, src.get(r,c) );
	    }
	}
	
	return RV;
    }


    // ===================================================================================
    /** @brief Copy.
     *  @param src reference to the source table.
     *  @param rzero starting row from the parent.
     *  @param czero starting column from the parent.
     *  @param nr    number of rows.
     *  @param nc    number of columns.
     *  @return true if allocation changed.
     *
     *  This call will result in a reallocation of this Table.
     */
    // -----------------------------------------------------------------------------------
    public boolean copy( Table src, int rzero, int czero, int nr, int nc ) {
	// -------------------------------------------------------------------------------

	boolean RV = this.resize(nr, nc);

	for ( int r=0; r<nr; r++ ) {
	    for ( int c=0; c<nc; c++ ) {
		this.set(r, c, src.get(rzero+r,czero+c) );
	    }
	}
	
	return RV;
    }


    // ===================================================================================
    /** @brief Set all.
     *  @param v value.
     *
     *  Set all the active elements with v.
     */
    // -----------------------------------------------------------------------------------
    public void set_all( double v ) {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<this.n_row; r++ ) {
	    for ( int c=0; c<this.n_col; c++ ) {
		this.set(r, c, v);
	    }
	}
    }


    // ===================================================================================
    /** @brief Set all.
     *
     *  Set all the active elements with 0.
     */
    // -----------------------------------------------------------------------------------
    public void set_all() {
	// -------------------------------------------------------------------------------
	for ( int r=0; r<this.n_row; r++ ) {
	    for ( int c=0; c<this.n_col; c++ ) {
		this.set(r, c, 0.0e0);
	    }
	}
    }

    
    // ===================================================================================
    /** @brief Row.
     *  @param r row index.
     *  @return row address.
     */
    // -----------------------------------------------------------------------------------
    public double[] row( int r ) {
	// -------------------------------------------------------------------------------
	return buffer[r];
    }

    
    // ===================================================================================
    /** @brief Transpose.
     *  @param src reference to the source table.
     *  @return true if allocation was changed.
     *
     *  Transpose this table inplace.
     */
    // -----------------------------------------------------------------------------------
    public boolean transpose( Table src ) {
	// -------------------------------------------------------------------------------
	int nr = src.rows();
	int nc = src.cols();

	boolean RV = this.resize(nc,nr);

	for ( int r=0; r<nr; r++ ) {
	    for ( int c=0; c<nc; c++ ) {
		this.set(c, r, src.get(r,c) );
	    }
	}
	
	return RV;
    }








	
    // ===================================================================================
    /** @breif Sum Squares.
     *  @return sum of the squares of the elements.
     *
     *  Compute the sum of the squares of the elements of a Tables.
     *  SUM( (A[i,j] * A[i,j]), 0, n-1 )
     */
    // -----------------------------------------------------------------------------------
    public double sumsq() {
	// -------------------------------------------------------------------------------
	double sum = 0.0e0;
	for ( int r=0; r<this.n_row; r++ ) {
	    for ( int c=0; c<this.n_col; c++ ) {
		double x = this.get(r,c);
		sum += ( x * x );
	    }
	}
	return sum;
    }

    
    // ===================================================================================
    /** @breif Sum Squares.
     *  @param rhs pointer to the second Table.
     *  @return sum of the squares of the difference.
     *
     *  Compute the sum of the squares of the difference of the elements of two Tables.
     *  SUM( (A[i,j] - B[i,j])**2, 0, n-1 )
     */
    // -----------------------------------------------------------------------------------
    public double sumsq( Table rhs ) {
	// -------------------------------------------------------------------------------
	double sum = 0.0e0;
	for ( int r=0; r<this.n_row; r++ ) {
	    for ( int c=0; c<this.n_col; c++ ) {
		double x = this.get(r,c) - rhs.get(r,c);
		sum += ( x * x );
	    }
	}
	return sum;
    }








    // ===================================================================================
    /** @brief Read.
     *  @param is  java input stream.
     *  @param tab reference to a table.
     *  @return true if error occurs.
     *
     *  Read the contents of this container from a file. 
     */
    // -----------------------------------------------------------------------------------
    public static boolean read_ascii( java.io.InputStream is, Table tab ) {
	// -------------------------------------------------------------------------------
	boolean RV = true;
	
	Scanner scan = new Scanner( is );

	int nr = scan.nextInt();
	int nc = scan.nextInt();

	tab.resize( nr, nc );

	int count = 0;

	try {
	    for ( int r=0; r<nr; r++ ) {
		count += 1;
		for ( int c=0; c<nc; c++ ) {
		    double x = scan.nextDouble();
		    tab.set( r, c, x );
		}
	    }
	    RV = false;
	} catch( InputMismatchException e1 ) {
	    logger.error( "Table.read: value error on line: "+count );
	} catch( NoSuchElementException e2 ) {
	    logger.error( "Table.read: read past end on line: "+count );
	} catch( IllegalStateException  e3 ) {
	    logger.error( "Table.read: steram closed on line: "+count );
	}
	
	return RV;
    }

    // ===================================================================================
    /** @brief Read.
     *  @param is       java input stream.
     *  @param lefttab  reference to a table.
     *  @param righttab reference to a table.
     *  @return true if error occurs.
     *
     *  Read the contents of this container from a file. 
     */
    // -----------------------------------------------------------------------------------
    public static boolean read_ascii( java.io.InputStream is,
				      Table lefttab, Table righttab ) {
	// -------------------------------------------------------------------------------
	boolean RV = true;
	
	Scanner scan = new Scanner( is );

	int nr  = scan.nextInt();
	int nc1 = scan.nextInt();
	int nc2 = scan.nextInt();

	lefttab.resize( nr, nc1 );
	righttab.resize( nr, nc2 );

	int count = 0;

	try {
	    for ( int r=0; r<nr; r++ ) {
		count += 1;
		for ( int c=0; c<nc1; c++ ) {
		    double x = scan.nextDouble();
		    lefttab.set( r, c, x );
		}
		for ( int c=0; c<nc2; c++ ) {
		    double x = scan.nextDouble();
		    righttab.set( r, c, x );
		}
	    }
	    RV = false;
	} catch( InputMismatchException e1 ) {
	    logger.error( "Table.read: value error on line: "+count );
	} catch( NoSuchElementException e2 ) {
	    logger.error( "Table.read: read past end on line: "+count );
	} catch( IllegalStateException  e3 ) {
	    logger.error( "Table.read: steram closed on line: "+count );
	}

	return RV;
    }
    

    // ===================================================================================
    /** @brief Write.
     *  @param os  java print output stream.
     *  @param tab reference to a table.
     *  @param fmt format for output.
     *  @return true if an error occurs.
     *
     *  Write the contents of a Table to a file.
     */
    // -----------------------------------------------------------------------------------
    public static boolean write_ascii( java.io.PrintStream os,
				       Table tab, String fmt ) {
	// -------------------------------------------------------------------------------
	int nr = tab.rows();
	int nc = tab.cols();

	os.printf( "%d %d\n", nr, nc );

	for ( int r=0; r<nr; r++ ) {
	    os.printf( fmt, tab.get(r,0) );
	    for ( int c=1; c<nc; c++ ) {
		os.printf( " " );
		os.printf( fmt, tab.get(r,c) );
	    }
	    os.printf( "\n" );		
	}
	
	return false;
    }

    
    // ===================================================================================
    /** @brief Write.
     *  @param os       java print output stream.
     *  @param lefttab  reference to a table.
     *  @param righttab reference to a table.
     *  @param fmt      format for output.
     *  @return true if an error occurs.
     *
     *  Write the contents of a table pair to a file.
     */
    // -----------------------------------------------------------------------------------
    public static boolean write_ascii( java.io.PrintStream os,
				       Table lefttab, Table righttab, String fmt ) {
	// -------------------------------------------------------------------------------
	int nr  = lefttab.rows();
	int nc1 = lefttab.cols();
	int nc2 = righttab.cols();

	os.printf( "%d %d %d\n", nr, nc1, nc2 );

	for ( int r=0; r<nr; r++ ) {
	    for ( int c=0; c<nc1; c++ ) {
		os.printf( fmt, lefttab.get(r,c) );
		os.printf( " " );
	    }
	    for ( int c=0; c<nc2; c++ ) {
		os.printf( " " );
		os.printf( fmt, righttab.get(r,c) );
	    }
	    os.printf( "\n" );		
	}
	
	return false;
    }








    // ===================================================================================
    /** @brief Read.
     *  @param tab  reference to a Table.
     *  @param fspc full path to the USMTF file.
     *  @return true is error occurs.
     *
     *  Read a Table from a file.
     */
    // -----------------------------------------------------------------------------------
    public static boolean read_ascii( Table tab, String fspc ) {
	// -------------------------------------------------------------------------------
	boolean RV = true;
	try {
	    InputStream is = new FileInputStream( new File( fspc ) );
	    RV = read_ascii( is, tab );
	    is.close();
	} catch( NullPointerException e ) {
	    System.err.println( "Path name can not be null" );
	} catch( FileNotFoundException e ) {
	    System.err.println( "Cannot open [" + fspc + "] for reading" );
	} catch( IOException e ) {
	    System.err.println( "Cannot close [" + fspc + "] after reading" );
	}
	return RV;
    }


    // ===================================================================================
    /** @brief Read.
     *  @param lefttab  reference to a table.
     *  @param righttab reference to a table.
     *  @param fspc full path to the USMTF file.
     *  @return true is error occurs.
     *
     *  Read a pair of Tables from a file.
     */
    // -----------------------------------------------------------------------------------
    public static boolean read_ascii( Table lefttab, Table righttab, String fspc ) {
	// -------------------------------------------------------------------------------
	boolean RV = true;
	try {
	    InputStream is = new FileInputStream( new File( fspc ) );
	    RV = Table.read_ascii( is, lefttab, righttab );
	    is.close();
	} catch( NullPointerException e ) {
	    System.err.println( "Path name can not be null" );
	} catch( FileNotFoundException e ) {
	    System.err.println( "Cannot open [" + fspc + "] for reading" );
	} catch( IOException e ) {
	    System.err.println( "Cannot close [" + fspc + "] after reading" );
	}
	return RV;
    }




    // ===================================================================================
    /** @brief Write.
     *  @param tab  reference to a table.
     *  @param fmt  format for output.
     *  @param fspc path to the file.
     *  @return true if an error occurs.
     *
     *  Write the contents of a Table to a file.
     */
    // -----------------------------------------------------------------------------------
    public static boolean write_ascii( Table tab, String fspc, String fmt ) {
	// -------------------------------------------------------------------------------
	boolean RV = true;
	try {
	    PrintStream ps = new PrintStream( new File( fspc ) );
	    RV = Table.write_ascii( ps, tab, fmt );
	    ps.close();
	} catch( NullPointerException e ) {
	    System.err.println( "Path name can not be null" );
	} catch( FileNotFoundException e ) {
	    System.err.println( "Cannot open [" + fspc + "] for writting" );
	}
	return RV;
    }


    // ===================================================================================
    /** @brief Write.
     *  @param lefttab  reference to a table.
     *  @param righttab reference to a table.
     *  @param fmt      format for output.
     *  @param fspc path to the file.
     *  @return true if an error occurs.
     *
     *  Write the contents of a table pair to a file.
     */
    // -----------------------------------------------------------------------------------
    public static boolean write_ascii( Table lefttab, Table righttab, String fspc, String fmt ) {
	// -------------------------------------------------------------------------------
	boolean RV = true;
	try {
	    PrintStream ps = new PrintStream( new File( fspc ) );
	    RV = Table.write_ascii( ps, lefttab, righttab, fmt );
	    ps.close();
	} catch( NullPointerException e ) {
	    System.err.println( "Path name can not be null" );
	} catch( FileNotFoundException e ) {
	    System.err.println( "Cannot open [" + fspc + "] for writting" );
	}
	return RV;
    }


    // ===================================================================================
    /** @brief Write.
     *  @param tab  reference to a table.
     *  @param fmt  format for output.
     *  @param fspc path to the file.
     *  @return true if an error occurs.
     *
     *  Write the contents of a Table to a file.
     */
    // -----------------------------------------------------------------------------------
    public static boolean write_ascii( Table tab, String fspc ) {
	// -------------------------------------------------------------------------------
	return Table.write_ascii( tab, fspc, "%19.12e" );
    }

    
    // ===================================================================================
    /** @brief Write.
     *  @param lefttab  reference to a table.
     *  @param righttab reference to a table.
     *  @param fmt      format for output.
     *  @param fspc path to the file.
     *  @return true if an error occurs.
     *
     *  Write the contents of a table pair to a file.
     */
    // -----------------------------------------------------------------------------------
    public static boolean write_ascii( Table lefttab, Table righttab, String fspc ) {
	// -------------------------------------------------------------------------------
	return Table.write_ascii( lefttab, righttab, fspc, "%19.12e" );
    }

    
}


// =======================================================================================
// **                                     T A B L E                                     **
// ======================================================================== END FILE =====
