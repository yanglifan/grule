package com.github.yanglifan.grule.core;

import com.github.yanglifan.grule.core.domain.EvaluateResult;
import com.github.yanglifan.grule.core.domain.Rule;
import com.github.yanglifan.grule.core.repository.RuleRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GRuleEngineConfiguration.class)
@DataJpaTest
public class GRuleEngineTest {
    @Autowired
    private RuleRepository ruleRepository;

    private GRuleEngine ruleEngine;

    @Before
    public void setUp() {
        ruleEngine = new GRuleEngine(ruleRepository);
        ruleEngine.init();
    }

    @After
    public void tearDown() {
        ruleRepository.deleteAll();
    }

    @Test
    public void test1() {
        Rule rule = new Rule();
        rule.setName("test");
        rule.setExpression("p.name == 'tom' && p.size > 5");
        rule.setValue("success");

        ruleRepository.save(rule);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "tom");
        params.put("size", 6);

        EvaluateResult result = ruleEngine.evaluate("test", params);
        assertThat(result.getValue(), is("success"));

        params.put("name", "tom");
        params.put("size", 1);

        result = ruleEngine.evaluate("test", params);
        assertThat(result.getType(), is(EvaluateResult.ResultType.FALSE));
    }

    @Test
    public void test2() {
        Rule rule = new Rule();
        rule.setName("test");
        rule.setExpression("p.name.startsWith('x') && p.age > 18");
        rule.setValue("success");

        ruleRepository.save(rule);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "x-man");
        params.put("age", 6);

        EvaluateResult result = ruleEngine.evaluate("test", params);
        assertThat(result.getType(), is(EvaluateResult.ResultType.FALSE));

        params.put("name", "tom");
        params.put("age", 20);

        result = ruleEngine.evaluate("test", params);
        assertThat(result.getType(), is(EvaluateResult.ResultType.FALSE));

        params.put("name", "x-man");
        params.put("age", 20);

        result = ruleEngine.evaluate("test", params);
        assertThat(result.getType(), is(EvaluateResult.ResultType.TRUE));
    }
}