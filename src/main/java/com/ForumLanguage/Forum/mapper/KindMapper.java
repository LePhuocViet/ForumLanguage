package com.ForumLanguage.Forum.mapper;

import com.ForumLanguage.Forum.dto.KindDto;
import com.ForumLanguage.Forum.enity.Kind;

public class KindMapper {

    public static KindDto mapToKindDto(Kind kind, KindDto kindDto) {
        kindDto.setLanguage(kind.getLanguage());
        kindDto.setDetail(kind.getDetail());
        return kindDto;
    }

    public static Kind mapToKind(Kind kind, KindDto kindDto) {
        kind.setLanguage(kindDto.getLanguage());
        kind.setDetail(kindDto.getDetail());
        return kind;
    }
}
