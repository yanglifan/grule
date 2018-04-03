package com.github.yanglifan.grule.core;

import com.github.yanglifan.grule.core.domain.Rule;
import com.github.yanglifan.grule.core.repository.RuleRepository;
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
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GRuleEngineConfiguration.class)
@DataJpaTest
public class GRuleEngineTest {
    @Autowired
    private RuleRepository ruleRepository;

    private GRuleEngine ruleEngine;

    @Before
    public void setUp() throws Exception {
        Rule rule = new Rule();
        rule.setName("test");
        rule.setExpression("p.name == 'tom' && p.size > 5");
        rule.setValue("success");

        ruleRepository.save(rule);

        ruleEngine = new GRuleEngine(ruleRepository);
        ruleEngine.init();
    }

    @Test
    public void basic() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "tom");
        params.put("size", 6);

        String value = ruleEngine.evaluate("test", params);
        assertThat(value, is("success"));
    }
}