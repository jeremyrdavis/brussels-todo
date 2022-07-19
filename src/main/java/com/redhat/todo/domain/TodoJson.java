package com.redhat.todo.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record TodoJson(Long id, String title, int order, boolean completed) {
}
