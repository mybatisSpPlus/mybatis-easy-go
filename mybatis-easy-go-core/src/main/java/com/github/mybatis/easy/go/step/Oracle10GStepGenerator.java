package com.github.mybatis.easy.go.step;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.supportAnnotation.DatabaseVersion;

import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 9:10
 */
@DatabaseVersion(maxVersion = "Oracle Database 11g Enterprise Edition Release 11.1")
public class Oracle10GStepGenerator extends OracleLikeStepGenerator {

    public Oracle10GStepGenerator(List<Action> actions) {
        super(actions);
    }

}
