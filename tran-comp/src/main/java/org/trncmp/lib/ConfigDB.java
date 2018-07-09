// ====================================================================== BEGIN FILE =====
// **                                  C O N F I G D B                                  **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2013, 17, Stephen W. Soliday                                       **
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
 * @file ConfigDB.java
 *  Provides a configuration database.
 *
 * @author Stephen W. Soliday
 * @date 2013-07-06 Original release.
 * @date 2017-06-18 Migration to the Proxima libraries.
 */
// =======================================================================================

package org.trncmp.lib;
import java.io.*;
import java.util.*;
import java.util.regex.*;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class ConfigDB {                                                        // ConfigDB
  // -------------------------------------------------------------------------------------

  public static final int INI_UNKNOWN           = 0;
  public static final int INI_BLANK_LINE        = 1;
  public static final int INI_LINE_COMMENT      = 2;
  public static final int INI_SECTION_NAME      = 3;
  public static final int INI_KEY_VALUE         = 4;
  public static final int INI_KEY_VALUE_COMMENT = 5;

  // =====================================================================================
  /** ConfigDB base Exception.                                                          */
  // -------------------------------------------------------------------------------------
  static public class BaseException extends java.lang.Exception {
    // -----------------------------------------------------------------------------------
    public BaseException() {               super("ConfigDB.BaseException" );          }
    public BaseException(String message) { super("ConfigDB.BaseException: "+message); }
  };

  // =====================================================================================
  /** ConfigDB value error exception.
   *
   *  Custom exception for reporting incompatable values.
   */
  // -------------------------------------------------------------------------------------
  static public class ValueError extends ConfigDB.BaseException {
    // -----------------------------------------------------------------------------------
    public ValueError() {               super("ConfigDB.ValueError" );          }
    public ValueError(String message) { super("ConfigDB.ValueError: "+message); }
  };

  // =====================================================================================
  /** ConfigDB value error exception.
   *
   *  Custom exception for reporting incompatable values.
   */
  // -------------------------------------------------------------------------------------
  static public class MissingElement extends ConfigDB.BaseException {
    // -----------------------------------------------------------------------------------
    public MissingElement() {               super("ConfigDB.MissingElement" );          }
    public MissingElement(String message) { super("ConfigDB.MissingElement: "+message); }
  };

  // =====================================================================================
  /** ConfigDB no such key exception.
   *
   *  Custom exception for reporting general key/value pair exceptions.
   */
  // -------------------------------------------------------------------------------------
  static public class NoSuchKey extends ConfigDB.MissingElement {
    // -----------------------------------------------------------------------------------
    public NoSuchKey() {               super("ConfigDB.NoSuchKey" );          }
    public NoSuchKey(String message) { super("ConfigDB.NoSuchKey: "+message); }
  };

  // =====================================================================================
  /** ConfigDB no such section.
   *
   *  Custom exception for reporting general key/value pair exceptions.
   */
  // -------------------------------------------------------------------------------------
  static public class NoSection extends ConfigDB.MissingElement {
    // -----------------------------------------------------------------------------------
    public NoSection() {               super("ConfigDB.NoSection" );          }
    public NoSection(String message) { super("ConfigDB.NoSection: "+message); }
  };

  // =====================================================================================
  /** ConfigDB no such entry.
   *
   *  Custom exception for reporting general key/value pair exceptions.
   */
  // -------------------------------------------------------------------------------------
  static public class NoEntry extends ConfigDB.MissingElement {
    // -----------------------------------------------------------------------------------
    public NoEntry() {               super("ConfigDB.NoEntry" );          }
    public NoEntry(String message) { super("ConfigDB.NoEntry: "+message); }
  };









  // =====================================================================================
  /** Comments.
   * <p>
   *  Array of comments that can be retrieved by line number.
   */
  // -------------------------------------------------------------------------------------
  static public class Comments {                                      // ConfigDB.Comments
    // -----------------------------------------------------------------------------------
    protected Vector<String> array = null;

    // ===================================================================================
    /** Constructor.
     * <p>
     *  Instantiate an array of comments.
     */
    // -----------------------------------------------------------------------------------
    public Comments( ) {
      // ---------------------------------------------------------------------------------
      array = new Vector<String>(16,16);
    }

    // ===================================================================================
    /** Size.
     * <p>
     *  Provide the number of comments contained in this structure.
     *
     *  @return number of comments.
     */
    // -----------------------------------------------------------------------------------
    public int size( ) {
      // ---------------------------------------------------------------------------------
      return array.size();
    }

    // ===================================================================================
    /** Add.
     * <p>
     *  Add a new comment to the end of this array. Throw a null exception if the
     *  the source comment string is null.
     *
     *  @param comment line to be added.
     */
    // -----------------------------------------------------------------------------------
    public void add( String c ) {
      // ---------------------------------------------------------------------------------
      if ( null == c ) {
        throw new java.lang.NullPointerException("Comment string is null");
      }
      array.add( new String( c ) );
    }

    // ===================================================================================
    /** Get.
     * <p>
     *  Return one comment line based on the provided index. Throw and out of bounds
     *  exception if the index exceeds the number of comments.
     *
     *  @param line number for comment.
     *  @return comment line.
     */
    // -----------------------------------------------------------------------------------
    public String get( int i ) {
      // ---------------------------------------------------------------------------------
      try {
        return array.elementAt( i );
      } catch( java.lang.ArrayIndexOutOfBoundsException e ) {
      }
      return null;
    }
  };








  // =====================================================================================
  static public class Entry {                                           // ConfigDB::Entry
    // -----------------------------------------------------------------------------------
    public String key     = null;
    public String value   = null;
    public String comment = null;

    // ===================================================================================
    /** Constructor.
     * <p>
     * Instantiate an entry.
     *
     * @param k key.
     * @param v value.
     */
    // -----------------------------------------------------------------------------------
    public Entry( String k, String v ) {
      // ---------------------------------------------------------------------------------
      key     = new String( k );
      value   = v;
      comment = "";
    }

    // ===================================================================================
    /** Constructor.
     * <p>
     * Instantiate an entry.
     *
     * @param k key.
     * @param v value.
     * @param c comment.
     */
    // -----------------------------------------------------------------------------------
    public Entry( String k, String v, String c ) {
      // ---------------------------------------------------------------------------------
      key     = new String( k );
      value   = new String( v );
      if ( null == c ) {
        comment = "";
      } else {
        comment = new String( c );
      }
    }



    // ===================================================================================
    /** Entry Iterator.
     * <p>
     *  Iterator for the hashmap to iterate and return the sections
     */
    // -----------------------------------------------------------------------------------
    static public class Iterator {                            // ConfigDB::Entry::Iterator
      // ---------------------------------------------------------------------------------

      protected HashMap< String, Entry >                      sec_map           = null;
      protected java.util.Iterator< Map.Entry<String,Entry> > hash_map_iterator = null;

      // =================================================================================
      /** Constructor.
       * <p>
       * Instantiate an entry iterator.
       *
       * @param sm hash map of Sections.
       */
      // ---------------------------------------------------------------------------------
      public Iterator( HashMap< String, Entry > sm ) {
        // -------------------------------------------------------------------------------
        sec_map = sm;
        rewind();
      }

      // =================================================================================
      /** Has Next.
       * <p>
       * Determine if mor entries exist.
       *
       * @return true if the iterator can retieve more entries.
       */
      // ---------------------------------------------------------------------------------
      public boolean hasNext() {
        // -------------------------------------------------------------------------------
        return hash_map_iterator.hasNext();
      }
	
      // =================================================================================
      /** Next Entry.
       * <p>
       * Get the next entry in the iteration.
       *
       * @return Reference to the next entry.
       * @throws ConfigDB.NoEntry
       */
      // ---------------------------------------------------------------------------------
      public Entry next() throws NoEntry {
        // -------------------------------------------------------------------------------
        try {
          return hash_map_iterator.next().getValue();
        } catch( NoSuchElementException e ) {
          throw new NoEntry( e.getMessage() );
        }
      }

      // =================================================================================
      /** Rewind.
       * <p>
       * Restore the iterations to the begining.
       */
      // ---------------------------------------------------------------------------------
      public void rewind( ) {
        // -------------------------------------------------------------------------------
        hash_map_iterator = sec_map.entrySet().iterator();
      }
    };
  };














  // =====================================================================================
  /** Section.
   * <p>
   *  Individual confiuration section.
   */
  // -------------------------------------------------------------------------------------
  static public class Section {                                        // ConfigDB.Section
    // -----------------------------------------------------------------------------------
    protected String                 sec_name     = null;
    protected HashMap<String, Entry> sec_map      = null;
    protected Comments               sec_comments = null;

    // ===================================================================================
    /**
     * <p>
     *
     */
    // -----------------------------------------------------------------------------------
    public Section( String nm ) {
      // ---------------------------------------------------------------------------------
      sec_name     = new String(nm);
      sec_map      = new HashMap<String, Entry>();
      sec_comments = new Comments();
    }



    // ===================================================================================
    /** Entry iterator.
     * <p>
     * Get an entry iterator.
     */
    // -----------------------------------------------------------------------------------
    public Entry.Iterator iterator() {
      // ---------------------------------------------------------------------------------
      return new Entry.Iterator( sec_map );
    }

    // ===================================================================================
    /** Size.
     * <p>
     * Return the number of entries in the map.
     */
    // -----------------------------------------------------------------------------------
    public int size() {
      // ---------------------------------------------------------------------------------
      return sec_map.size();
    }

    // ===================================================================================
    /** Section name.
     * <p>
     * Each section has a name.
     * @return String containing the name of the Section.
     */
    // -----------------------------------------------------------------------------------
    public String name() {
      // ---------------------------------------------------------------------------------
      return sec_name;
    }

    // ===================================================================================
    /**
     * <p>
     *
     */
    // -----------------------------------------------------------------------------------
    public Comments comments() {
      // ---------------------------------------------------------------------------------
      return sec_comments;
    }

    // ===================================================================================
    /** @brief Check for key.
     *  @param sec_key section key
     *  @return true if the key exists in this section.
     *
     *  Check to see if this section has a key.
     */
    // -----------------------------------------------------------------------------------
    public boolean hasKey( String key ) {
      // ---------------------------------------------------------------------------------
      if ( null == key ) {
        return false;
      }
      return sec_map.containsKey( key );
    }

    // ===================================================================================
    /** Set Value and Comment.
     * <p>
     *  Associate a key with a value and a comment.
     *
     * @param key associate with the value.
     * @param value associate with the key.
     * @param comment associate with the key.
     * @throws java.lang.NullPointerException
     */
    // -----------------------------------------------------------------------------------
    public void set( String key, String value, String comment ) 
        throws java.lang.NullPointerException {
      // ---------------------------------------------------------------------------------
      if ( null == key ) {
        throw new java.lang.NullPointerException("Key string is null");
      }

      sec_map.put( key, new Entry( key, value, comment ) );
    }

    // ===================================================================================
    /** Set Value.
     * <p>
     *  Associate a key with a value.
     *
     * @param key associate with the value.
     * @param value associate with the key.
     * @throws java.lang.NullPointerException
     */
    // -----------------------------------------------------------------------------------
    public void set( String key, String value ) 
        throws java.lang.NullPointerException {
      // ---------------------------------------------------------------------------------
      set( key, value, "" );
    }

    // ===================================================================================
    /** Get Value.
     * <p>
     *  Get a value associated with a key.
     *
     * @param key key to retrieve the associated value.
     * @return string containing the value associated.
     * @throws ConfigDB.NoSuchKey
     * @throws java.lang.NullPointerException
     */
    // -----------------------------------------------------------------------------------
    public String get( String key )
        throws ConfigDB.NoSuchKey, java.lang.NullPointerException {
      // ---------------------------------------------------------------------------------
      if ( null == key ) {
        throw new java.lang.NullPointerException("Key string is null");
      }

      if ( sec_map.containsKey( key ) ) {
        return sec_map.get( key ).value;
      }

      throw new ConfigDB.NoSuchKey( key );
    }

    // ===================================================================================
    /** Get Comment.
     * <p>
     *  Get a section comment.
     *
     * @param key key to retrieve a comment.
     * @return string containing a section comment.
     * @throws ConfigDB.NoSuchKey
     * @throws java.lang.NullPointerException
     */
    // -----------------------------------------------------------------------------------
    public String getComment( String key )
        throws ConfigDB.NoSuchKey, java.lang.NullPointerException {
      // ---------------------------------------------------------------------------------
      if ( null == key ) {
        throw new java.lang.NullPointerException("Key string is null");
      }

      if ( sec_map.containsKey( key ) ) {
        return sec_map.get( key ).comment;
      }

      throw new ConfigDB.NoSuchKey( key );
    }





    // ===================================================================================
    /** @brief Section Iterator
     *  Iterator for the hashmap to iterate and return the sections
     */
    // -----------------------------------------------------------------------------------
    static public class Iterator {
      // ---------------------------------------------------------------------------------

      protected HashMap<String, Section>                        sections          = null;
      protected java.util.Iterator< Map.Entry<String,Section> > hash_map_iterator = null;

      // =================================================================================
      // ---------------------------------------------------------------------------------
      public Iterator( HashMap<String, Section> ss ) {
        // -------------------------------------------------------------------------------
        sections = ss;
        rewind();
      }

      // =================================================================================
      // ---------------------------------------------------------------------------------
      public boolean hasNext() {
        // -------------------------------------------------------------------------------
        return hash_map_iterator.hasNext();
      }
	
      // =================================================================================
      // ---------------------------------------------------------------------------------
      public Section next() throws NoSection {
        // -------------------------------------------------------------------------------
        try {
          return hash_map_iterator.next().getValue();
        } catch( NoSuchElementException e ) {
          throw new NoSection( e.getMessage() );
        }
      }

      // =================================================================================
      // ---------------------------------------------------------------------------------
      public void rewind() {
        // -------------------------------------------------------------------------------
        hash_map_iterator = sections.entrySet().iterator();
      }
    };



  };















  // =====================================================================================
  // CONFIGDB MAIN METHODS                                                        ConfigDB
  // =====================================================================================

  protected HashMap<String, Section> sections      = null;
  protected Comments                 file_comments = null;

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public ConfigDB() {
    // -----------------------------------------------------------------------------------
    sections      = new HashMap<String, Section>();
    file_comments = new Comments();
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public ConfigDB( Section sec ) {
    // -----------------------------------------------------------------------------------
    sections      = new HashMap<String, Section>();
    file_comments = new Comments();
    this.set( sec );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public ConfigDB( String sname ) {
    // -----------------------------------------------------------------------------------
    sections      = new HashMap<String, Section>();
    file_comments = new Comments();
    this.set( sname );
  }



  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public Section.Iterator iterator() {
    // -----------------------------------------------------------------------------------
    return new Section.Iterator( sections );
  }



  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public int size( ) {
    // -----------------------------------------------------------------------------------
    return sections.size();
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public boolean hasSection( String sname ) {
    // -----------------------------------------------------------------------------------
    if ( null == sname ) {
      return false;
    }
    return sections.containsKey( sname );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public boolean hasKey( String sname, String key ) {
    // -----------------------------------------------------------------------------------
    if ( null == sname ) { return false; }
    try {
      return this.get( sname ).hasKey( key );
    } catch ( ConfigDB.NoSection e ) {
    }
    return false;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public Comments comments( ) {
    // -----------------------------------------------------------------------------------
    return file_comments;
  }



  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( Section sec, boolean warn )
      throws java.lang.NullPointerException {
    // -----------------------------------------------------------------------------------
    if ( null == sec ) {
      throw new java.lang.NullPointerException("Section is null");
    }

    if ( hasSection( sec.name() ) ) {
      if ( warn ) {
        System.err.println( "Section " + sec.name() + " already exists" );
      }
    } else {
      sections.put( sec.name(), sec );
    }
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( Section sec )
      throws java.lang.NullPointerException {
    // -----------------------------------------------------------------------------------
    set( sec, true );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public Section set( String sname, boolean warn )
      throws java.lang.NullPointerException {
    // -----------------------------------------------------------------------------------
    if ( null == sname ) {
      throw new java.lang.NullPointerException("Section is null");
    }

    Section ns = null;

    try {
      ns = get( sname );
      if ( warn ) {
        System.err.println( "Section " + sname + " already exists" );
      }
    } catch( ConfigDB.NoSection e ) {
      ns = new Section( sname );
      sections.put( sname, ns );
    }

    return ns;
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public Section  set( String sname )
      throws java.lang.NullPointerException {
    // -----------------------------------------------------------------------------------
    return set( sname, true );
  }



  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public Section get( String sname )
      throws ConfigDB.NoSection, java.lang.NullPointerException {
    // -----------------------------------------------------------------------------------
    if ( null == sname ) {
      throw new java.lang.NullPointerException("Section name is null");
    }

    if ( sections.containsKey( sname ) ) {
      return sections.get( sname );
    }

    throw new ConfigDB.NoSection( sname );
  }








  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public Comments comments( String sname )
      throws ConfigDB.NoSection {
    // -----------------------------------------------------------------------------------
    return get( sname ).comments();
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( String sname, String key, String value, String comment )
      throws java.lang.NullPointerException {
    // -----------------------------------------------------------------------------------
    if ( null == sname ) {
      throw new java.lang.NullPointerException("Section name is null");
    }

    Section S = set( sname, false );

    if ( null == S ) {
      System.err.println( "Further debugging is required" );
    } else {
      S.set( key, value, comment );
    }
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public void set( String sname, String key, String value )
      throws java.lang.NullPointerException {
    // -----------------------------------------------------------------------------------
    set( sname, key, value, "" );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public String get( String sname, String key )
      throws ConfigDB.NoSection, ConfigDB.NoSuchKey, java.lang.NullPointerException {
    // -----------------------------------------------------------------------------------
    return get( sname ).get( key );
  }

  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public String getComment( String sname, String key )
      throws ConfigDB.NoSection, ConfigDB.NoSuchKey, java.lang.NullPointerException {
    // -----------------------------------------------------------------------------------
    return get( sname ).getComment( key );
  }











  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public String processString( String raw ) {
    // -----------------------------------------------------------------------------------
    String a = raw.trim();
    int beginIndex = 0;
    int endIndex   = a.length();
    if ( a.startsWith("'")  ||  a.startsWith("\"") ) { beginIndex = 1; }
    if ( a.endsWith("'")    ||  a.endsWith("\"") )   { endIndex  -= 1; }

    return a.substring( beginIndex, endIndex );
  }



  // =====================================================================================
  public static int ParseLine_INI( String ref, String[] ret_val ) {
    // -----------------------------------------------------------------------------------
    int n,c;
    String test = ref.trim();

    if ( 0 == test.length() )  { return INI_BLANK_LINE; }

    // ------- find a line comment -------------------------------------------------------
    if ( ';' == test.charAt(0) ) {
      ret_val[0] = test.substring( 1 ).trim();
      return INI_LINE_COMMENT;
    }

    // ------- find a section name -------------------------------------------------------
    if (( '[' == test.charAt(0) ) ||
        ( '{' == test.charAt(0) ) ||
        ( '(' == test.charAt(0) ) ) {

      ret_val[0] = StringTool.containedBy( test, "[{(", "]})" ).trim();

      if ( 0 == ret_val[0].length() ) { return INI_UNKNOWN; }

      return INI_SECTION_NAME;
    }

    // ------- find key value pairs ------------------------------------------------------
    int sep = StringTool.find_any( test, "=:" );

    if ( 0 < sep ) {
      // ------- find the key ------------------------------------------------------------
      ret_val[0] = test.substring( 0, sep ).trim();

      if ( 0 == ret_val[0].length() ) { return INI_UNKNOWN; }

      String rhs = test.substring( sep+1 ).trim();

      if ( 0 == rhs.length() ) { return INI_UNKNOWN; }

      if (( '"'  == rhs.charAt(0) ) ||
          ( '\'' == rhs.charAt(0) )) {
        // ------- value is wrapped by quotes --------------------------------------------
        ret_val[1] = StringTool.containedBy( rhs, "'\"", "'\"" );
        n = ret_val[1].length();
        if (0 == n) { return INI_UNKNOWN; }
        c = StringTool.find_any( rhs, ";", n );
        if (0 > c) {
          // ------- no comments ---------------------------------------------------------
          return INI_KEY_VALUE;
        } else {
          // ------- with comments -------------------------------------------------------
          ret_val[2] = rhs.substring( c+1 ).trim();
          return INI_KEY_VALUE_COMMENT;
        }
      } else {
        // ------- value has no quotes ---------------------------------------------------
        c = StringTool.find_any( rhs, ";" );
        if (0 > c) {
          // ------- no comments ---------------------------------------------------------
          ret_val[1] = rhs.trim();

          if (0 == ret_val[1].length()) {
            return INI_UNKNOWN;
          } else {
            return INI_KEY_VALUE;
          }
        } else {
          // ------- with comments -------------------------------------------------------
          ret_val[1] = rhs.substring( 0, c ).trim();
          ret_val[2] = rhs.substring( c+1 ).trim();
          return INI_KEY_VALUE_COMMENT;
        }
      }
    }

    return INI_UNKNOWN;
  }



  // =====================================================================================
  /** @brief Write INI file.
   *  @param ps print stream.
   *
   *  Write an INI file to a print stream.
   */
  // -------------------------------------------------------------------------------------
  public boolean writeINI( java.io.PrintStream os ) {
    // -----------------------------------------------------------------------------------
    ConfigDB.Comments file_cc = this.comments();
    int n_file_cc = file_cc.size();
  
    for (int i=0; i<n_file_cc; i++) {
      os.println( "; " + file_cc.get(i) );
    }

    ConfigDB.Section.Iterator sit = this.iterator();
    while (sit.hasNext()) {
      try {
        ConfigDB.Section sec = sit.next();
        os.println( "" );
        os.println( "[" + sec.name() + "]" );

        ConfigDB.Comments sec_cc = sec.comments();
        int n_sec_cc = sec_cc.size();

        for (int i=0; i<n_sec_cc; i++) {
          os.println( "  ; " + sec_cc.get(i) );
        }

        ConfigDB.Entry.Iterator eit = sec.iterator();
        while (eit.hasNext()) {
          try {
            ConfigDB.Entry ent = eit.next();
            os.print( "  " + ent.key + " = " );
            if (-1 == ent.value.indexOf(' ')) {
              os.print( ent.value );
            } else {
              os.print( "'" + ent.value + "'" );
            }
            if (!ent.comment.equals("")) {
              os.print( " ; " + ent.comment );
            }
            os.println( "" );
          } catch( ConfigDB.NoEntry e ) {
            System.err.println( "*** FIX THIS (1): " + e + " ***" );
          }
        }
      } catch( ConfigDB.NoSection e) {
        System.err.println( "*** FIX THIS (2): " + e + " ***" );
      }
    }
    return false;
  }

  // =====================================================================================
  /** @brief Read INI file.
   *  @param is java input stream
   *
   *  Read a INI file specified by an input stream.
   */
  // -------------------------------------------------------------------------------------
  public boolean readINI( java.io.InputStream is ) {
    // -----------------------------------------------------------------------------------
    BufferedReader br = new BufferedReader( new InputStreamReader(is) );

    String raw = null;
    String[] work = new String[3];

    ConfigDB.Section current_section = null;

    int count = 0;
    try {
      while ( null != (raw = br.readLine()) ) {
        count += 1;
        String line = raw.trim();

        int ltype = ConfigDB.ParseLine_INI( line, work );

        switch( ltype ) {
          // ------- blank lines are okay ------------------------------------------------
          case ConfigDB.INI_BLANK_LINE:
            break;

            // ------- line comment ------------------------------------------------------
          case ConfigDB.INI_LINE_COMMENT:
            if ( null == current_section ) { // ------- file level comment ---------------
              comments().add( work[0] );
            } else {                         // ------- section level comment ------------
              current_section.comments().add( work[0] );
            }
            break;

            // ------- new section -------------------------------------------------------
          case ConfigDB.INI_SECTION_NAME:
            if (hasSection( work[0] )) { // ------- section already exits ----------------
              try {
                current_section = get( work[0] );
              } catch( ConfigDB.NoSection e ) {
                System.err.println( "*** FIX THIS (1) "+e );
              }
            } else {                     // ------- create a new section -----------------
              current_section = new ConfigDB.Section( work[0] );
              set( current_section );
            }
            break;

            // ------- section key/value -------------------------------------------------
          case ConfigDB.INI_KEY_VALUE:
            if ( null == current_section ) {
              System.err.println( "Line "+count+
                                  ": this version does not yet support adding " +
                                  "key=values at a file level (no section)" );
            } else {
              current_section.set( work[0], work[1] );
            }
            break;

            // ------- section key/value/comment -----------------------------------------
          case ConfigDB.INI_KEY_VALUE_COMMENT:
            if ( null == current_section ) {
              System.err.println( "Line "+count+
                                  ": this version does not yet support adding " +
                                  "key=value/comments at a file level (no section)" );
            } else {
              current_section.set( work[0], work[1], work[2] );
            }
            break;

            // ------- non-parsable line -------------------------------------------------
          default:
            System.err.println( "Line "+count+": Error cannot parse{"+line+"}" );
        }
      }
    } catch( IOException e ) {
      System.err.println( "Read error at line "+count );
      return true;
    }

    return false;
  }

  // =====================================================================================
  /** @brief Write USMTF file.
   *  @param ps print stream.
   *
   *  Write an USMTF file to a print stream.
   */
  // -------------------------------------------------------------------------------------
  public boolean writeUSMTF( java.io.PrintStream os ) {
    // -----------------------------------------------------------------------------------
    ConfigDB.Comments file_cc = this.comments();
    int n_file_cc = file_cc.size();
  
    for (int i=0; i<n_file_cc; i++) {
      os.println( "@REM|" + file_cc.get(i) );
    }

    ConfigDB.Section.Iterator sit = this.iterator();
    while (sit.hasNext()) {
      try {
        ConfigDB.Section  sec      = sit.next();
        ConfigDB.Comments sec_cc   = sec.comments();
        int               n_sec_cc = sec_cc.size();

        for (int i=0; i<n_sec_cc; i++) {
          os.println( sec.name() + "|@REM|" + sec_cc.get(i) );
        }

        ConfigDB.Entry.Iterator eit = sec.iterator();
        while (eit.hasNext()) {
          try {
            ConfigDB.Entry ent = eit.next();
            os.print( sec.name() + "|" + ent.key + "|"+ ent.value + "|" );
            if (!ent.comment.equals("")) {
              os.print( ent.comment );
            }
            os.println( "" );
          } catch( ConfigDB.NoEntry e ) {
            System.err.println( "*** FIX THIS (1): " + e + " ***" );
            return true;
          }
        }
      } catch( ConfigDB.NoSection e) {
        System.err.println( "*** FIX THIS (2): " + e + " ***" );
        return true;
      }
    }
    return false;
  }

  // =====================================================================================
  /** @brief Read USMTF file.
   *  @param fspc full file path.
   *
   *  Read a USMTF file specified by a file path.
   */
  // -------------------------------------------------------------------------------------
  public boolean readUSMTF( java.io.InputStream is ) {
    // -----------------------------------------------------------------------------------
    BufferedReader br = new BufferedReader( new InputStreamReader(is) );

    String raw   = null;
    String tag   = null;
    String cmt   = null;
    String snm   = null;
    String scm   = null;
    int    count = 0;
    try {
      while ( null != (raw = br.readLine()) ) {
        count += 1;
        String raw_t = raw.trim()+" ";
        String[] line = raw_t.split("\\|");
        int      n    = line.length;

        switch(n) {
          case 1:  // ------- empty line -------------------------------------------------
            cmt = processString(line[0]);
            if ( ! cmt.equals("") ) {
              System.err.println( "line "+count+": raw=["+raw+"]" );
              System.err.println( "line "+count+
                                  ": Expected blank line: Unknown content" );
            }
            break;
          case 2:  // ------- file comments ----------------------------------------------
            tag = processString(line[0]);
            cmt = processString(line[1]);

            if ( tag.equals("@REM") ) {
              comments().add( cmt );
            } else {
              System.err.println( "line "+count+": tag=["+tag+"], cmt=["+
                                  cmt+"], raw=["+raw+"]" );
              System.err.println( "line "+count+
                                  ": Expected file comment: Missing content" );
            }
            break;
          case 3:  // ------- section comment --------------------------------------------
            snm = processString(line[0]);
            tag = processString(line[1]);
            scm = processString(line[2]);

            if ( tag.equals("@REM") ) {
              if ( ! hasSection(snm)) {
                set( snm );
              }
              try {
                get( snm ).comments().add( scm );
              } catch( NoSection e ) {
                System.err.println( e.toString() );
              }
            } else {
              System.err.println( "line "+count+": sec=["+snm+"], tag=["+
                                  tag+"], cmt=["+scm+"], raw=["+raw+"]" );
              System.err.println( "line "+count+
                                  ": Expected section comment: Missing content" );
            }
            break;
          case 4:  // ------- section key/value/comment ----------------------------------
            String sn = processString(line[0]);
            String ky = processString(line[1]);
            String va = processString(line[2]);
            String cm = processString(line[3]);

            if ( ! sn.equals("") ) {
              if ( ! ky.equals("") ) {
                if ( ! va.equals("") ) {
                  set( sn, ky, va, cm );
                } else {
                  System.err.println( "line "+count+": sec=["+sn+
                                      "], key=["+ky+"], val=[NULL], cmt=["+
                                      cm+"], raw=["+raw+"]" );
                  System.err.println( "line "+count+
                                      ": Expected S/K/V/C: Value may not be null" );
                }
              } else {
                if ( ! hasSection( sn ) ) {
                  set( sn );
                }
              }
            } else {
              System.err.println( "line "+count+
                                  ": sec=[NULL], key=[*], val=[*], cmt=[*], raw=["+raw+"]" );
              System.err.println( "line "+count+
                                  ": Expected S/K/V/C: Section may not be null" );
            }
            break;
          default: // ------- unknown line -----------------------------------------------
            System.err.println( "line "+count+": raw=["+raw+"]" );
            System.err.println( "line "+count+": Unknown error" );
            break;
        }
      }
    } catch( IOException e ) {
      System.err.println( e.toString() );
      return true;
    }

    return false;
  }

  // =====================================================================================
  /** @brief Write JSON file.
   *  @param fspc full file path.
   *
   *  Write a JSON file specified by a file path.
   */
  // -------------------------------------------------------------------------------------
  public boolean writeJSON( java.io.PrintStream ps ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "ConfigDB.writeJSON: Not yet available." );
    return true;
  }

  // =====================================================================================
  /** @brief Read JSON file.
   *  @param fspc full file path.
   *
   *  Read a JSON file specified by a file path.
   */
  // -------------------------------------------------------------------------------------
  public boolean readJSON( java.io.InputStream is ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "ConfigDB.readJSON: Not yet available." );
    return true;
  }

  // =====================================================================================
  /** @brief Write INI file.
   *  @param fspc full file path.
   *
   *  Write an INI file specified by a file path.
   */
  // -------------------------------------------------------------------------------------
  public boolean writeINI( String fspc ) {
    // -----------------------------------------------------------------------------------
    boolean rv = true;
    try {
      PrintStream ps = new PrintStream( new File( fspc ) );
      rv = writeINI( ps );
      ps.close();
    } catch( NullPointerException e ) {
      System.err.println( "Path name can not be null" );
    } catch( FileNotFoundException e ) {
      System.err.println( "Cannot open [" + fspc + "] for writting" );
    }
    return rv;
  }

  // =====================================================================================
  /** @brief Read INI file.
   *  @param fspc full file path.
   *
   *  Read an INI file specified by a file path.
   */
  // -------------------------------------------------------------------------------------
  public boolean readINI( String fspc ) {
    // -----------------------------------------------------------------------------------
    boolean rv = true;
    try {
      InputStream ps = new FileInputStream( new File( fspc ) );
      rv = readINI( ps );
      ps.close();
    } catch( NullPointerException e ) {
      System.err.println( "Path name can not be null" );
    } catch( FileNotFoundException e ) {
      System.err.println( "Cannot open [" + fspc + "] for reading" );
    } catch( IOException e ) {
      System.err.println( "Cannot close [" + fspc + "] after reading" );
    }
    return rv;
  }

  // =====================================================================================
  /** @brief Write USMTF file.
   *  @param fspc full file path.
   *
   *  Write an USMTF file specified by a file path.
   */
  // -------------------------------------------------------------------------------------
  public boolean writeUSMTF( String fspc ) {
    // -----------------------------------------------------------------------------------
    boolean rv = true;
    try {
      PrintStream ps = new PrintStream( new File( fspc ) );
      rv = writeUSMTF( ps );
      ps.close();
    } catch( NullPointerException e ) {
      System.err.println( "Path name can not be null" );
    } catch( FileNotFoundException e ) {
      System.err.println( "Cannot open [" + fspc + "] for writting" );
    }
    return rv;
  }

  // =====================================================================================
  /** @brief Read USMTF file.
   *  @param fspc full file path.
   *
   *  Read a USMTF file specified by a file path.
   */
  // -------------------------------------------------------------------------------------
  public boolean readUSMTF( String fspc ) {
    // -----------------------------------------------------------------------------------
    boolean rv = true;
    try {
      InputStream ps = new FileInputStream( new File( fspc ) );
      rv = readUSMTF( ps );
      ps.close();
    } catch( NullPointerException e ) {
      System.err.println( "Path name can not be null" );
    } catch( FileNotFoundException e ) {
      System.err.println( "Cannot open [" + fspc + "] for reading" );
    } catch( IOException e ) {
      System.err.println( "Cannot close [" + fspc + "] after reading" );
    }
    return rv;
  }

  // =====================================================================================
  /** @brief Write JSON file.
   *  @param fspc full file path.
   *
   *  Write an JSON file specified by a file path.
   */
  // -------------------------------------------------------------------------------------
  public boolean writeJSON( String fspc ) {
    // -----------------------------------------------------------------------------------
    boolean rv = true;
    try {
      PrintStream ps = new PrintStream( new File( fspc ) );
      rv = writeJSON( ps );
      ps.close();
    } catch( NullPointerException e ) {
      System.err.println( "Path name can not be null" );
    } catch( FileNotFoundException e ) {
      System.err.println( "Cannot open [" + fspc + "] for writting" );
    }
    return rv;
  }

  // =====================================================================================
  /** @brief Read JSON file.
   *  @param fspc full file path.
   *
   *  Read an JSON file specified by a file path.
   */
  // -------------------------------------------------------------------------------------
  public boolean readJSON( String fspc ) {
    // -----------------------------------------------------------------------------------
    boolean rv = true;
    try {
      InputStream ps = new FileInputStream( new File( fspc ) );
      rv = readJSON( ps );
      ps.close();
    } catch( NullPointerException e ) {
      System.err.println( "Path name can not be null" );
    } catch( FileNotFoundException e ) {
      System.err.println( "Cannot open [" + fspc + "] for reading" );
    } catch( IOException e ) {
      System.err.println( "Cannot close [" + fspc + "] after reading" );
    }
    return rv;
  }

  // =====================================================================================
  void merge( ConfigDB db ) {
    // -----------------------------------------------------------------------------------
    System.err.println( "ConfigDB::merge() is not yet implemented" );
  }

};

// =======================================================================================
// **                                  C O N F I G D B                                  **
// ======================================================================== END FILE =====
