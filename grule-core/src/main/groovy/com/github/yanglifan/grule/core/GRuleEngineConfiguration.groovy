package com.github.yanglifan.grule.core

import com.github.yanglifan.grule.core.repository.RuleRepository
import org.springframework.boot.autoconfigure.AutoConfigurationPackage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@AutoConfigurationPackage
@Configuration
class GRuleEngineConfiguration {
    @Bean
    GRuleEngine ruleEngine(RuleRepository ruleRepository) {
        GRuleEngine ruleEngine = new GRuleEngine(ruleRepository)
        ruleEngine.init()
        return ruleEngine
    }
}
