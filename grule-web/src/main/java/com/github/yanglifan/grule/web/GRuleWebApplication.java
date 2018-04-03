package com.github.yanglifan.grule.web;

import com.github.yanglifan.grule.core.GRuleEngine;
import com.github.yanglifan.grule.core.GRuleEngineConfiguration;
import com.github.yanglifan.grule.core.domain.EvaluateResult;
import com.github.yanglifan.grule.core.domain.Rule;
import com.github.yanglifan.grule.core.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Import(GRuleEngineConfiguration.class)
@SpringBootApplication
public class GRuleWebApplication implements CommandLineRunner {
    @Autowired
    private RuleRepository ruleRepository;

    public static void main(String[] args) {
        SpringApplication.run(GRuleWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Rule rule = new Rule();
        rule.setName("test");
        rule.setExpression("p.name == 'tom' && (p.length as Integer) > 5");
        rule.setValue("success");

        ruleRepository.save(rule);
    }

    @RequestMapping("/rules")
    @RestController
    class RuleController {
        @Autowired
        private GRuleEngine ruleEngine;

        @RequestMapping(value = "/{ruleName}/evaluate", method = RequestMethod.GET)
        public EvaluateResult evaluate(@PathVariable String ruleName, @RequestParam Map<String, Object> params) {
            params.put("length", 5);
            String evalResult = ruleEngine.evaluate(ruleName, params);
            EvaluateResult evaluateResult = new EvaluateResult();
            evaluateResult.setValue(evalResult);
            return evaluateResult;
        }
    }
}
