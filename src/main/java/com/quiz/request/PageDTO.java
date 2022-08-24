package com.quiz.request;

import lombok.Builder;
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
    @Builder
    public PageDTO(int page, String sort) {
        this.page = page;
        this.sort = sort;
    }

    public int getPage() {
        return Math.max(1, page);
    }

    public long getOffset(){
        return (long) (Math.max(1, page) - 1) * 20;
    }



}
