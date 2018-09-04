function [U,S,V,W,L,TP,DX,mu] = pcatest
  ns = 1000;
  nv = 3;
  
  dmu  = [ 35.0, 72.0, 51.0 ];
  dsig = [ 4.0, 1.0, 0.5 ];
  
  a = deg2rad( 45. );
  b = deg2rad( 10. );
  c = deg2rad( 30. );
  
  TP = zeros(ns,nv);
  DX = zeros(ns,nv);
  
  r1 = rot1(a);
  r2 = rot2(b);
  r3 = rot3(c);
  
  fwd = r3*r2*r1;
   
  r1 = rot1(-a);
  r2 = rot2(-b);
  r3 = rot3(-c);
  
  rvs = r1*r2*r3;
  
  TP = randn(ns,nv) * diag(dsig);
  DX = TP*rvs .+ dmu;
     
  mu = mean( DX );
  
  A = DX - mu;
  
  cov = (A'*A)/(ns-1);
  
  [U,S,V] = svd(A);
  
  [W, L] = eig( cov );
  
end
