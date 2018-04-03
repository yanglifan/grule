package com.github.yanglifan.grule.core.repository

import com.github.yanglifan.grule.core.domain.Rule
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

/**
 * @author Yang Lifan
 */
@Component
interface RuleRepository extends CrudRepository<Rule, Long> {
    Rule findByName(String name)
}
