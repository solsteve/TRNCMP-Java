// ====================================================================== BEGIN FILE =====
// **                           C O N F U S I O N M A T R I X                           **
// =======================================================================================
// **                                                                                   **
// **  Copyright (c) 2020, Stephen W. Soliday                                           **
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
 * @brief   Generate a Confusion Matrix.
 * @file    ConfusionMatrix.java
 *
 * @details Provides the interface and procedures for generating a ConfusionMatrix and
 *          the associated metrics.
 *
 * @date    2020-Jul-27
 */
// =======================================================================================


package org.trncmp.lib;

import java.io.PrintStream;


// =======================================================================================
public class ConfusionMatrix {
  // -------------------------------------------------------------------------------------

  /** Graph title */
  protected String graph_title = "";

  /** Three letter class labels */
  protected String[] short_labels = null;

  /** Seven letter class labels */
  protected String[] long_labels = null;

  /** Total number of unlabeld actuals */
  protected int unknown_class_actual = 0;

  /** Total number of unlabeled predictions */
  protected int unknown_class_predict = 0;

  /** Number of classes */
  protected int num_cls = 0;

  /** Confusion matrix */
  protected int[][] mat = null;

  /** Actual totals */
  protected int[] act_total = null;

  /** Predicted totals */
  protected int[] prd_total = null;

  /** total agreement ( diagonal sum ) */
  protected int tru_total = 0;

  /** total off diagonal */
  protected int off_total = 0;

  /** grand total */
  protected int total = 0;

  /** Accuracy */
  protected double ACC = 0.0e0;

  /** Precision or positive predictive value   */
  protected double[] PPV      = null;
  protected double   mean_PPV = 0.0e0;
  
  /** Recall, sensitivity, hit rate, or true positive rate  */
  protected double[] TPR      = null;
  protected double  mean_TPR = 0.0e0;

  /** F1 score is the harmonic mean of precision and sensitivity  */
  protected double[] F1S      = null;
  protected double  mean_F1S = 0.0e0;

  
  // =====================================================================================
  /** @brief Find Location
   *  @param array integer list
   *  @param integer test value
   *  @return index in the list of the te4st value, or -1 if test is not found.
   */
  // -------------------------------------------------------------------------------------
  public static int findloc( int[] array, int test ) {
    // -----------------------------------------------------------------------------------
    if (array == null) { 
      return -1; 
    } 
  
    int n = array.length; 

    for ( int i=0; i<n; i++ ) { 
      if ( array[i] == test ) { 
        return i; 
      } 
    } 
    return -1;
  }


  // =====================================================================================
  /** @brief Find Location
   *  @param array String list
   *  @param String test value
   *  @return index in the list of the te4st value, or -1 if test is not found.
   */
  // -------------------------------------------------------------------------------------
  public static int findloc( String[] array, String test ) {
    // -----------------------------------------------------------------------------------
    if (array == null) { 
      return -1; 
    } 
  
    int n = array.length; 

    for ( int i=0; i<n; i++ ) { 
      if ( test.equalsIgnoreCase( array[i] ) ) { 
        return i; 
      } 
    } 
    return -1;
  }








  // =====================================================================================
  /** Builder for the ConfusionMatrix class. */
  // -------------------------------------------------------------------------------------
  public static class Builder {
    // -----------------------------------------------------------------------------------

    /** Graph title */
    protected String graph_title = "";

    /** Three letter class labels */
    protected String[] lab3 = null;

    /** Seven letter class labels */
    protected String[] lab7 = null;

    
    // ===================================================================================
    /** @brief Constructor
     */
    // -----------------------------------------------------------------------------------
    public Builder() {
      // ---------------------------------------------------------------------------------
    }


    // ===================================================================================
    /** @brief Graph Title
     *  @param t title
     *  @return reference to this Builder.
     */
    // -----------------------------------------------------------------------------------
    public Builder title( String t ) {
      // ---------------------------------------------------------------------------------
      graph_title = t;
      return this;
    }

    // ===================================================================================
    /** @brief Short Labels
     *  @param lab list of 3 character labels.
     *  @return reference to this Builder.
     *
     *  Shallow copy of the arguments
     */
    // -----------------------------------------------------------------------------------
    public Builder short_labels( String[] lab ) {
      // ---------------------------------------------------------------------------------
      int n = lab.length;
      lab3 = new String[n];
      for ( int i=0; i<n; i++ ) {
        lab3[i] = lab[i];
      }
      return this;
    }

    // ===================================================================================
    /** @brief Long Labels
     *  @param lab list of 7 character labels.
     *  @return reference to this Builder.
     *
     *  Shallow copy of the arguments
     */
    // -----------------------------------------------------------------------------------
    public Builder long_labels( String[] lab ) {
      // ---------------------------------------------------------------------------------
      int n = lab.length;
      lab7 = new String[n];
      for ( int i=0; i<n; i++ ) {
        lab7[i] = lab[i];
      }
      return this;
    }

    
    // ===================================================================================
    /** @brief Build.
     *  @param A    list of actuals.
     *  @param P    list of predictions.
     *  @param ncls number of classes.
     *  @return Pointer to a fully processed ConfusionMatrix object.
     *
     *  List of actuals and predictions are correlated by index number. Each value is an
     *  integer from 0 to ncls-1.
     */
    // -----------------------------------------------------------------------------------
    public ConfusionMatrix build( int[] A, int[] P, int ncls ) {
      // ---------------------------------------------------------------------------------
      int nl = lab3.length;
      if ( nl == lab7.length ) {
        ConfusionMatrix CM = new ConfusionMatrix( graph_title, lab3, lab7, nl );
        int[] labels = new int[nl];
        for ( int i=0; i<nl; i++ ) {
          labels[i] = i;
        }
        CM.compile( A, P, labels );
        return CM;
      }
      System.err.format( "Name arrays must be the same length lab3=%d, lab7=%d\n",
                         nl, lab7.length );
      return null;
    }

    // ===================================================================================
    /** @brief Build.
     *  @param A      list of actuals.
     *  @param P      list of predictions.
     *  @param labels list of classes.
     *  @return Pointer to a fully processed ConfusionMatrix object.
     *
     *  List of actuals and predictions are correlated by index number. Each value is an
     *  integer selcted from the list of labels.
     */
    // -----------------------------------------------------------------------------------
    public ConfusionMatrix build( int[] A, int[] B, int[] labels ) {
      // ---------------------------------------------------------------------------------
      int nl = lab3.length;
      if ( nl == lab7.length ) {
        if ( nl == labels.length ) {
          ConfusionMatrix CM = new ConfusionMatrix( graph_title, lab3, lab7, nl );
          CM.compile( A, B, labels );
          return CM;
        } else {
          System.err.format( "Label array must be the same length as data labels=%d, data=%d\n",
                             labels.length, nl );
        }
      } else {
        System.err.format( "Name arrays must be the same length lab3=%d, lab7=%d\n",
                           nl, lab7.length );
      }
      return null;
    }

    // ===================================================================================
    /** @brief Build.
     *  @param A      list of actuals.
     *  @param P      list of predictions.
     *  @param labels list of classes.
     *  @return Pointer to a fully processed ConfusionMatrix object.
     *
     *  List of actuals and predictions are correlated by index number. Each value is a
     *  String selcted from the list of labels.
     */
    // -----------------------------------------------------------------------------------
    public ConfusionMatrix build( String[] A, String[] B, String[] labels ) {
      // ---------------------------------------------------------------------------------
      int nl = lab3.length;
      if ( nl == lab7.length ) {
        if ( nl == labels.length ) {
          ConfusionMatrix CM = new ConfusionMatrix( graph_title, lab3, lab7, nl );
          CM.compile( A, B, labels );
          return CM;
        } else {
          System.err.format( "Label array must be the same length as data labels=%d, data=%d\n",
                             labels.length, nl );
        }
      } else {
        System.err.format( "Name arrays must be the same length lab3=%d, lab7=%d\n",
                           nl, lab7.length );
      }
      return null;
    }

  } // end class ConfusionMatrix.Builder








  // =====================================================================================
  /** @brief Protected Constructor.
   *  @param title title for this chart.
   *  @param lab3 list of 3 character labels.
   *  @param lab7 list of 7 character labels.
   *  @param nl   number of labels.
   *
   *  construct an unprocessed ConfusionMatrix object.
   */
  // -------------------------------------------------------------------------------------
  protected ConfusionMatrix( String title, String[] lab3, String[] lab7, int nl ) {
    // -----------------------------------------------------------------------------------

    graph_title = new String(title);

    short_labels = new String[nl];
    long_labels  = new String[nl];

    for ( int i=0; i<nl; i++ ) {
      short_labels[i] = new String( lab3[i] );
      long_labels[i]  = new String( lab7[i] );
    }

    unknown_class_actual  = 0;
    unknown_class_predict = 0;

    mat       = new int[nl][nl];
    act_total = new int[nl];
    prd_total = new int[nl];

    for ( int i=0; i<nl; i++ ) {
      act_total[i] = 0;
      prd_total[i] = 0;
      for ( int j=0; j<nl; j++ ) {
        mat[i][j] = 0;
      }
    }

    tru_total = 0;
    off_total = 0;
    total     = 0;

    num_cls = nl;

    TPR = new double[nl];
    PPV = new double[nl];
    F1S = new double[nl];

    for ( int i=0; i<nl; i++ ) {
      TPR[i] = 0.0e0;
      PPV[i] = 0.0e0;
      F1S[i] = 0.0e0;
    }

    ACC      = 0.0e0;
    mean_TPR = 0.0e0;
    mean_PPV = 0.0e0;
    mean_F1S = 0.0e0;
  }




  // =====================================================================================
  /** @brief Precision
   *  @param n class index.
   *  @return Precision of class n.
   *
   *  Precision is the estimated probability that a randomly selected retrieved
   *  document is relevant.
   *
   *  TP / ( TP + FP )
   */
  // -------------------------------------------------------------------------------------
  public double Precision( int n ) {
    // -----------------------------------------------------------------------------------
    return PPV[n];
  }

  // =====================================================================================
  /** @brief Recall
   *  @param n class index.
   *  @return Recall of class n.
   *
   *  Recall is the estimated probability that a randomly selected relevant document
   *  is retrieved in a search.
   *
   *  TP / ( TP + FN )
   */
  // -------------------------------------------------------------------------------------
  public double Recall( int n ) {
    // -----------------------------------------------------------------------------------
    return TPR[n];
  }

  // =====================================================================================
  /** @brief F1 score
   *  @param n class index.
   *  @return F1 score of class n.
   *
   *  A measure that combines precision and recall is the harmonic mean of precision
   *  and recall.
   
   *  2 * PPV * TPR / ( PPV + TPR )
   */
  // -------------------------------------------------------------------------------------
  public double F1Score( int n ) {
    // -----------------------------------------------------------------------------------
    return F1S[n];
  }

  // =====================================================================================
  /** @brief Accuracy
   *  @return Accuracy
   */
  // -------------------------------------------------------------------------------------
  public double Accuracy() {
    // -----------------------------------------------------------------------------------
    return ACC;
  }

  // =====================================================================================
  /** @brief Mean of the Precision
   *  @return Mean Precision
   *
   *  Precision is the estimated probability that a randomly selected retrieved
   *  document is relevant.
   *
   *  TP / ( TP + FP )
   */
  // -------------------------------------------------------------------------------------
  public double Precision() {
    // -----------------------------------------------------------------------------------
    return mean_PPV;
  }

  // =====================================================================================
  /** @brief Mean of the Recall
   *  @return Mean Recall
   *
   *  Recall is the estimated probability that a randomly selected relevant document
   *  is retrieved in a search.
   *
   *  TP / ( TP + FN )
   */
  // -------------------------------------------------------------------------------------
  public double Recall() {
    // -----------------------------------------------------------------------------------
    return mean_TPR;
  }

  // =====================================================================================
  /** @brief Mean of the F1 score
   *  @return Mean F1 score
   *
   *  A measure that combines precision and recall is the harmonic mean of precision
   *  and recall.
   
   *  2 * PPV * TPR / ( PPV + TPR )
   */
  // -------------------------------------------------------------------------------------
  public double F1Score() {
    // -----------------------------------------------------------------------------------
    return mean_F1S;
  }




  // =====================================================================================
  /** @brief Compile the Confusion Matrix.
   *  @param A    list of actuals.
   *  @param P    list of predictions.
   *  @param tlab symbols.
   *
   *  List of actuals and predictions are correlated by index number. Each value is an
   *  integer selcted from the list of symbols.
   */
  // -------------------------------------------------------------------------------------
  protected void compile( int[] A, int[] P, int[] tlab ) {
    // -----------------------------------------------------------------------------------
    int n = A.length;
    if ( P.length < n ) {
      n = P.length;
    }

    for ( int i=0; i<n; i++ ) {
      int row = findloc( tlab, A[i] );
      if ( -1 < row ) {
        int col = findloc( tlab, P[i] );
        if ( -1 < col ) {
          mat[row][col] += 1;
        }
      }
    }

    for ( int i=0; i<A.length; i++ ) {
      int row = findloc( tlab, A[i] );
      if ( 0 > row ) {
        unknown_class_actual += 1;
      }
    }


    for ( int i=0; i<P.length; i++ ) {
      int col = findloc( tlab, P[i] );
      if ( 0 > col ) {
        unknown_class_predict += 1;
      }
    }

    compute_metrics();
  }


  // =====================================================================================
  /** @brief Compile the Confusion Matrix.
   *  @param A    list of actuals.
   *  @param P    list of predictions.
   *  @param tlab symbols.
   *
   *  List of actuals and predictions are correlated by index number. Each value is a
   *  String selcted from the list of symbols.
   */
  // -------------------------------------------------------------------------------------
  protected void compile( String[] A, String[] P, String[] tlab ) {
    // -----------------------------------------------------------------------------------
    int n = A.length;
    if ( P.length < n ) {
      n = P.length;
    }

    for ( int i=0; i<n; i++ ) {
      int row = findloc( tlab, A[i] );
      if ( -1 < row ) {
        int col = findloc( tlab, P[i] );
        if ( -1 < col ) {
          mat[row][col] += 1;
        }
      }
    }

    for ( int i=0; i<A.length; i++ ) {
      int row = findloc( tlab, A[i] );
      if ( 0 > row ) {
        unknown_class_actual += 1;
      }
    }


    for ( int i=0; i<P.length; i++ ) {
      int col = findloc( tlab, P[i] );
      if ( 0 > col ) {
        unknown_class_predict += 1;
      }
    }

    compute_metrics();
  }




  // =====================================================================================
  /** @brief Compute the Metrics.
   *
   *  Use the compiled matrix to compute the rest of the metrics
   */
  // -------------------------------------------------------------------------------------
  protected void compute_metrics( ) {
    // -----------------------------------------------------------------------------------

    // sum the columns
    for ( int col=0; col<num_cls; col++ ) {
      prd_total[col] = 0;
      for ( int row=0; row<num_cls; row++ ) {
        prd_total[col] += mat[row][col];
      }
    }

    // sum the rows
    for ( int row=0; row<num_cls; row++ ) {
      act_total[row] = 0;
      for ( int col=0; col<num_cls; col++ ) {
        act_total[row] += mat[row][col];
      }
    }

    // sum the totals
    for ( int i=0; i<num_cls; i++ ) {
      tru_total += mat[i][i];
      total     += act_total[i];
    }
    off_total = total - tru_total;

    // Accuracy
    ACC = (double)tru_total / (double)total;

    // Precision
    for ( int i=0; i<num_cls; i++ ) {
      PPV[i] = (double)mat[i][i] / (double)prd_total[i];
    }

    // Recall
    for ( int i=0; i<num_cls; i++ ) {
      TPR[i] = (double)mat[i][i] / (double)act_total[i];
    }

    // F1 Score
    for ( int i=0; i<num_cls; i++ ) {
      F1S[i] = 2.0e0 * PPV[i] * TPR[i] / ( PPV[i] + TPR[i] );
    }

    // Compute Means
    mean_PPV = 0.0e0;
    mean_TPR = 0.0e0;
    mean_F1S = 0.0e0;
    for ( int i=0; i<num_cls; i++ ) {
     mean_PPV += ( PPV[i] * (double)act_total[i] );
     mean_TPR += ( TPR[i] * (double)act_total[i] );
     mean_F1S += ( F1S[i] * (double)act_total[i] );
    }
    mean_PPV /= (double)total;
    mean_TPR /= (double)total;
    mean_F1S /= (double)total;
  }


  // =====================================================================================
  /** @brief Show Matrix
   *  @param ps reference to a PrintStream.
   *
   *  Develop an ASCII art table describing the Confusion Matrix.
   */
  // -------------------------------------------------------------------------------------
  public void show_matrix( PrintStream ps ) {
    // -----------------------------------------------------------------------------------
    ps.format( "      %s\n\n", graph_title );

    ps.format( "      Prediction " );
    for ( int i=1; i<num_cls; i++ ) {
      ps.format( "          " );
    }
    ps.format( " +--%8d\n", off_total );
    
    ps.format( "A      " );
    for ( int i=0; i<num_cls; i++ ) {
      ps.format( " %7s  ", long_labels[i] );
    }
    ps.format( "/\n" );

    ps.format( "c     +" );
    for ( int i=0; i<num_cls; i++ ) {
      ps.format( "---------+" );
    }
    ps.format( "\n" );
    
    for ( int row=0; row<num_cls; row++ ) {
      switch(row) {
        case 0:  ps.format( "t %3s |", short_labels[0] ); break;
        case 1:  ps.format( "a %3s |", short_labels[1] ); break;
        default: ps.format( "  %3s |", short_labels[row] ); break;
      }
      
      for ( int col=0; col<num_cls; col++ ) {
        ps.format( " %7d |", mat[row][col] );
      }
      ps.format( "    %8d\n", act_total[row] );

      switch(row) {
        case 0:  ps.format( "u     +" ); break;
        case 1:  ps.format( "l     +" ); break;
        default: ps.format( "      +" ); break;
      }
      
      for ( int col=0; col<num_cls; col++ ) {
        ps.format( "---------+", mat[row][col] );
      }
      ps.format( "\n" );
    }

    ps.format( "       " );
    for ( int col=0; col<num_cls; col++ ) {
      ps.format( "%8d  ", prd_total[col] );
    }
    ps.format( "\\\n" );

    ps.format( "       " );
    for ( int i=0; i<num_cls; i++ ) {
      ps.format( "          " );
    }
    ps.format( " +--%8d\n", tru_total );

    ps.format( "  Total       Samples   = %d\n", total );
    if ( 0 < unknown_class_actual ) {
      ps.format( "  Actual      Unknowns  = %d\n", unknown_class_actual );
    }
    
    if ( 0 < unknown_class_predict ) {
      ps.format( "  Predicted   Unknowns  = %d\n", unknown_class_predict );
    }
  }

  
  // =====================================================================================
  /** @brief Show Metrics
   *  @param ps reference to a PrintStream.
   *
   *  Develop an ASCII art table describing the Metrics and their means.
   */
  // -------------------------------------------------------------------------------------
  public void show_metrics( PrintStream ps ) {
    // -----------------------------------------------------------------------------------

    ps.format( "            | Precision |   Recall  | F1-Score  | Support\n" );
    ps.format( " -----------+-----------+-----------+-----------+---------\n" );
    for ( int i=0; i<num_cls; i++ ) {
      ps.format( "  %-9s | %9.6f | %9.6f | %9.6f |%8d\n",
                 long_labels[i],
                 PPV[i],
                 TPR[i],
                 F1S[i],
                 act_total[i] );
    }
    ps.format( " -----------+-----------+-----------+-----------+---------\n" );
    ps.format( "  avg/total | %9.6f | %9.6f | %9.6f |%8d\n",
               mean_PPV,
               mean_TPR,
               mean_F1S,
               total );
    ps.format( "  Accuracy  = %9.6f\n", ACC );
  }

  
  // =====================================================================================
  /** @brief Show Both.
   *  @param ps reference to a PrintStream.
   *
   *  This is a convenience procedure. It will display both the matrix and the table
   *  of Metrics.
   */
  // -------------------------------------------------------------------------------------
  public void show( PrintStream ps ) {
    // -----------------------------------------------------------------------------------
    ps.format( "\n" );
    show_matrix(ps);
    ps.format( "\n" );
    show_metrics(ps);
    ps.format( "\n" );
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public static void TEST01() {
    // -----------------------------------------------------------------------------------
    int[] A = { 0,0,0,0,0,0,0,3,1,1,4,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2 };
    int[] P = { 0,0,0,0,1,1,2,4,0,1,3,1,1,1,1,2,2,2,0,0,2,2,2,2,2,2,3 };

    String   T  = "Test Net (Count)";
    String[] L3 = { "TGT", "NEU", "CLU" };
    String[] L7 = { "Target", "Neutral", "Clutter" };

    ConfusionMatrix CM = new Builder()
        .title( T )
        .short_labels( L3 )
        .long_labels( L7 )
        .build( A, P, 3 );

    CM.show( System.out );
    
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public static void TEST02() {
    // -----------------------------------------------------------------------------------
    int[] A = { 0,0,0,0,0,0,0,3,1,1,4,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2 };
    int[] P = { 0,0,0,0,1,1,2,4,0,1,3,1,1,1,1,2,2,2,0,0,2,2,2,2,2,2,3 };

    String   T  = "Test Net (List)";
    String[] L3 = { "TGT", "NEU", "CLU" };
    String[] L7 = { "Target", "Neutral", "Clutter" };
    int[] cls = { 0,1,2 };

    ConfusionMatrix CM = new Builder()
        .title( T )
        .short_labels( L3 )
        .long_labels( L7 )
        .build( A, P, cls );

    CM.show( System.out );
    
  }

  
  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public static void TEST03() {
    // -----------------------------------------------------------------------------------
    String[] A = { "T","T","T","T","T","T","T","x","N","N","y","N","N","N","N","N","N",
                   "N","C","C","C","C","C","C","C","C" };
    String[] P = { "T","T","T","T","N","N","C","y","T","N","x","N","N","N","N","C","C",
                   "C","T","T","C","C","C","C","C","C","x" };

    String   T  = "Test Net (String)";
    String[] L3 = { "TGT", "NEU", "CLU" };
    String[] L7 = { "Target", "Neutral", "Clutter" };
    String[] cls = { "T", "N", "C" };

    ConfusionMatrix CM = new Builder()
        .title( T )
        .short_labels( L3 )
        .long_labels( L7 )
        .build( A, P, cls );

    CM.show( System.out );
    
  }


  // =====================================================================================
  // -------------------------------------------------------------------------------------
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------
    TEST01();
    TEST02();
    TEST03();
  }

} // end Class ConfusionMatrix


// =======================================================================================
// **                           C O N F U S I O N M A T R I X                           **
// ======================================================================== END FILE =====





