package LambdaContent;

import javax.security.auth.RefreshFailedException;
import java.util.HashSet;

public class Expr{
    public static final int
            APPLICATION = 1,  ABSTRACTION = 2, BINOPEXPR = 3, VAREXPR = 5, NUMEXPR = 6;
    public static final int
            EQ = 9, NEQ = 10, LT = 11, LTE = 12,
            GT = 13, GTE = 14, PLUS = 15, BIN_MINUS = 16,
            DIV = 17, MUL = 18;
    public static final String[] opText= {
            "==", "!=", "<", "<=", ">", ">=", "+", "-", "/", "*"
    };

    private String getOpText(int op) {
        return opText[op - Expr.EQ];
    }

    public int type;
    public Expr expr1;
    public int op;
    public Expr expr2;
    public String var;
    public int num = -1;
    public HashSet<String> freeVar = new HashSet<>();


    public Expr(Expr expr1, Expr expr2) {
        type = Expr.APPLICATION;
        this.expr1 = expr1;
        this.expr2 = expr2;

        updateFreeVar();
    }

    public Expr(String var, Expr expr2) {
        type = Expr.ABSTRACTION;
        this.var = var;
        this.expr2 = expr2;

        updateFreeVar();
    }

    public Expr(Expr expr1, int op, Expr expr2) {
        type = Expr.BINOPEXPR;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.op = op;

        updateFreeVar();
    }

    public Expr(String var) {
        type = Expr.VAREXPR;
        this.var = var;

        updateFreeVar();
    }

    public Expr(int num) {
        type = Expr.NUMEXPR;
        this.num = num;

    }

    public void updateFreeVar() {
        freeVar.clear();
        if (expr1 != null) {
            freeVar.addAll(expr1.freeVar);
            if(expr1.type == Expr.ABSTRACTION){
                freeVar.add(expr1.var);
            }
        }
        if (expr2 != null) {
            freeVar.addAll(expr2.freeVar);
            if(expr2.type == Expr.ABSTRACTION){
                freeVar.add(expr2.var);
            }
        }
        if (type == VAREXPR) {
            freeVar.add(var);
        }
    }

    public String getText() {
        String text;
        switch(type) {
            case Expr.APPLICATION -> text = "(" + expr1.getText() + " " + expr2.getText() + ")";
            case Expr.ABSTRACTION -> text = "λ" + var + ".(" + expr2.getText() + ")";
            case Expr.BINOPEXPR -> text = expr1.getText() + getOpText(op) + expr2.getText();
            case Expr.VAREXPR -> text = var;
            case Expr.NUMEXPR -> text = Integer.toString(num);
            default -> text = "error";
        }
        return text;
    }
}
