// ====================================================================== BEGIN FILE =====
// **                                 F I L E T O O L S                                 **
// =======================================================================================
// **                                                                                   **
// **  This file is part of the TRNCMP Research Library. (formerly SolLib)              **
// **                                                                                   **
// **  Copyright (c) 2013, Stephen W. Soliday                                           **
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
 * @file FileTools.java
 *  Provides the interface for a set of file tools.
 *
 * @author Stephen W. Soliday
 * @date 2013-08-21
 */
// =======================================================================================

package org.trncmp.lib;
import java.nio.ByteBuffer;
    import java.io.*;
import org.apache.log4j.*;

// =======================================================================================
public class FileTools {
    // -----------------------------------------------------------------------------------
    private static final Logger logger = LogManager.getRootLogger();

    private static long[] uid = { -1, -1 };
    private static long t_count =  0;

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static String tempName( String prefix, String extension ) {
	// -------------------------------------------------------------------------------
	if ( 0 > uid[0] ) {
	    Dice ent = Dice.getInstance();
	    for ( int i=0; i<7337; i++ ) {
		uid[0] = ent.uniform(268435456);
		uid[1] = ent.uniform(268435456);
	    }
	}	
	String name = String.format( "%s-%X-%X-%X.%s",
				     prefix, uid[0], uid[1], t_count, extension );
	t_count += 1;
	return name;
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static String tempName( String prefix ) {
	// -------------------------------------------------------------------------------
	return FileTools.tempName( prefix, "tmp" );
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static String tempName( ) {
	// -------------------------------------------------------------------------------
	return FileTools.tempName( "TC", "tmp" );
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static boolean dirExists( String dirName ) {
	// -------------------------------------------------------------------------------
	return java.nio.file.Files.exists( java.nio.file
					   .FileSystems
					   .getDefault()
					   .getPath(dirName) );
    }
    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static boolean fileExists( String fileName ) {
	// -------------------------------------------------------------------------------
	return java.nio.file.Files.exists( java.nio.file
					   .FileSystems
					   .getDefault()
					   .getPath(fileName) );
    }
    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static String findFile( String name, String path ) {
	// -------------------------------------------------------------------------------
	String[] SP = path.split(":");
	int      n  = SP.length;

	for (int i=0; i<n; i++) {
	    String test = new String( SP[i] + "/" + name );
	    if ( true == FileTools.fileExists( test ) ) {
		return test;
	    }
	}

	return null;
    }
    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static String findFile( String name ) {
	// -------------------------------------------------------------------------------
	return FileTools.findFile( name, ".:~" );
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static boolean delete( String fileName ) {
	// -------------------------------------------------------------------------------
	java.nio.file.Path tgt_path = java.nio.file
	    .FileSystems
	    .getDefault()
	    .getPath(fileName);

	try {
	    java.nio.file.Files.delete( tgt_path );
	} catch( java.nio.file.NoSuchFileException e ) {
	    logger.error( "Target ["+fileName+"] does not exist: "+e.toString() );
	    return true;
	} catch( java.nio.file.DirectoryNotEmptyException e ) {
	    logger.error( "Target ["+fileName+"] is a non empty Directory: "+e.toString() );
	    return true;
	} catch( java.io.IOException e ) {
	    logger.error( "Other I/O Error occured: "+e.toString() );
	    return true;
	}

	return false;
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static boolean copy( String src, String tgt, boolean clobber ) {
	// -------------------------------------------------------------------------------

	java.nio.file.Path src_path = java.nio.file.FileSystems.getDefault().getPath(src);
	java.nio.file.Path tgt_path = java.nio.file.FileSystems.getDefault().getPath(tgt);

	try {
	    if ( clobber ) {
		java.nio.file.Files.copy( src_path, tgt_path,
					  java.nio.file.StandardCopyOption.REPLACE_EXISTING );
	    } else {
		java.nio.file.Files.copy( src_path, tgt_path );
	    }
	} catch( java.nio.file.FileAlreadyExistsException e ) {
	    logger.error( "Target already exisit and you did not use TRUE: "+e.toString() );
	    return true;
	} catch( java.io.IOException e ) {
	    logger.error( "Other I/O Error occured: "+e.toString() );
	    return true;
	}
	return false;
    }

    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static boolean move( String src, String tgt, boolean clobber ) {
	// -------------------------------------------------------------------------------

	java.nio.file.Path src_path = java.nio.file.FileSystems.getDefault().getPath(src);
	java.nio.file.Path tgt_path = java.nio.file.FileSystems.getDefault().getPath(tgt);

	try {
	    if ( clobber ) {
		java.nio.file.Files.move( src_path, tgt_path,
					  java.nio.file.StandardCopyOption.REPLACE_EXISTING );
	    } else {
		java.nio.file.Files.move( src_path, tgt_path );
	    }
	} catch( java.nio.file.FileAlreadyExistsException e ) {
	    logger.error( "Target already exisit and you did not use TRUE: "+e.toString() );
	    return true;
	} catch( java.io.IOException e ) {
	    logger.error( "Other I/O Error occured: "+e.toString() );
	    return true;
	}
	return false;
    }





    // ===================================================================================
    /** @brief Random source.
     *  @param buffer pointer to the destination for the random bytes.
     *  
     *  interface with /dev/urandom
     *  @note WARNING this is not suitable for large sequences. Use it to seed a PRNG
     */
    // -----------------------------------------------------------------------------------
    public static void urandom( byte[] buffer ) {
	// -------------------------------------------------------------------------------
	int n = buffer.length;

	try {
	    java.io.FileInputStream in = new java.io.FileInputStream("/dev/urandom");
	    in.read( buffer, 0, n );
	    in.close();
	} catch( java.io.FileNotFoundException e1 ) {
	    logger.error( "/dev/urandom not here: are you using M$ Windoz" );
	} catch( java.io.IOException e2 ) {
	    logger.error( "it apears that /dev/urandom is not here: are you using M$ Windoz" );
	}
    }


    // ===================================================================================
    /** @brief Random source.
     *  @param buffer pointer to the destination for the random bytes.
     *  
     *  interface with /dev/urandom
     *  @note WARNING this is not suitable for large sequences. Use it to seed a PRNG
     */
    // -----------------------------------------------------------------------------------
    public static void urandom( short[] data ) {
	// -------------------------------------------------------------------------------
	int n = data.length;

	ByteBuffer buffer = ByteBuffer.allocate(n*2);

	try {
	    java.io.FileInputStream in = new java.io.FileInputStream("/dev/urandom");
	    in.read( buffer.array(), 0, n );
	    in.close();
	    for ( int i=0; i<n; i++ ) { data[i] = buffer.getShort(i); }
	} catch( java.io.FileNotFoundException e1 ) {
	    logger.error( "/dev/urandom not here: are you using M$ Windoz" );
	} catch( java.io.IOException e2 ) {
	    logger.error( "it apears that /dev/urandom is not here: are you using M$ Windoz" );
	}
    }


    // ===================================================================================
    /** @brief Random source.
     *  @param buffer pointer to the destination for the random bytes.
     *  
     *  interface with /dev/urandom
     *  @note WARNING this is not suitable for large sequences. Use it to seed a PRNG
     */
    // -----------------------------------------------------------------------------------
    public static void urandom( int[] data ) {
	// -------------------------------------------------------------------------------
	int n = data.length;

	ByteBuffer buffer = ByteBuffer.allocate(n*4);

	try {
	    java.io.FileInputStream in = new java.io.FileInputStream("/dev/urandom");
	    in.read( buffer.array(), 0, n );
	    in.close();
	    for ( int i=0; i<n; i++ ) { data[i] = buffer.getInt(i); }
	} catch( java.io.FileNotFoundException e1 ) {
	    logger.error( "/dev/urandom not here: are you using M$ Windoz" );
	} catch( java.io.IOException e2 ) {
	    logger.error( "it apears that /dev/urandom is not here: are you using M$ Windoz" );
	}
    }


    // ===================================================================================
    /** @brief Random source.
     *  @param buffer pointer to the destination for the random bytes.
     *  
     *  interface with /dev/urandom
     *  @note WARNING this is not suitable for large sequences. Use it to seed a PRNG
     */
    // -----------------------------------------------------------------------------------
    public static void urandom( long[] data ) {
	// -------------------------------------------------------------------------------
	int n = data.length;

	ByteBuffer buffer = ByteBuffer.allocate(n*8);

	try {
	    java.io.FileInputStream in = new java.io.FileInputStream("/dev/urandom");
	    in.read( buffer.array(), 0, n );
	    in.close();
	    for ( int i=0; i<n; i++ ) { data[i] = buffer.getLong(i); }
	} catch( java.io.FileNotFoundException e1 ) {
	    logger.error( "/dev/urandom not here: are you using M$ Windoz" );
	} catch( java.io.IOException e2 ) {
	    logger.error( "it apears that /dev/urandom is not here: are you using M$ Windoz" );
	}
    }

    // ===================================================================================
    /** @brief Open File for Reading.
     *  @param fspc path to file.
     *  @return reference to a buffered reader. null if failed.
     */
    // -----------------------------------------------------------------------------------
    public static BufferedReader openRead( String fspc ) {
	// -------------------------------------------------------------------------------
	try {
	    InputStream    is = new FileInputStream( new File( fspc ) );
	    BufferedReader br = new BufferedReader( new InputStreamReader(is) );
	    return br;
	}  catch( NullPointerException e ) {
	    logger.error( "Path name can not be null" );
	} catch( FileNotFoundException e ) {
	    logger.error( "Cannot open [" + fspc + "] for reading" );
	} catch( IOException e ) {
	    logger.error( "Cannot close [" + fspc + "] after reading" );
	}
	return null;
    }


    // ===================================================================================
    /** @brief Open File for Writting.
     *  @param fspc path to file.
     *  @return reference to a buffered reader. null if failed.
     */
    // -----------------------------------------------------------------------------------
    public static PrintStream openWrite( String fspc ) {
	// -------------------------------------------------------------------------------
	try {
	    return new PrintStream( new File( fspc ) );
	} catch( NullPointerException e ) {
	    System.err.println( "Path name can not be null" );
	} catch( FileNotFoundException e ) {
	    System.err.println( "Cannot open [" + fspc + "] for writting" );
	}
	return null;
    }


};

// =======================================================================================
// **                                 F I L E T O O L S                                 **
// ======================================================================== END FILE =====
