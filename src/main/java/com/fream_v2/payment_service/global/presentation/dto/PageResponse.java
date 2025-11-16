package com.fream_v2.payment_service.global.presentation.dto;

import com.fream_v2.payment_service.global.common.dto.PageInfo;
import com.fream_v2.payment_service.global.presentation.exception.CommonException;
import com.fream_v2.payment_service.global.presentation.exception.GlobalErrorCode;
import lombok.Getter;

import java.util.List;
import java.util.Collections;

@Getter
public class PageResponse<T> {
    private final List<T> content;
    private final PageInfo pageInfo;

    private PageResponse(List<T> content, PageInfo pageInfo) {
        validatePageInfo(pageInfo);
        this.content = content != null ? content : Collections.emptyList();
        this.pageInfo = pageInfo;
    }

    public static <T> PageResponse<T> of(List<T> content, PageInfo pageInfo) {
        return new PageResponse<>(content, pageInfo);
    }

    private void validatePageInfo(PageInfo pageInfo) {
        if (pageInfo == null) {
            throw new CommonException(GlobalErrorCode.PAGE_INFO_NULL);
        }
    }

}
