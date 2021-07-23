package com.github.mybatis.easy.go.windowFunctions.frame;

public abstract class Frame {

    public static CurrentRow currentRow(){
        return new CurrentRow();
    }

    public static UnboundedPreceding unboundedPreceding(){
        return new UnboundedPreceding();
    }

    public static UnboundedFollowing unboundedFollowing(){
        return new UnboundedFollowing();
    }

    public static Preceding preceding(int rowCount){
        return new Preceding(rowCount);
    }

    public static Following following(int rowCount){
        return new Following(rowCount);
    }
}
