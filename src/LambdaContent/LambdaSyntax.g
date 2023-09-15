expr := expr expr
     | var expr
     | expr binop expr
     | unop expr
     | var
     | number
     ;