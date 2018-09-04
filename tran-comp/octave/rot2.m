function R = rot2(ang)
  
  s = sin(ang);
  c = cos(ang);

  R = [  c,  0.0, -s;
        0.0, 1.0, 0.0;
         s,  0.0,  c ];
  
endfunction 