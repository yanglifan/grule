package com.github.yanglifan.grule.core.domain;

import lombok.Data;
import lombok.Getter;

/**
 * @author Yang Lifan
 */
@Getter
public class EvaluateResult {
    private ResultType type;
    private String value;

   public enum ResultType {
        TRUE, FALSE
    }

    public static EvaluateResult trueResult(String value) {
        EvaluateResult evaluateResult = new EvaluateResult();
        evaluateResult.type = ResultType.TRUE;
        evaluateResult.value = value;
        return evaluateResult;
    }


    public static EvaluateResult falseResult() {
        EvaluateResult evaluateResult = new EvaluateResult();
        evaluateResult.type = ResultType.FALSE;
        return evaluateResult;
    }
}
