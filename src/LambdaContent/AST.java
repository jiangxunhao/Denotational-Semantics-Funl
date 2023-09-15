package LambdaContent;

import java.util.HashMap;
import java.util.Map.Entry;

public class AST {
    public HashMap<String, Expr> map;
    public Expr mainExpr;
    public AST(){
        map = new HashMap<>();
    }

    public void mainExpr() {
        map.get("main").expr1 = map.get(map.get("main").expr1.getText());
        mainExpr = map.get("main");
    }

    public void printMain() {
        System.out.println(mainExpr.getText());
    }


    public void printAST() {
        for (Entry<String, Expr> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue().getText());
        }
    }

    public void evaluateMain() {
        printMain();
        while (canBeEvaluated(mainExpr)) {
            mainExpr = evaluate(mainExpr);
            printMain();
        }
    }

    private boolean canBeEvaluated(Expr expr) {
        boolean res = false;
        if (expr.type == Expr.APPLICATION) {
            if (expr.expr1.type == Expr.ABSTRACTION) {
                res = true;
            } else {
                res = res || canBeEvaluated(expr.expr1);
            }
        } else if (expr.type == Expr.BINOPEXPR) {
            if (expr.expr1.num != -1 && expr.expr2.num != -1) {
                res = true;
            } else {
                if (expr.expr1.num != -1) {
                    res = res || canBeEvaluated(expr.expr1);
                }
                if (expr.expr2.num != -1) {
                    res = res || canBeEvaluated(expr.expr2);
                }
            }
        } else {
            if (expr.expr1 != null) {
                res = res || canBeEvaluated(expr.expr1);
            }
            if (expr.expr2 != null) {
                res = res || canBeEvaluated(expr.expr2);
            }
        }
        return res;
    }

    public Expr evaluate(Expr expr) {
        switch (expr.type) {
            case Expr.APPLICATION -> {
                if (expr.expr1.type == Expr.ABSTRACTION) {
                    if (expr.expr1.freeVar.contains(expr.expr2.var)) {
                        rename(expr.expr1, expr.expr2.var);
                    }
                    expr = substitute(expr.expr1.expr2, expr.expr1.var, expr.expr2);
                } else {
                    expr.expr1 = evaluate(expr.expr1);
                }
            }
            case Expr.BINOPEXPR -> {
                if (expr.expr1.num != -1 && expr.expr2.num != -1) {
                    expr = calculate(expr);
                } else {
                    if (expr.expr1.num != -1) {
                        expr.expr1 = evaluate(expr.expr1);
                    }
                    if (expr.expr2.num != -1) {
                        expr.expr2 = evaluate(expr.expr2);
                    }
                }

            }
            default -> {
                if (expr.expr1 != null) {
                    expr.expr1 = evaluate(expr.expr1);
                }
                if (expr.expr2 != null) {
                    expr.expr2 = evaluate(expr.expr2);
                }

            }
        }

        return expr;
    }

    private Expr calculate(Expr expr) {
        switch (expr.op) {
            case Expr.PLUS -> expr = new Expr(expr.expr1.num + expr.expr2.num);
            case Expr.BIN_MINUS -> expr = new Expr(expr.expr1.num - expr.expr2.num);
            case Expr.MUL -> expr = new Expr(expr.expr1.num * expr.expr2.num);
            case Expr.DIV -> expr = new Expr(expr.expr1.num / expr.expr2.num);
            case Expr.EQ -> expr = (expr.expr1.num == expr.expr2.num) ? Encoding.TRUE : Encoding.FALSE;
            case Expr.NEQ -> expr = (expr.expr1.num != expr.expr2.num) ? Encoding.TRUE : Encoding.FALSE;
            case Expr.LT -> expr = (expr.expr1.num < expr.expr2.num) ? Encoding.TRUE : Encoding.FALSE;
            case Expr.LTE -> expr = (expr.expr1.num <= expr.expr2.num) ? Encoding.TRUE : Encoding.FALSE;
            case Expr.GT -> expr = (expr.expr1.num > expr.expr2.num) ? Encoding.TRUE : Encoding.FALSE;
            case Expr.GTE -> expr = (expr.expr1.num >= expr.expr2.num) ? Encoding.TRUE : Encoding.FALSE;
        }
        return expr;
    }

    public Expr substitute(Expr expr, String para, Expr newExpr) {
        if(expr.type == Expr.VAREXPR && expr.var.equals(para)) {
            return newExpr;
        }
        if (expr.expr1 != null) {
            expr.expr1 = substitute(expr.expr1, para, newExpr);
        }
        if (expr.expr2 != null) {
            expr.expr2 = substitute(expr.expr2, para, newExpr);
        }
        expr.updateFreeVar();
        return expr;
    }

    public void rename(Expr expr, String repeatedName) {
        if (expr.var != null && expr.var.equals(repeatedName)) {
            expr.var = expr.var + "'";
        }
        if (expr.expr1 != null) {
            rename(expr.expr1, repeatedName);
        }
        if (expr.expr2 != null) {
            rename(expr.expr2, repeatedName);
        }
    }

}
