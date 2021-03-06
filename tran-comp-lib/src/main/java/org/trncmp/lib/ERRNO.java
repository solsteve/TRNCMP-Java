// =======================================================================================
// **                                     E R R N O                                     **
// ====================================================================== BEGIN FILE =====
// **                                                                                   **
// **  Copyright (c) 2015, Stephen W. Soliday                                           **
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
 * @file ERRNO.java
 *
 *  Provides ERRNO Error Numbers.
 *
 * @author Stephen W. Soliday
 * @date 2015-09-04
 */
// =======================================================================================

package org.trncmp.lib;

// =======================================================================================
public class ERRNO {
    // -----------------------------------------------------------------------------------

    protected static int last_error;

    public static int set( int eno ) { return ( last_error = eno ); }
    public static int get( )         { return   last_error;         }
    public static int clear( )       { return ( last_error = 0 );   }

    public static final int  EPERM           =   1; /**< Operation not permitted                         */
    public static final int  ENOENT          =   2; /**< No such file or directory                       */
    public static final int  ESRCH           =   3; /**< No such process                                 */
    public static final int  EINTR           =   4; /**< Interrupted system call                         */
    public static final int  EIO             =   5; /**< I/O error                                       */
    public static final int  ENXIO           =   6; /**< No such device or address                       */
    public static final int  E2BIG           =   7; /**< Argument list too long                          */
    public static final int  ENOEXEC         =   8; /**< Exec format error                               */
    public static final int  EBADF           =   9; /**< Bad file number                                 */
    public static final int  ECHILD          =  10; /**< No child processes                              */
    public static final int  EAGAIN          =  11; /**< Try again                                       */
    public static final int  ENOMEM          =  12; /**< Out of memory                                   */
    public static final int  EACCES          =  13; /**< Permission denied                               */
    public static final int  EFAULT          =  14; /**< Bad address                                     */
    public static final int  ENOTBLK         =  15; /**< Block device required                           */
    public static final int  EBUSY           =  16; /**< Device or resource busy                         */
    public static final int  EEXIST          =  17; /**< File exists                                     */
    public static final int  EXDEV           =  18; /**< Cross-device link                               */
    public static final int  ENODEV          =  19; /**< No such device                                  */
    public static final int  ENOTDIR         =  20; /**< Not a directory                                 */
    public static final int  EISDIR          =  21; /**< Is a directory                                  */
    public static final int  EINVAL          =  22; /**< Invalid argument                                */
    public static final int  ENFILE          =  23; /**< File table overflow                             */
    public static final int  EMFILE          =  24; /**< Too many open files                             */
    public static final int  ENOTTY          =  25; /**< Not a typewriter                                */
    public static final int  ETXTBSY         =  26; /**< Text file busy                                  */
    public static final int  EFBIG           =  27; /**< File too large                                  */
    public static final int  ENOSPC          =  28; /**< No space left on device                         */
    public static final int  ESPIPE          =  29; /**< Illegal seek                                    */
    public static final int  EROFS           =  30; /**< Read-only file system                           */
    public static final int  EMLINK          =  31; /**< Too many links                                  */
    public static final int  EPIPE           =  32; /**< Broken pipe                                     */
    public static final int  EDOM            =  33; /**< Math argument out of domain of func             */
    public static final int  ERANGE          =  34; /**< Math result not representable                   */
    public static final int  EDEADLK         =  35; /**< Resource deadlock would occur                   */
    public static final int  ENAMETOOLONG    =  36; /**< File name too long                              */
    public static final int  ENOLCK          =  37; /**< No record locks available                       */
    public static final int  ENOSYS          =  38; /**< Function not implemented                        */
    public static final int  ENOTEMPTY       =  39; /**< Directory not empty                             */
    public static final int  ELOOP           =  40; /**< Too many symbolic links encountered             */
    public static final int  ENOMSG          =  42; /**< No message of desired type                      */
    public static final int  EIDRM           =  43; /**< Identifier removed                              */
    public static final int  ECHRNG          =  44; /**< Channel number out of range                     */
    public static final int  EL2NSYNC        =  45; /**< Level 2 not synchronized                        */
    public static final int  EL3HLT          =  46; /**< Level 3 halted                                  */
    public static final int  EL3RST          =  47; /**< Level 3 reset                                   */
    public static final int  ELNRNG          =  48; /**< Link number out of range                        */
    public static final int  EUNATCH         =  49; /**< Protocol driver not attached                    */
    public static final int  ENOCSI          =  50; /**< No CSI structure available                      */
    public static final int  EL2HLT          =  51; /**< Level 2 halted                                  */
    public static final int  EBADE           =  52; /**< Invalid exchange                                */
    public static final int  EBADR           =  53; /**< Invalid request descriptor                      */
    public static final int  EXFULL          =  54; /**< Exchange full                                   */
    public static final int  ENOANO          =  55; /**< No anode                                        */
    public static final int  EBADRQC         =  56; /**< Invalid request code                            */
    public static final int  EBADSLT         =  57; /**< Invalid slot                                    */
    public static final int  EBFONT          =  59; /**< Bad font file format                            */
    public static final int  ENOSTR          =  60; /**< Device not a stream                             */
    public static final int  ENODATA         =  61; /**< No data available                               */
    public static final int  ETIME           =  62; /**< Timer expired                                   */
    public static final int  ENOSR           =  63; /**< Out of streams resources                        */
    public static final int  ENONET          =  64; /**< Machine is not on the network                   */
    public static final int  ENOPKG          =  65; /**< Package not installed                           */
    public static final int  EREMOTE         =  66; /**< Object is remote                                */
    public static final int  ENOLINK         =  67; /**< Link has been severed                           */
    public static final int  EADV            =  68; /**< Advertise error                                 */
    public static final int  ESRMNT          =  69; /**< Srmount error                                   */
    public static final int  ECOMM           =  70; /**< Communication error on send                     */
    public static final int  EPROTO          =  71; /**< Protocol error                                  */
    public static final int  EMULTIHOP       =  72; /**< Multihop attempted                              */
    public static final int  EDOTDOT         =  73; /**< RFS specific error                              */
    public static final int  EBADMSG         =  74; /**< Not a data message                              */
    public static final int  EOVERFLOW       =  75; /**< Value too large for defined data type           */
    public static final int  ENOTUNIQ        =  76; /**< Name not unique on network                      */
    public static final int  EBADFD          =  77; /**< File descriptor in bad state                    */
    public static final int  EREMCHG         =  78; /**< Remote address changed                          */
    public static final int  ELIBACC         =  79; /**< Can not access a needed shared library          */
    public static final int  ELIBBAD         =  80; /**< Accessing a corrupted shared library            */
    public static final int  ELIBSCN         =  81; /**< .lib section in a.out corrupted                 */
    public static final int  ELIBMAX         =  82; /**< Attempting to link in too many shared libraries */
    public static final int  ELIBEXEC        =  83; /**< Cannot exec a shared library directly           */
    public static final int  EILSEQ          =  84; /**< Illegal byte sequence                           */
    public static final int  ERESTART        =  85; /**< Interrupted system call should be restarted     */
    public static final int  ESTRPIPE        =  86; /**< Streams pipe error                              */
    public static final int  EUSERS          =  87; /**< Too many users                                  */
    public static final int  ENOTSOCK        =  88; /**< Socket operation on non-socket                  */
    public static final int  EDESTADDRREQ    =  89; /**< Destination address required                    */
    public static final int  EMSGSIZE        =  90; /**< Message too long                                */
    public static final int  EPROTOTYPE      =  91; /**< Protocol wrong type for socket                  */
    public static final int  ENOPROTOOPT     =  92; /**< Protocol not available                          */
    public static final int  EPROTONOSUPPORT =  93; /**< Protocol not supported                          */
    public static final int  ESOCKTNOSUPPORT =  94; /**< Socket type not supported                       */
    public static final int  EOPNOTSUPP      =  95; /**< Operation not supported on transport endpoint   */
    public static final int  EPFNOSUPPORT    =  96; /**< Protocol family not supported                   */
    public static final int  EAFNOSUPPORT    =  97; /**< Address family not supported by protocol        */
    public static final int  EADDRINUSE      =  98; /**< Address already in use                          */
    public static final int  EADDRNOTAVAIL   =  99; /**< Cannot assign requested address                 */
    public static final int  ENETDOWN        = 100; /**< Network is down                                 */
    public static final int  ENETUNREACH     = 101; /**< Network is unreachable                          */
    public static final int  ENETRESET       = 102; /**< Network dropped connection because of reset     */
    public static final int  ECONNABORTED    = 103; /**< Software caused connection abort                */
    public static final int  ECONNRESET      = 104; /**< Connection reset by peer                        */
    public static final int  ENOBUFS         = 105; /**< No buffer space available                       */
    public static final int  EISCONN         = 106; /**< Transport endpoint is already connected         */
    public static final int  ENOTCONN        = 107; /**< Transport endpoint is not connected             */
    public static final int  ESHUTDOWN       = 108; /**< Cannot send after transport endpoint shutdown   */
    public static final int  ETOOMANYREFS    = 109; /**< Too many references: cannot splice              */
    public static final int  ETIMEDOUT       = 110; /**< Connection timed out                            */
    public static final int  ECONNREFUSED    = 111; /**< Connection refused                              */
    public static final int  EHOSTDOWN       = 112; /**< Host is down                                    */
    public static final int  EHOSTUNREACH    = 113; /**< No route to host                                */
    public static final int  EALREADY        = 114; /**< Operation already in progress                   */
    public static final int  EINPROGRESS     = 115; /**< Operation now in progress                       */
    public static final int  ESTALE          = 116; /**< Stale file handle                               */
    public static final int  EUCLEAN         = 117; /**< Structure needs cleaning                        */
    public static final int  ENOTNAM         = 118; /**< Not a XENIX named type file                     */
    public static final int  ENAVAIL         = 119; /**< No XENIX semaphores available                   */
    public static final int  EISNAM          = 120; /**< Is a named type file                            */
    public static final int  EREMOTEIO       = 121; /**< Remote I/O error                                */
    public static final int  EDQUOT          = 122; /**< Quota exceeded                                  */
    public static final int  ENOMEDIUM       = 123; /**< No medium found                                 */
    public static final int  EMEDIUMTYPE     = 124; /**< Wrong medium type                               */
    public static final int  ECANCELED       = 125; /**< Operation Canceled                              */
    public static final int  ENOKEY          = 126; /**< Required key not available                      */
    public static final int  EKEYEXPIRED     = 127; /**< Key has expired                                 */
    public static final int  EKEYREVOKED     = 128; /**< Key has been revoked                            */
    public static final int  EKEYREJECTED    = 129; /**< Key was rejected by service                     */
    public static final int  EOWNERDEAD      = 130; /**< Owner died                                      */
    public static final int  ENOTRECOVERABLE = 131; /**< State not recoverable                           */
    public static final int  ERFKILL         = 132; /**< Operation not possible due to RF-kill           */
    public static final int  EHWPOISON       = 133; /**< Memory page has hardware error                  */

    public static String strerror( int eno ) {
	switch( eno ) {
	case EPERM:           return new String( "Operation not permitted" );
	case ENOENT:          return new String( "No such file or directory" );
	case ESRCH:           return new String( "No such process" );
	case EINTR:           return new String( "Interrupted system call" );
	case EIO:             return new String( "I/O error" );
	case ENXIO:           return new String( "No such device or address" );
	case E2BIG:           return new String( "Argument list too long" );
	case ENOEXEC:         return new String( "Exec format error" );
	case EBADF:           return new String( "Bad file number" );
	case ECHILD:          return new String( "No child processes" );
	case EAGAIN:          return new String( "Try again" );
	case ENOMEM:          return new String( "Out of memory" );
	case EACCES:          return new String( "Permission denied" );
	case EFAULT:          return new String( "Bad address" );
	case ENOTBLK:         return new String( "Block device required" );
	case EBUSY:           return new String( "Device or resource busy" );
	case EEXIST:          return new String( "File exists" );
	case EXDEV:           return new String( "Cross-device link" );
	case ENODEV:          return new String( "No such device" );
	case ENOTDIR:         return new String( "Not a directory" );
	case EISDIR:          return new String( "Is a directory" );
	case EINVAL:          return new String( "Invalid argument" );
	case ENFILE:          return new String( "File table overflow" );
	case EMFILE:          return new String( "Too many open files" );
	case ENOTTY:          return new String( "Not a typewriter" );
	case ETXTBSY:         return new String( "Text file busy" );
	case EFBIG:           return new String( "File too large" );
	case ENOSPC:          return new String( "No space left on device" );
	case ESPIPE:          return new String( "Illegal seek" );
	case EROFS:           return new String( "Read-only file system" );
	case EMLINK:          return new String( "Too many links" );
	case EPIPE:           return new String( "Broken pipe" );
	case EDOM:            return new String( "Math argument out of domain of func" );
	case ERANGE:          return new String( "Math result not representable" );
	case EDEADLK:         return new String( "Resource deadlock would occur" );
	case ENAMETOOLONG:    return new String( "File name too long" );
	case ENOLCK:          return new String( "No record locks available" );
	case ENOSYS:          return new String( "Function not implemented" );
	case ENOTEMPTY:       return new String( "Directory not empty" );
	case ELOOP:           return new String( "Too many symbolic links encountered" );
	case ENOMSG:          return new String( "No message of desired type" );
	case EIDRM:           return new String( "Identifier removed" );
	case ECHRNG:          return new String( "Channel number out of range" );
	case EL2NSYNC:        return new String( "Level 2 not synchronized" );
	case EL3HLT:          return new String( "Level 3 halted" );
	case EL3RST:          return new String( "Level 3 reset" );
	case ELNRNG:          return new String( "Link number out of range" );
	case EUNATCH:         return new String( "Protocol driver not attached" );
	case ENOCSI:          return new String( "No CSI structure available" );
	case EL2HLT:          return new String( "Level 2 halted" );
	case EBADE:           return new String( "Invalid exchange" );
	case EBADR:           return new String( "Invalid request descriptor" );
	case EXFULL:          return new String( "Exchange full" );
	case ENOANO:          return new String( "No anode" );
	case EBADRQC:         return new String( "Invalid request code" );
	case EBADSLT:         return new String( "Invalid slot" );
	case EBFONT:          return new String( "Bad font file format" );
	case ENOSTR:          return new String( "Device not a stream" );
	case ENODATA:         return new String( "No data available" );
	case ETIME:           return new String( "Timer expired" );
	case ENOSR:           return new String( "Out of streams resources" );
	case ENONET:          return new String( "Machine is not on the network" );
	case ENOPKG:          return new String( "Package not installed" );
	case EREMOTE:         return new String( "Object is remote" );
	case ENOLINK:         return new String( "Link has been severed" );
	case EADV:            return new String( "Advertise error" );
	case ESRMNT:          return new String( "Srmount error" );
	case ECOMM:           return new String( "Communication error on send" );
	case EPROTO:          return new String( "Protocol error" );
	case EMULTIHOP:       return new String( "Multihop attempted" );
	case EDOTDOT:         return new String( "RFS specific error" );
	case EBADMSG:         return new String( "Not a data message" );
	case EOVERFLOW:       return new String( "Value too large for defined data type" );
	case ENOTUNIQ:        return new String( "Name not unique on network" );
	case EBADFD:          return new String( "File descriptor in bad state" );
	case EREMCHG:         return new String( "Remote address changed" );
	case ELIBACC:         return new String( "Can not access a needed shared library" );
	case ELIBBAD:         return new String( "Accessing a corrupted shared library" );
	case ELIBSCN:         return new String( ".lib section in a.out corrupted" );
	case ELIBMAX:         return new String( "Attempting to link in too many shared libraries" );
	case ELIBEXEC:        return new String( "Cannot exec a shared library directly" );
	case EILSEQ:          return new String( "Illegal byte sequence" );
	case ERESTART:        return new String( "Interrupted system call should be restarted" );
	case ESTRPIPE:        return new String( "Streams pipe error" );
	case EUSERS:          return new String( "Too many users" );
	case ENOTSOCK:        return new String( "Socket operation on non-socket" );
	case EDESTADDRREQ:    return new String( "Destination address required" );
	case EMSGSIZE:        return new String( "Message too long" );
	case EPROTOTYPE:      return new String( "Protocol wrong type for socket" );
	case ENOPROTOOPT:     return new String( "Protocol not available" );
	case EPROTONOSUPPORT: return new String( "Protocol not supported" );
	case ESOCKTNOSUPPORT: return new String( "Socket type not supported" );
	case EOPNOTSUPP:      return new String( "Operation not supported on transport endpoint" );
	case EPFNOSUPPORT:    return new String( "Protocol family not supported" );
	case EAFNOSUPPORT:    return new String( "Address family not supported by protocol" );
	case EADDRINUSE:      return new String( "Address already in use" );
	case EADDRNOTAVAIL:   return new String( "Cannot assign requested address" );
	case ENETDOWN:        return new String( "Network is down" );
	case ENETUNREACH:     return new String( "Network is unreachable" );
	case ENETRESET:       return new String( "Network dropped connection because of reset" );
	case ECONNABORTED:    return new String( "Software caused connection abort" );
	case ECONNRESET:      return new String( "Connection reset by peer" );
	case ENOBUFS:         return new String( "No buffer space available" );
	case EISCONN:         return new String( "Transport endpoint is already connected" );
	case ENOTCONN:        return new String( "Transport endpoint is not connected" );
	case ESHUTDOWN:       return new String( "Cannot send after transport endpoint shutdown" );
	case ETOOMANYREFS:    return new String( "Too many references: cannot splice" );
	case ETIMEDOUT:       return new String( "Connection timed out" );
	case ECONNREFUSED:    return new String( "Connection refused" );
	case EHOSTDOWN:       return new String( "Host is down" );
	case EHOSTUNREACH:    return new String( "No route to host" );
	case EALREADY:        return new String( "Operation already in progress" );
	case EINPROGRESS:     return new String( "Operation now in progress" );
	case ESTALE:          return new String( "Stale file handle" );
	case EUCLEAN:         return new String( "Structure needs cleaning" );
	case ENOTNAM:         return new String( "Not a XENIX named type file" );
	case ENAVAIL:         return new String( "No XENIX semaphores available" );
	case EISNAM:          return new String( "Is a named type file" );
	case EREMOTEIO:       return new String( "Remote I/O error" );
	case EDQUOT:          return new String( "Quota exceeded" );
	case ENOMEDIUM:       return new String( "No medium found" );
	case EMEDIUMTYPE:     return new String( "Wrong medium type" );
	case ECANCELED:       return new String( "Operation Canceled" );
	case ENOKEY:          return new String( "Required key not available" );
	case EKEYEXPIRED:     return new String( "Key has expired" );
	case EKEYREVOKED:     return new String( "Key has been revoked" );
	case EKEYREJECTED:    return new String( "Key was rejected by service" );
	case EOWNERDEAD:      return new String( "Owner died" );
	case ENOTRECOVERABLE: return new String( "State not recoverable" );
	case ERFKILL:         return new String( "Operation not possible due to RF-kill" );
	case EHWPOISON:       return new String( "Memory page has hardware error" );
	default: break;
	}
	return new String( "Undefined POSIX error" );
    }

    public static void perror( ) {
	System.err.println( strerror( last_error ) );
    }

    public static void perror( String msg ) {
	System.err.println( msg + ": " + strerror( last_error ) );
    }


}

// =======================================================================================
// **                                     E R R N O                                     **
// ======================================================================== END FILE =====
