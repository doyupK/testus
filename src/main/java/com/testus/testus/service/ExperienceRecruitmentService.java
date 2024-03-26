package com.testus.testus.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testus.testus.common.response.ResponseDto;
import com.testus.testus.common.response.exception.Code;
import com.testus.testus.common.response.exception.CustomException;
import com.testus.testus.domain.ExperienceRecruitment;
import com.testus.testus.domain.TestJoinMapping;
import com.testus.testus.domain.User;
import com.testus.testus.dto.post.ExperienceRecruitmentThumbnailDto;
import com.testus.testus.repository.ExperienceRecruitmentRepo;
import com.testus.testus.repository.TestJoinMapRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceRecruitmentService {

    private final ExperienceRecruitmentRepo experienceRecruitmentRepo;
    private final ObjectMapper ob;
    private final UserService userService;
    private final TestJoinMapRepo testJoinMapRepo;


    @Transactional(readOnly = true)
    public ResponseDto<List<ExperienceRecruitmentThumbnailDto>> getLatestPost(String category) {
        return new ResponseDto<>(Code.SUCCESS, experienceRecruitmentRepo.getLatestPost(category));


    }

    @Transactional(readOnly = true)
    public ResponseDto<List<ExperienceRecruitmentThumbnailDto>> getPopularPost(String category) {
        return new ResponseDto<>(Code.SUCCESS, experienceRecruitmentRepo.getPopularPost(category));

    }

    @Transactional(readOnly = true)
    public Page<ExperienceRecruitment.MyPostDataResponse> getMyTest(User user, PageRequest pageRequest) {
        return experienceRecruitmentRepo.getMyTest(user, pageRequest);

    }

    @Transactional
    public Object createRecruitment(ExperienceRecruitment.CreatePostDto dto, User user) throws JsonProcessingException {
        //
        // TODO: 3/9/24 S3 upload logic will be add later
        experienceRecruitmentRepo.save(ExperienceRecruitment.builder()
                .title(dto.getTitle())
                .thumbnail(ob.writeValueAsString(dto.getThumbnailUrl()))
                .serviceUrl(dto.getTestUrl())
                .exposureType(dto.isExposureType() ? 'O' : 'I')
                .joinLimit(dto.getJoinLimit())
                .currentJoinCount(0)
                .contents(dto.getTestContents())
                .category(dto.getExperienceRecruitmentCategory())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .ageLimit(dto.getAge())
                .createDate(LocalDate.now())
                .createUser(user)
                .build());
        return new ResponseDto<>(Code.SUCCESS);
    }

    @Transactional
    public ResponseDto<Code> addTester(ExperienceRecruitment.AddTesterDto dto, User user) {
        if (dto.getUserEmail().equals(user.getUserEmail())) {
            throw new CustomException(Code.ADD_SELF_ERROR);
        } else {
            User findUser = userService.addTesterBeforeCheck(dto.getUserEmail());
            ExperienceRecruitment experienceRecruitment =
                    experienceRecruitmentRepo.findById(dto.getSeq()).orElseThrow(
                            () -> new CustomException(Code.BAD_REQUEST)
                    );

            testJoinMapRepo.save(
                    TestJoinMapping.builder()
                            .experienceRecruitment(experienceRecruitment)
                            .user(findUser)
                            .build()
            );
            return new ResponseDto<>(Code.SUCCESS);
        }
    }

    @Transactional(readOnly = true)
    public Object getTestList(int lastId, String category, String tag, String sortBy, User user) {

        if (user == null){
            experienceRecruitmentRepo.getTestList(lastId, category, tag, sortBy);
        } else {
            experienceRecruitmentRepo.getTestList(lastId, category, tag, sortBy, user);
        }
        return new ResponseDto<>(null);
    }
}
