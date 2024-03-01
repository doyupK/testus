package com.testus.testus.repository;

import com.testus.testus.dto.post.PostThumbnailDto;

import java.util.List;

public interface PostRepoCustom {
    List<PostThumbnailDto> getLatestPost(String category);

    List<PostThumbnailDto> getPopularPost(String category);

}
