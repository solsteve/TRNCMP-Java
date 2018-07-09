// ====================================================================== BEGIN FILE =====
// **                                A P P O P T I O N S                                **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2015, 17, Stephen W. Soliday                                       **
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
 * @file AppOptions.java
 *  Provides the interface for creating a ConfigDB from a combination of default values
 *  configuration file and command line options.
 *  Precedence is:   CommandLine <-- ConfigFile <-- DefaultValues
 *
 * @author Stephen W. Soliday
 * @date 2015-06-03 Original release.
 * @date 2017-06-18 Migration to the Proxima libraries.
 */
// =======================================================================================

package org.trncmp.lib;
import java.io.*;
import java.util.*;
import org.apache.log4j.*;

// =======================================================================================
// ---------------------------------------------------------------------------------------
public class AppOptions {                                                    // AppOptions
    // -----------------------------------------------------------------------------------
    private static final Logger logger = LogManager.getRootLogger();

    // protected AbortHandler MyAbortHandler = new AbortHandler() {
    // 	    public void handle( int n ) {
    // 		System.err.println( "This is my Java logger abort function" );
    // 		System.exit( n );
    // 	    }
    // 	};

    public static final Map<String,String> NO_ENV            = null;
    public static final String             FILENAME          = null;
    public static final String             NO_DEFCFG         = null;
    public static final String             NO_CFG_ON_CMDLINE = null;
    public static final String             NO_CFG_IN_ENV     = null;

    public static final Usage              DEFAULT_USAGE     = null;

    public static int file_count = 0; //< number of option less file names

    // ===================================================================================
    static public interface Usage {                                   // AppOptions::Usage
	// -------------------------------------------------------------------------------
	public void display( String pn );
    };

    // ===================================================================================
    static public class cli_map {                                   // AppOptions::cli_map
	// -------------------------------------------------------------------------------
	public String  opt = null;   //< command line option that overrides a config entry
	public String  sec = null;   //< section to be overridden in ConfigDB
	public String  key = null;   //< key     to be overridden in ConfigDB
	public boolean req = false;  //< boolean flag, this option is required
	public String  def = null;   //< default value
	public String  dec = null;   //< description for the auto generated usage

	// ===============================================================================
	public cli_map( String a1, String a2, String a3,
			boolean a4, String a5, String a6 ) {
	    // ---------------------------------------------------------------------------
	    if ( null == a1 ) {
		this.opt = new String( "file" + AppOptions.file_count );
		AppOptions.file_count += 1;
	    } else {
		this.opt = new String( a1 );
	    }

	    if ( null == a2 ) {
		this.sec = null;
	    } else {
		this.sec = new String( a2 );
	    }

	    if ( null == a3 ) {
		this.key = null;
	    } else {
		this.key = new String( a3 );
	    }

	    this.req = a4;

	    if ( null == a5 ) {
		this.def = null;
	    } else {
		this.def = new String( a5 );
	    }

	    if ( null == a6 ) {
		this.dec = new String( "" );
	    } else {
		this.dec = new String( a6 );
	    }
	};

	// ===============================================================================
	public cli_map( cli_map that ) {
	    // ---------------------------------------------------------------------------
	    this.copy( that );
	};

	// ===============================================================================
	public void copy( cli_map that ) {
	    // ---------------------------------------------------------------------------
	    this.opt = new String( that.opt );
	    this.sec = new String( that.sec );
	    this.key = new String( that.key );
	    this.req = that.req;
	    if ( null == that.def ) {
		this.def = null;
	    } else {
		this.def = new String( that.def );
	    }
	    this.dec = new String( that.dec );
	};

	// ===============================================================================
	public void println( ) {
	    // ---------------------------------------------------------------------------
	    System.out.println( "Opt:"+opt
				+", Sec:"+sec
				+", Key:"+key
				+", Req:"+req
				+", Def:"+def
				+", Dec:"+dec
				);
	};
    };


    // ===================================================================================
    public static cli_map INIT( String a1, String a2, String a3,
				boolean a4, String a5, String a6 ) {
	// -------------------------------------------------------------------------------
	return new AppOptions.cli_map( a1, a2, a3, a4, a5, a6 );
    };

    
    // ===================================================================================
    static public class classInstance {                       // AppOptions::classInstance
	// -------------------------------------------------------------------------------

	/**                                       configuration file base for searching */
	public String config_base = new String("");
	
	/**                                      section name for environment variables */
	public String env_secname = new String("ENV");
	
	/**                                       configuration file path for searching */
	public String config_path = new String(".");
	
	/**                                       section name for command line options */
	public String opt_secname = new String("CLI");
	
	/**                               keyname for override config file found in ENV */
	public String env_keyname = new String("");

	/**                       keyname for override config file found on CommandLine */
	public String opt_keyname = new String("");

	/**                                      keyname to ask for help on CommandLine */
	public String  help_keyname = new String("");  

	/**                                                      check for help request */
	public boolean check_for_help = false;

	/**                                                     name of the application */
	public String  progName = new String("Application");

	/**                                             number of command line pointers */
	public int argc = 0;

	/**                                       shallow copy of command line pointers */
	public String[] argv = null;

	/**                                   shallow copy of environment pair pointers */
	public Map<String,String> env = null;

	/**                                   array of command line option descriptions */
	public cli_map[] map = null;

	/**                                                      number of current maps */
	public int map_count = 0;

	
	/**                                         flag for user supplied help display */
	public Usage user_supplied = null;

	/**                                                     parsed configuration DB */
	ConfigDB config = null;



	
	// ===============================================================================
	public classInstance( ) {
	    // ---------------------------------------------------------------------------
	    //logger.showLocation( true );
	};

	
	// ===============================================================================
	public void resize_map( cli_map[] new_map, int n ) {
	    // ---------------------------------------------------------------------------
	    cli_map[] old_map = map;
	    map = new cli_map[ map_count + n ];

	    for ( int i=0; i<map_count; i++ ) {
		map[i]     = new cli_map( old_map[i] ); 
		old_map[i] = null;
	    }

	    old_map = null;

	    for ( int i=0; i<n; i++ ) {
		map[map_count + i] = new cli_map( new_map[i] ); 
	    }

	    map_count += n;
	};

	
	// ===============================================================================
	public void display_map( ) {
	    // ---------------------------------------------------------------------------
	    if ( null == map ) {
		logger.info( "Map was null" );
	    } else {
		for ( int i=0; i<map_count; i++ ) {
		    map[i].println();
		}
	    }
	};
    };

    
    //    public static boolean EOD( cli_map[] cmap );

    
    public static classInstance instance = null;

    
    // ===================================================================================
    public static classInstance getInstance( ) {
	// -------------------------------------------------------------------------------
	if ( null == AppOptions.instance ) {
	    AppOptions.instance = new AppOptions.classInstance();
	}
	return AppOptions.instance;
    };

    public  AppOptions( ) {};

    
    // ===================================================================================
    public static void init( cli_map[] cmap ) {
	// -------------------------------------------------------------------------------
	addOptions( cmap );
    };

    
    // ===================================================================================
    public static void init( cli_map[] cmap, Usage uf ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	I.user_supplied = uf;
	addOptions( cmap );
    };

    // ===================================================================================
    public static void free(  ) {
	// -------------------------------------------------------------------------------
	AppOptions.instance = null;
    };

    
    // ===================================================================================
    public static void setConfigBase( String base ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	I.config_base = base;
    };

    
    // ===================================================================================
    public static void setConfigPath( String path ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	I.config_path = path;
    };

    // ===================================================================================
    public static void setEnvSectionName( String sec_name ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	I.env_secname = sec_name;
    };
    

    // ===================================================================================
    public static void setOptSectionName( String sec_name ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	I.opt_secname = sec_name;
    };

    // ===================================================================================
    public static void setEnvConfigFilename( String env_name ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	I.env_keyname = env_name;
    };

    
    // ===================================================================================
    public static void setOptConfigFilename( String opt_name ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	I.opt_keyname = opt_name;
    };

    
    // ===================================================================================
    public static void setHelp( String help_name ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	I.help_keyname = help_name;
	I.check_for_help = true;
    };

    
    // ===================================================================================
    public static void setCommandLine( String[] _argv, Map<String,String> _env ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	
	I.argc = _argv.length;

	I.argv = new String[I.argc];

	for ( int i=0; i<I.argc; i++ ) {
	    I.argv[i] = new String( _argv[i] );
	}

	I.env = _env;  // REPLACE THIS WITH A DEEP COPY
    };

    
    // ===================================================================================
    public static void setCommandLine( String[] _argv ) {
	// -------------------------------------------------------------------------------
	setCommandLine(_argv,null);
    };

    
    // ===================================================================================
    public static void addOptions( cli_map[] cmap ) {
	// -------------------------------------------------------------------------------
	if ( null != cmap ) {
	    AppOptions.classInstance I = AppOptions.getInstance();

	    int n = cmap.length;
	    I.resize_map( cmap, n );
	}
    };

    
    // ===================================================================================
    public static ConfigDB setConfigDB ( ConfigDB db ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();

	if ( null == I.config ) {
	    I.config = new ConfigDB();
	} else {
	    I.config.merge( db );
	}
	return I.config;
    };

    
    // ===================================================================================
    /**
     *  A ConfigDB is constructed by reading data in successive layers each layer over
     *  writing the previous.
     *
     *  For the following example:
     *      cfg=test.ini is on the command line.
     *      MY_CONFIG=~/common.ini as an environment variable key-pair
     *      cfg_base = "example.cfg"
     *      cfg_path = "/usr/share/trncmp:/etc:~/lib:~:."
     *
     *  empty ConfigDB                 <-- performed first
     *  default_map
     *  /usr/share/trncmp/example.cfg
     *  /etc/example.cfg
     *  ~/lib/example.cfg
     *  ~/lib/example.cfg
     *  ./example.cfg
     *  ~/common.ini
     *  ./test.ini
     *  command_line_overrides         <-- last performed
     */
    // -----------------------------------------------------------------------------------
    public static ConfigDB getConfigDB( ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();

	if ( I.check_for_help ) {
	    if ( null != I.argv ) {
		for ( int i=0; i<I.argc; i++ ) {
		    if ( 0 == I.help_keyname.compareTo( I.argv[i] ) ) {
			AppOptions.usage( I.argv[0] );
			return null;
		    }
		}
	    }
	}

	// -------------------------------------------------------------------------------
	// allocate a new config DB if necessary
	// -------------------------------------------------------------------------------

	if ( null == I.config ) {
	    I.config = new ConfigDB();
	}

	// -------------------------------------------------------------------------------
	// add defaults from the user if available
	// -------------------------------------------------------------------------------

	for ( int idx=0; idx<I.map_count; idx++ ) {
	    if ( null != I.map[idx].def ) {
		I.config.set( I.map[idx].sec, I.map[idx].key, I.map[idx].def );
	    }
	}

	// -------------------------------------------------------------------------------
	// parse the command line for options and environment variables
	// -------------------------------------------------------------------------------

	ConfigDB.Section csec = null;
	ConfigDB.Section esec = null;

	if ( null != I.env ) {
	    AppOptions.parseEnvironment( I.config, I.env, I.env_secname );

	    try {
		esec = I.config.get( I.env_secname );
	    } catch( ConfigDB.NoSection e ) {
		logger.error( "failed to read environment section: " + I.env_secname );
		I.config = null;
		return null;
	    }
	}

	if ( null != I.argv ) {
	    AppOptions.parseCommandLine( I.config, I.argc, I.argv, I.opt_secname );

	    try {
		csec = I.config.get( I.opt_secname );
	    } catch( ConfigDB.NoSection e ) {
		logger.error( "failed to read command line section: " + I.opt_secname );
		I.config = null;
		return null;
	    }
	}

	// -------------------------------------------------------------------------------
	// first try to guess where config files are
	// -------------------------------------------------------------------------------

	if ( 0 < I.config_base.length() ) {
	    String[] prefix = { "", "." };
	    int      prefix_n = 2;

	    String[] suffix = { "", ".ini", ".cfg", ".config" };
	    int      suffix_n = 4;

	    String[] SP = I.config_path.split( ":" );

	    int n = SP.length;

	    for (int i=0; i<n; i++) {
		for ( int j=0; j<prefix_n; j++ ) {
		    for ( int k=0; k<suffix_n; k++ ) {
			String test = new String( SP[i] + "/" + prefix[j] +
						  I.config_base + suffix[k] );
			//std::cout << "Try [" << test << "]";
			if ( true == FileTools.fileExists( test ) ) {
			    //std::cout << " (found)";
			    I.config.readINI( test );
			}
			//std::cout << std::endl;
		    }
		}
	    }
	}
  
	// -------------------------------------------------------------------------------
	// check to see if there is an environment variable for the config filename
	// -------------------------------------------------------------------------------

	if ( AppOptions.NO_ENV != I.env ) {
	    if ( 0 < I.env_keyname.length() ) {
		try {
		    esec = I.config.get( I.env_secname );
		    String cfgfn = esec.get( I.env_keyname );
		    logger.debug( "Reading config from ENV: " + cfgfn );
		    I.config.readINI( cfgfn );
		} catch( ConfigDB.NoSection e1 ) {
		} catch( ConfigDB.NoSuchKey e2 ) {
		}
	    }
	}

	// -------------------------------------------------------------------------------
	// next check to see if there is a command line option for the config filename
	// -------------------------------------------------------------------------------

	if ( 0 < I.opt_keyname.length() ) {
	    try {
		String cfgfn = csec.get( I.opt_keyname );
		logger.debug( "Reading config from CMDLIN: " + cfgfn );
		I.config.readINI( cfgfn );
	    } catch( ConfigDB.NoSuchKey e ) {
	    }
	}

	// -------------------------------------------------------------------------------
	// use command line options to override keypairs in the config database
	// -------------------------------------------------------------------------------

	for ( int idx=0; idx<I.map_count; idx++ ) {
	    String cli_opt = I.map[ idx ].opt;
	    try {
		String cli_var = csec.get( cli_opt );
		I.config.set( I.map[idx].sec, I.map[idx].key, cli_var );
	    } catch( ConfigDB.NoSuchKey e ) {
	    }
	}

	// -------------------------------------------------------------------------------
	// finally validate the contents of the config database
	// -------------------------------------------------------------------------------

	int missing_opt = 0;
	for ( int idx=0; idx<I.map_count; idx++ ) {
	    try {
		if ( I.map[ idx ].req ) {
		    ConfigDB.Section sec = I.config.get( I.map[ idx ].sec );
		    if ( ! sec.hasKey( I.map[ idx ].key ) ) {
			System.err.println( "You either need [" + I.map[ idx ].opt
					    + "] on the command line, or ["
					    + I.map[ idx ].sec + "." + I.map[ idx ].key
					    + "] in the configuration file" );
			missing_opt += 1;
		    }
		}
	    } catch ( ConfigDB.NoSection e ) {
		missing_opt += 1;
	    }
	}

	if ( 0 < missing_opt ) {
	    logger.error( "missing options from the command line." );
	    AppOptions.usage();
	    I.config = null;
	}

	return I.config;
    };


    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static void usage( ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	if ( AppOptions.DEFAULT_USAGE == I.user_supplied ) {
	    AppOptions.auto_usage( I.progName, I.map, I.map_count );
	} else {
	    I.user_supplied.display( I.progName );
	}
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static void usage( String pn ) {
	// -------------------------------------------------------------------------------
	AppOptions.classInstance I = AppOptions.getInstance();
	if ( AppOptions.DEFAULT_USAGE == I.user_supplied ) {
	    AppOptions.auto_usage( pn, I.map, I.map_count );
	} else {
	    I.user_supplied.display( pn );
	}
    }

    
    // ===================================================================================
    // -----------------------------------------------------------------------------------
    public static void usage( String pn, Usage nuf ) {
	// -------------------------------------------------------------------------------
	nuf.display( pn );
    }

    
    // ===================================================================================
    public static void auto_usage( String pname, AppOptions.cli_map[] cmap, int n_maps ) {
	// -------------------------------------------------------------------------------
	if ( null == cmap ) {
	    logger.warn("To use AppOptions::auto_usage a valid cli_map must be supplied");
	} else {
	    if ( null == cmap[0] ) {
		logger.warn("To use AppOptions::auto_usage a valid cli_map must be supplied");
	    } else {

		int n_file = 0;

		for ( int i=0; i<n_maps; i++ ) {
		    if ( cmap[ i ].opt.startsWith( "file" ) ) { n_file += 1; }
		}

		int n_opts = n_maps - n_file;

		System.err.print( "\n  USAGE: " + pname );
		if ( 0 < n_opts ) { System.err.print( " options" ); }
		if ( 0 < n_file ) { System.err.print( " files" );   }
		System.err.println( "" );

		// ----- display options -------------------------------------------------
		if ( 0 < n_opts ) {
		    System.err.println( "\n    options" );
		    for (int i=0; i<n_maps; i++) {
			if ( ! cmap[ i ].opt.startsWith( "file" ) ) {
			    System.err.print( "      " + cmap[ i ].opt +
					      "\t- " + cmap[ i ].dec );
			    if ( cmap[ i ].req ) { System.err.print( " (required)" ); }
			    System.err.println( "" );
			}
		    }
		}
      

		// ----- display files ---------------------------------------------------
		if ( 0 < n_file ) {
		    int fc = 0;
		    System.err.println( "\n    files" );
		    for (int i=0; i<n_maps; i++) {
			if ( cmap[ i ].opt.startsWith( "file" ) ) {
			    System.err.print( "      file" + fc + "\t- " + cmap[ i ].dec );
			    fc += 1;
			    if ( cmap[ i ].req ) { System.err.print(" (required)" ); }
			    System.err.println( "" );
			}
		    }
		}
      

		System.err.println( "" );
	    }
	}
    }

    // ===================================================================================
    public static boolean parseCommandLine( ConfigDB cfg, int _argc, String[] _argv,
					    String secname ) {
	// -------------------------------------------------------------------------------
	String pn = System.getProperty("sun.java.command").split(" ")[0];
  
	cfg.set( secname, "progname", pn );

	int fileCounter = 0;

	for (int i=0; i<_argc; i++) {
	    int sep = StringTool.find_any( _argv[i], "=:" );
	    if ( 0 < sep ) {
		cfg.set( secname,
			 _argv[i].substring( 0, sep ).trim(),
			 _argv[i].substring( sep+1 ).trim() ); 
	    } else {
		String file_key = new String( "file" + fileCounter );
		fileCounter += 1;
		cfg.set( secname, file_key, _argv[i] );
		
	    }
	}

	return false;
    };

    // ===================================================================================
    public static boolean parseEnvironment( ConfigDB cfg, Map<String,String> env,
					    String envname ) {
	// -------------------------------------------------------------------------------

	Iterator< Map.Entry<String,String> > it = env.entrySet().iterator();

	while( it.hasNext() ) {
	    Map.Entry<String,String> me = it.next();
	    cfg.set( envname, me.getKey(), me.getValue() );
	}

	return false;
    };

    // ===================================================================================
    public static void display_map( ) {
	// -------------------------------------------------------------------------------
	AppOptions.getInstance().display_map();
    };


};


// =======================================================================================
// **                                A P P O P T I O N S                                **
// ======================================================================== END FILE =====
