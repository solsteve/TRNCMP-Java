// ====================================================================== BEGIN FILE =====

package org.trncmp.test;

// =======================================================================================
public class jtest_builder {
  // -------------------------------------------------------------------------------------

  static class A {

    private int myid = 0;
    
    public static abstract class Builder<T extends Builder<T>> {

      private int id = 0;
      
      protected abstract T getThis();
      
      public T setID( int i ) {
        System.out.println("A: setID("+i+")");
        id = i;
        return getThis();
      }
      
      public A build() {
        System.out.println("A: build()");
        return new A(this);
      }
    } // end class Builder
    
    protected A( Builder<?> builder ) {
      System.out.println("A: constructor()");
      myid = builder.id;
    }

    public int getID() { return myid; }
    
  } // end class A


  static class B extends A {

    private int myNumber = 0;
    
    public static class Builder extends A.Builder<Builder> {

      private int num = 0;
      
      @Override
      public Builder getThis() {
        System.out.println("B: getThis()");
        return this;
      }
      
      public Builder setNumber( int n ) {
        System.out.println("B: setNumber("+n+")");
        num = n;
        return this;
      }
      
      public B build() {
        System.out.println("B: build()");
        return new B(this);
      }

    } // end class Builder
    
    protected B( Builder builder ) {
      super(builder);
      System.out.println("B: constructor()");
      myNumber = builder.num;
    }

    public int getNumber() { return myNumber; }
      
  } // end class B





  static class C extends A {

    private int myTemp = 0;
    
    public static class Builder extends A.Builder<Builder> {

      private int temp = 0;
      
      @Override
      public Builder getThis() {
        System.out.println("C: getThis()");
        return this;
      }
      
      public Builder setTemp( int t ) {
        System.out.println("C: setTemp("+t+")");
        temp = t;
        return this;
      }
      
      public C build() {
        System.out.println("C: build()");
        return new C(this);
      }
    } // end class Builder
    
    protected C( Builder builder ) {
      super(builder);
      System.out.println("C: constructor()");
      myTemp = builder.temp;
    }

    public int getTemp() { return myTemp; }
    
  } // end class C




  // =====================================================================================
  public static void main(String[] args) {
    // -----------------------------------------------------------------------------------

    System.out.println("\ncreate A <-- B\n");
    B b = new B.Builder().setID(1).setNumber(2).build();
    System.out.println( "B.id = "+b.getID()+" B.number = "+b.getNumber() );
    
    System.out.println("\ncreate A <-- C\n");
    C c = new C.Builder().setID(3).setTemp(4).build();
    System.out.println( "C.id = "+c.getID()+" C.temp   = "+c.getTemp() );
    
  }

}

// ======================================================================== END FILE =====
