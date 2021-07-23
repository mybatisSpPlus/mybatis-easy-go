package com.github.mybatis.easy.go.step;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.actions.InsertInto;
import com.github.mybatis.easy.go.actions.Window;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.supportAnnotation.DatabaseVersion;
import com.github.mybatis.easy.go.windowFunctions.Over;
import com.github.mybatis.easy.go.windowFunctions.OverWindow;

import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 9:10
 */
@DatabaseVersion(maxVersion = "5.8")
public class Mysql5StepGenerator extends MysqlLikeStepGenerator {

    public Mysql5StepGenerator(List<Action> actions) {
        super(actions);
    }

    @Override
    public void OverWindowToStep(OverWindow function) throws Exception {
       throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void OverToStep(Over function) throws Exception {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void WindowToStep(Window window) throws Exception {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }
}
