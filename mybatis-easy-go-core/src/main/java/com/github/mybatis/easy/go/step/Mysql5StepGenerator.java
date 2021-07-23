package com.github.mybatis.easy.go.step;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.actions.InsertInto;
import com.github.mybatis.easy.go.actions.Window;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.supportAnnotation.DatabaseVersion;
import com.github.mybatis.easy.go.windowFunctions.*;

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
    public void RowNumberToStep() {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void RankToStep() {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void PercentRankToStep() {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void NthValueToStep(NthValue function)  {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void NtileToStep(Ntile function) {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void LeadToStep(Lead function)  {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void LastValueToStep(LastValue function)  {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void LagToStep(Lag function)  {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void FirstValueToStep(FirstValue function)  {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void DenseRankToStep() {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void CumeDistToStep() {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void OverWindowToStep(OverWindow function)  {
       throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void OverToStep(Over function)  {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }

    @Override
    public void WindowToStep(Window window)  {
        throw new UnsupportedOperationException("window functions are unsupported in version 5.X");
    }
}
