package com.mybatis.sp.plus.step;


/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/14 21:01
 */
public class Step {

    String stepName;

    Object stepValue;

    public Step() {
    }

    public Step(String stepName) {
        this.stepName = stepName;
    }

    public Step(String stepName, Object stepValue) {
        this.stepName = stepName;
        this.stepValue = stepValue;
    }

    public String getStepName() {
        return stepName;
    }

    public Step setStepName(String stepName) {
        this.stepName = stepName;
        return this;
    }

    public Object getStepValue() {
        return stepValue;
    }

    public Step setStepValue(Object stepValue) {
        this.stepValue = stepValue;
        return this;
    }

}
