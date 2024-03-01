package com.testus.testus.service;

import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.dto.post.PostThumbnailDto;
import com.testus.testus.repository.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepo postRepo;


    @Transactional(readOnly = true)
    public ResponseDto<List<PostThumbnailDto>> getLatestPost(String category) {
        return new ResponseDto<>(Code.SUCCESS, postRepo.getLatestPost(category));


    }

    @Transactional(readOnly = true)
    public ResponseDto<List<PostThumbnailDto>> getPopularPost(String category) {
        return new ResponseDto<>(Code.SUCCESS, postRepo.getPopularPost(category));

    }
}
