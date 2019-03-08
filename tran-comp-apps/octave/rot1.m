function R = rot1(ang)
  
  s = sin(ang);
  c = cos(ang);

  R = [ 1.0, 0.0, 0.0;
        0.0,  c,   s;
        0.0, -s,   c ];
  
endfunction 