package com.github.yanglifan.grule.core;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Yang Lifan
 */
public interface RuleRepository extends CrudRepository<Rule, Long> {
    Rule findByName(String name);
}
