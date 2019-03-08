function R = rot3(ang)
  
  s = sin(ang);
  c = cos(ang);

  R = [  c,   s,  0.0;
        -s,   c,  0.0;
        0.0, 0.0, 1.0 ];
  
endfunction 