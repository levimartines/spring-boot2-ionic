package com.levimartines.cursomc.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class RestResponsePage<T> extends PageImpl<T> {
	private static final long serialVersionUID = 1L;

	public RestResponsePage() {
		super(new ArrayList());
	}

	public RestResponsePage(List<T> content) {
		super(content);
	}

	@JsonCreator(
			mode = JsonCreator.Mode.PROPERTIES
	)
	public RestResponsePage(@JsonProperty("content") List<T> content, @JsonProperty("number") int number, @JsonProperty("size") int size, @JsonProperty("totalElements") Long totalElements, @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last, @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort, @JsonProperty("first") boolean first, @JsonProperty("numberOfElements") int numberOfElements) {
		super((List) (content == null ? new ArrayList() : content), PageRequest.of(number, size < 1 ? 1 : size), totalElements == null ? 0L : totalElements);
	}

	public RestResponsePage(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}
}
