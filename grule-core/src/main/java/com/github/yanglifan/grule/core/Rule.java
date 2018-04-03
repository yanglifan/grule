package com.github.yanglifan.grule.core;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Yang Lifan
 */
@Data
@Entity
public class Rule {
    @Id
    private Long id;
    private String name;
    private String expression;
    private String value;
}
