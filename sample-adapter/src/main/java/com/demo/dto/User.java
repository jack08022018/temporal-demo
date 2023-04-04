package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class User {
    public String name;
}