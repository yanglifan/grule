package com.github.yanglifan.grule.core

import com.github.yanglifan.grule.core.domain.EvaluateResult
import com.github.yanglifan.grule.core.domain.Rule
import com.github.yanglifan.grule.core.repository.RuleRepository

import javax.script.Bindings
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class GRuleEngine {
    public static final String PARAMS = "p"
    private volatile ScriptEngine scriptEngine

    private final RuleRepository ruleRepository

    GRuleEngine(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository
    }

    void init() {
        ScriptEngineManager factory = new ScriptEngineManager()
        scriptEngine = factory.getEngineByName("groovy")
    }

    /**
     * Load a latest rule by the name, and execute it with params
     */
    EvaluateResult evaluate(String name, Map<String, Object> params) {
        Rule rule = findRule(name)

        Bindings binding = scriptEngine.createBindings()
        binding.put(PARAMS, params)

        String script = "def ${rule.name}($PARAMS) { return ${rule.expression} }"
        scriptEngine.eval(script, binding)

        boolean flag = scriptEngine.invokeFunction(name, params)

        if (flag) {
            return EvaluateResult.trueResult(rule.value)
        } else {
            return EvaluateResult.falseResult()
        }
    }

    private Rule findRule(String name) {
        ruleRepository.findByName(name)
    }
}
