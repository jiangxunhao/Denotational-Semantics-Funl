package LambdaContent;

public class Encoding {
    public static final Expr TRUE = new Expr("a",
            new Expr("b",
                    new Expr("a")
            )
    );
    public static final Expr FALSE = new Expr("a",
            new Expr("b",
                    new Expr("b")
            )
    );

    public static final Expr AND = new Expr("p",
            new Expr(
                    "q",
                    new Expr(
                            new Expr(new Expr("p"), new Expr("q")),
                            FALSE
                    )
            )
    );
    public static final Expr OR = new Expr("p",
            new Expr("q",
                    new Expr(
                            new Expr(new Expr("p"), TRUE),
                            new Expr("q")
                    )
            )
    );
    public static final Expr NOT = new Expr("p",
            new Expr("a",
                    new Expr("b",
                            new Expr(
                                    new Expr(new Expr("p"), new Expr("b")),
                                    new Expr("a")
                            )
                    )
            )
    );
    public static final Expr IF = new Expr("p",
            new Expr("a",
                    new Expr("b",
                            new Expr(
                                    new Expr(new Expr("p"), new Expr("a")),
                                    new Expr("b")
                            )
                    )
            )
    );

    public static final Expr Y =
            new Expr("f",
                    new Expr(
                            new Expr("x",
                                    new Expr(
                                            new Expr("f"),
                                            new Expr(new Expr("x"), new Expr("x"))
                                    )
                            ),
                            new Expr("x",
                                    new Expr(
                                            new Expr("f"),
                                            new Expr(new Expr("x"), new Expr("x"))
                                    )
                            )
                    )
            );

    public static final Expr CONS = new Expr("h",
            new Expr("t",
                    new Expr("f",
                            new Expr(
                                    new Expr(new Expr("f"), new Expr("h")),
                                    new Expr("t")
                            )
                    )
            )
    );

    public static final Expr NIL = new Expr(
            "f",
            TRUE
    );
/*
    public static final Expr NULL = new Expr(

    );

    public static final Expr HD = new Expr(

    );

    public static final Expr TL = new Expr(

    );

    public static final Expr L = new Expr(
            new Expr(CONS, new Expr(1)),
            new Expr(new Expr(CONS, new Expr(2)),
                    new Expr(new Expr(CONS, new Expr(3)),
                            NIL)
            )
    );
*/
}
