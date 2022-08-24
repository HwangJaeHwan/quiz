package com.quiz.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchDTO {

    String type;

    String content;
    @Builder
    public SearchDTO(String type, String content) {
        this.type = type;
        this.content = content;
    }
}
