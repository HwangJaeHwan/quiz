package com.quiz.request;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PageDTO {

    private int page;
    private String sort;

    public PageDTO() {
        this.page = 1;
        this.sort = "createdTime";
    }

    public PageDTO(int page, String sort) {
        this.page = page;
        this.sort = sort;
    }


    public Pageable getPageable() {

        return PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.DESC, sort));
    }


}
