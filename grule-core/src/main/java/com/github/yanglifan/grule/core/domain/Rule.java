package com.github.yanglifan.grule.core.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Yang Lifan
 */
@Data
@Entity
public class Rule {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String expression;
    private String value;
}
