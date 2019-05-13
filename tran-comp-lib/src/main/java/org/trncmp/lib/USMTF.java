// ====================================================================== BEGIN FILE =====
// **                                     U S M T F                                     **
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
 * @brief   USMTF.
 * @file    USMTF.java
 *
 * @details Provides the interface and procedures for US Message Transfer Format.
 *          USMTF is an ASCII formated file where each line is one record of variable
 *          field count. Each field in the record is separated by '|' leading and
 *          trailing spaces in a field are striped.
 *
 * @author  Stephen W. Soliday
 * @date    2017-09-24 Migration to the Proxima libraries.
 */
// =======================================================================================

package org.trncmp.lib;

import java.io.*;

// =======================================================================================
public class USMTF {
  // -----------------------------------------------------------------------------------

  // ===================================================================================
  public static class Record {                                          // USMTF::Record
    // -------------------------------------------------------------------------------
    protected java.util.Vector<String> col = new java.util.Vector<String>();
    
    // ===============================================================================
    /** @brief Constructor.
     *  
     *  Instantiate an empty row.
     */
    // -------------------------------------------------------------------------------
    public Record() {
      // ---------------------------------------------------------------------------
    }
    
    // ===============================================================================
    /** @brief Constructor.
     *  @param n   initial number of fields.
     *  @param src pointer to an array of strings to initialize the fields.
     *  
     *  Instantiate a row composed of the fields in the source string array.
     */
    // -------------------------------------------------------------------------------
    public Record( int n, String[] src ) {
      // ---------------------------------------------------------------------------

      resize( n, src );

    }

	
    // ===============================================================================
    /** @brief Constructor.
     *  @param n   initial number of fields.
     *  
     *  Instantiate a row composed of n empty.
     */
    // -------------------------------------------------------------------------------
    public Record( int n ) {
      // ---------------------------------------------------------------------------

      resize( n );

    }

	
    // ===============================================================================
    /** @brief Constructor.
     *  @param src reference to a parsable string.
     *  @param delim character that delimits the fields.
     *  
     *  Instantiate a row composed of the fields in the parsed source string.
     */
    // -------------------------------------------------------------------------------
    public Record( String src, String delim ) {
      // ---------------------------------------------------------------------------

      resize( src, delim );

    }

	
    // ===============================================================================
    /** @brief Constructor.
     *  @param src reference to a parsable string.
     *  
     *  Instantiate a row composed of the fields in the parsed source string.
     *  The string is split on '|'
     */
    // -------------------------------------------------------------------------------
    public Record( String src ) {
      // ---------------------------------------------------------------------------

      resize( src );

    }

	
    // ===============================================================================
    /** @brief Size.
     *  @return number of current elements in this row.
     */
    // -------------------------------------------------------------------------------
    public int size() {
      // ---------------------------------------------------------------------------

      return col.size();

    }

	
    // ===============================================================================
    /** @brief Reference.
     *  @param c column index
     *  @return reference to the string at index c.
     */
    // -------------------------------------------------------------------------------
    public String field( int c ) {
      // ---------------------------------------------------------------------------

      return col.elementAt(c);

    }

	
    // ===============================================================================
    /** @brief Set.
     *  @param c column index
     *  @param s value
     */
    // -------------------------------------------------------------------------------
    public void set( int c, String s ) {
      // ---------------------------------------------------------------------------

      col.set(c,s);

    }

	
    // ===============================================================================
    /** @brief Add.
     *  @param s value
     */
    // -------------------------------------------------------------------------------
    public void add( String s ) {
      // ---------------------------------------------------------------------------

      col.add(s);

    }

	
    // ===============================================================================
    /** @brief Add.
     *  @param sa array of values
     */
    // -------------------------------------------------------------------------------
    public void add( String[] sa ) {
      // ---------------------------------------------------------------------------

      for ( int i=0; i<sa.length; i++ ) {
        add( sa[i] );
      }
      
    }

	
    // ===============================================================================
    /** @brief Resize (Array of N strings).
     *  @param n   initial number of fields.
     *  @param src pointer to an array of strings to initialize the fields.
     *  
     *  Instantiate a row composed of the fields in the source string array.
     */
    // -------------------------------------------------------------------------------
    public void resize( int n, String[] src ) {
      // ---------------------------------------------------------------------------

      col = new java.util.Vector<String>(n);

      for ( int i=0; i<n; i++ ) {
        col.add( src[i] );
      }

    }

	
    // ===============================================================================
    /** @brief Resize (N empty fields).
     *  @param n   initial number of fields.
     *  
     *  Instantiate a row composed of n empty.
     */
    // -------------------------------------------------------------------------------
    public void resize( int n ) {
      // ---------------------------------------------------------------------------

      col = new java.util.Vector<String>(n);

    }

	
    // ===============================================================================
    /** @brief Resize (Parse).
     *  @param src reference to a parsable string.
     *  @param delim character that delimits the fields.
     *  
     *  Instantiate a row composed of the fields in the parsed source string.
     */
    // -------------------------------------------------------------------------------
    public void resize( String src, String delim ) {
      // ---------------------------------------------------------------------------

      String[] temp = src.split( "\\"+delim );
      int      n    = temp.length;

      resize( n, temp );

    }

	
    // ===============================================================================
    /** @brief Resize (Parse).
     *  @param src reference to a parsable string.
     *  
     *  Instantiate a row composed of the fields in the parsed source string.
     *  The string is split on '|'
     */
    // -------------------------------------------------------------------------------
    public void resize( String src ) {
      // ---------------------------------------------------------------------------

      resize( src, "|" );

    }

	
    // ===============================================================================
    /** @brief To String.
     *  @param delim field delimeter.
     *  @return single string containing all fields in this row separated by delim.
     */
    // -------------------------------------------------------------------------------
    public String toString( String delim ) {
      // ---------------------------------------------------------------------------
      StringBuffer buffer = new StringBuffer();

      int n = size();

      buffer.append( col.elementAt( 0 ) );

      for ( int i=1; i<n; i++ ) {
        buffer.append( delim + col.elementAt( i ) );
      }

      return buffer.toString();
    }


    // ===============================================================================
    /** @brief To String.
     *  @return single string containing all fields in this row separated by "|"
     */
    // -------------------------------------------------------------------------------
    public String toString() {
      // ---------------------------------------------------------------------------
      return toString( "|" );
    }

  }

  
  protected java.util.Vector<USMTF.Record> row = new java.util.Vector<USMTF.Record>();

  // ===================================================================================
  /** @brief Constructor.
   *  
   *  Instantiate an empty database.
   */
  // -----------------------------------------------------------------------------------
  public USMTF() {
    // -------------------------------------------------------------------------------
  }

    
  // ===================================================================================
  /** @brief Read.
   *  @param is java input stream
   *  @return true if error occurs.
   */
  // -----------------------------------------------------------------------------------
  public boolean read( BufferedReader br, String delim ) {
    // -------------------------------------------------------------------------------
    int     count = 0;
    boolean RV    = false;
    String  raw   = null;
    try {
      while ( null != (raw = br.readLine()) ) {
        if ( -1 == raw.indexOf('#') ) {
          count += 1;
          add( raw.trim(), delim );
        }
      }
    } catch( IOException e ) {
      System.err.println( "Read error at line "+count );
      RV = true;
    }

    return RV;
  }

    
  // ===================================================================================
  /** @brief Write.
   *  @param fspc  full path to the USMTF file.
   */
  // -----------------------------------------------------------------------------------
  public  boolean write( java.io.PrintStream os, String delim ) {
    // -------------------------------------------------------------------------------
    int     n     = size();
    boolean RV    = false;
	
    for ( int i=0; i<n; i++ ) {
      os.println( record(i).toString( delim ) );
    }
	
    return RV;
  }

  // ===================================================================================
  /** @brief Read.
   *  @param fspc full path to the USMTF file.
   *  @return true is error occurs.
   */
  // -----------------------------------------------------------------------------------
  public boolean read( String fspc, String delim ) {
    // -------------------------------------------------------------------------------
    boolean rv = true;
    BufferedReader br = FileTools.openRead( fspc );
    if ( null != br ) {
      rv = read( br, delim );
    }
    return rv;
  }


  // ===================================================================================
  /** @brief Write.
   *  @param fspc full path to the USMTF file.
   *  @return true is error occurs.
   */
  // -----------------------------------------------------------------------------------
  public boolean write( String fspc, String delim ) {
    // -------------------------------------------------------------------------------
    boolean rv = true;
    PrintStream ps = FileTools.openWrite( fspc );
    if ( null != ps ) {
      rv = write( ps, delim );
      ps.close();
    }
    return rv;
  }


  // ===================================================================================
  /** @brief Size.
   *  @return number of records in the database.
   */
  // -----------------------------------------------------------------------------------
  public int size() {
    // -------------------------------------------------------------------------------
    return row.size();
  }

  // ===================================================================================
  /** @brief Size.
   *  @param i index of the record
   *  @return number of fields in the record indexed by i.
   */
  // -----------------------------------------------------------------------------------
  public int size( int i ) {
    // -------------------------------------------------------------------------------
    return record(i).size();
  }

  // ===================================================================================
  /** @brief Add
   *  @param rec pointer to a record.
   *
   *  Append a new record to the database.
   */
  // -----------------------------------------------------------------------------------
  public void add( USMTF.Record rec ) {
    // -------------------------------------------------------------------------------
    row.add( rec );
  }

  // ===================================================================================
  /** @brief Add
   *  @param src   reference to a parsable source string.
   *  @param delim character that delimits the fields. (default: '|' )
   *
   *  Append a new record to the database.
   */
  // -----------------------------------------------------------------------------------
  public void add( String src, String delim ) {
    // -------------------------------------------------------------------------------
    add( new USMTF.Record( src, delim ) );
  }

  // ===================================================================================
  /** @brief Add
   *  @param src   reference to a parsable source string.
   *
   *  Append a new record to the database.
   */
  // -----------------------------------------------------------------------------------
  public void add( String src ) {
    // -------------------------------------------------------------------------------
    add( new USMTF.Record( src, "|" ) );
  }

  // ===================================================================================
  /** @brief Record.
   *  @param i record index.
   *  @return pointer to the record at index i.
   */
  // -----------------------------------------------------------------------------------
  public USMTF.Record record( int i ) {
    // -------------------------------------------------------------------------------
    return row.elementAt( i );
  }

  // ===================================================================================
  /** @brief Reference.
   *  @param r record index.
   *  @param c field index.
   *  @return reference to the string at record r and field c.
   */
  // -----------------------------------------------------------------------------------
  public String ref( int r, int c ) {
    // -------------------------------------------------------------------------------
    return record(r).field(c);
  }

}


// =======================================================================================
// **                                     U S M T F                                     **
// ======================================================================== END FILE =====
