// Generated from Funl.g by ANTLR 4.7.2

import LambdaContent.*;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FunlParser}.
 */
public class FListener extends FunlBaseListener {

    FunlParser parser;
	AST ast;

    public void printError(String error) {
        System.err.println(error);
        System.exit(-1);
    }


    public FListener(FunlParser parse) {
        this.parser = parser;
    }


    @Override
	public void enterProgram(FunlParser.ProgramContext ctx) {
        //System.out.println("enter: " + ctx.getText());
		ast = new AST();
    }
    @Override
	public void exitProgram(FunlParser.ProgramContext ctx) {
		//System.out.println("exit: " + ctx.getText());
		ast.mainExpr();
		ast.evaluateMain();

    }

    @Override
	public void enterMain(FunlParser.MainContext ctx) {
		//System.out.println("enter: " + ctx.getText());
		Expr expr = getExpr(ctx.expr());
		String id = "main";
		ast.map.put(id, expr);
    }

	public Expr getExpr(FunlParser.ExprContext ctx) {
		Expr expr = null;
		if (ctx.APPLY() != null) {
			Expr expr1 = getExpr(ctx.expr(0));
			Expr expr2 = getExpr(ctx.expr(1));
			expr = new Expr(expr1, expr2);

		} else if (ctx.IF() != null) {
			Expr expr1 = getExpr(ctx.expr(0));
			Expr expr2 = getExpr(ctx.expr(1));
			Expr expr3 = getExpr(ctx.expr(2));
			expr = new Expr(new Expr(new Expr(Encoding.IF, expr1), expr2), expr3);

		} else if (ctx.TRUE() != null) {
			expr = Encoding.TRUE;

		} else if (ctx.FALSE() != null) {
			expr = Encoding.FALSE;

		} else if (ctx.binop() != null) {
			expr = getBinopExpr(ctx);

		} else if (ctx.unop() != null) {
			Expr expr2 = getExpr(ctx.expr(0));
			if(ctx.unop().NOT() != null) {
				expr = new Expr(Encoding.NOT, expr2);
			}
		} else if (ctx.NUMERAL() != null) {
			expr = new Expr(Integer.parseInt(ctx.NUMERAL().getText()));

		} else if (ctx.IDENT() != null) {
			expr = new Expr(ctx.IDENT().getText());

		} else if (ctx.LPAREN() != null) {
			expr = getExpr(ctx.expr(0));

		}

		return expr;
	}


    @Override
	public void exitMain(FunlParser.MainContext ctx) {
		//System.out.println("exit: " + ctx.getText());
    }

    @Override 
	public void enterDecls(FunlParser.DeclsContext ctx) {
		//System.out.println("enter: " + ctx.getText());
		if (isRecursion(ctx)) {
			Expr exprSon = getExpr(ctx.expr());
			Expr expr = new Expr(ctx.args().getText(), exprSon);
			String id = ctx.IDENT().getText();
			Expr exprH = new Expr(id, expr);
			Expr Y_expr = new Expr(Encoding.Y,exprH);
			ast.map.put(id, Y_expr);

		} else {
			Expr exprSon = getExpr(ctx.expr());
			Expr expr = new Expr(ctx.args().getText(), exprSon);
			String id = ctx.IDENT().getText();
			ast.map.put(id, expr);

		}

    }

    @Override 
	public void exitDecls(FunlParser.DeclsContext ctx) {
		//System.out.println("exit: " + ctx.getText());
    }

    @Override 
	public void enterWhere(FunlParser.WhereContext ctx) {
		//System.out.println("enter: " + ctx.getText());
    }

    @Override 
	public void exitWhere(FunlParser.WhereContext ctx) {
		//System.out.println("exit: " + ctx.getText());
    }
    @Override 
	public void enterArgs(FunlParser.ArgsContext ctx) {
		//System.out.prhieve a grade higher than 17, you will neeintln("enter: " + ctx.getText());
    }

    @Override 
	public void exitArgs(FunlParser.ArgsContext ctx) {
		//System.out.println("exit: " + ctx.getText());
    }

    @Override 
	public void enterExpr(FunlParser.ExprContext ctx) {
		//System.out.println("enter: " + ctx.getText());
    }

    @Override 
	public void exitExpr(FunlParser.ExprContext ctx) {
		//System.out.println("exit: " + ctx.getText());
    }

    @Override 
	public void enterUnop(FunlParser.UnopContext ctx) {
		//System.out.println("enter: " + ctx.getText());
    }

    @Override 
	public void exitUnop(FunlParser.UnopContext ctx) {
		//System.out.println("exit: " + ctx.getText());
    }

    @Override 
	public void enterBinop(FunlParser.BinopContext ctx) {
		//System.out.println("enter: " + ctx.getText());
    }

    @Override 
	public void exitBinop(FunlParser.BinopContext ctx) {
		// System.out.println("exit: " + ctx.getText());
    }

	private boolean isRecursion(FunlParser.DeclsContext ctx) {
		String id = ctx.IDENT().getText();
		return containsID(ctx.expr(),id);
	}

	private boolean containsID(FunlParser.ExprContext ctx, String id) {
		if (ctx.IDENT() != null && ctx.IDENT().getText().equals(id)) {
			return true;
		}
		boolean res = false;
		for(FunlParser.ExprContext expr : ctx.expr()){
			res = res || containsID(expr, id);
		}
		return res;
	}

	public Expr getBinopExpr(FunlParser.ExprContext ctx) {
		Expr expr = null;
		Expr expr1 = getExpr(ctx.expr(0));
		Expr expr2 = getExpr(ctx.expr(1));
		if (ctx.binop().AND() != null) {
			expr = new Expr(new Expr(Encoding.AND, expr1), expr2);
		} else if (ctx.binop().OR() != null) {
			expr = new Expr(new Expr(Encoding.OR, expr1), expr2);
		} else if (ctx.binop().EQ() != null) {
			expr = new Expr(expr1, Expr.EQ, expr2);
		} else if (ctx.binop().NEQ() != null) {
			expr = new Expr(expr1, Expr.NEQ, expr2);
		} else if (ctx.binop().LT() != null) {
			expr = new Expr(expr1, Expr.LT, expr2);
		} else if (ctx.binop().LTE() != null) {
			expr = new Expr(expr1, Expr.LTE, expr2);
		} else if (ctx.binop().GT() != null) {
			expr = new Expr(expr1, Expr.GT, expr2);
		} else if (ctx.binop().GTE() != null) {
			expr = new Expr(expr1, Expr.GTE, expr2);
		} else if (ctx.binop().LTE() != null) {
			expr = new Expr(expr1, Expr.LTE, expr2);
		} else if(ctx.binop().PLUS() != null) {
			expr = new Expr(expr1, Expr.PLUS, expr2);
		} else if(ctx.binop().MINUS() != null) {
			expr = new Expr(expr1, Expr.BIN_MINUS, expr2);
		} else if (ctx.binop().MUL() != null) {
			expr = new Expr(expr1, Expr.MUL, expr2);
		} else if (ctx.binop().DIV() != null) {
			expr = new Expr(expr1, Expr.DIV, expr2);
		}
		return expr;
	}

}
