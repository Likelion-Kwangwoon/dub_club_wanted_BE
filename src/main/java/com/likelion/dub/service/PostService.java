package com.likelion.dub.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.likelion.dub.common.BaseException;
import com.likelion.dub.common.BaseResponse;
import com.likelion.dub.common.BaseResponseStatus;
import com.likelion.dub.domain.Club;
import com.likelion.dub.domain.Image;
import com.likelion.dub.domain.Member;
import com.likelion.dub.domain.Post;
import com.likelion.dub.domain.dto.PostEditRequest;
import com.likelion.dub.exception.AppException;
import com.likelion.dub.exception.Errorcode;
import com.likelion.dub.repository.ImageRepository;
import com.likelion.dub.repository.MemberRepository;
import com.likelion.dub.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final FileHandler fileHandler;


    public List<Post> getAllClubs() {
        return this.postRepository.findAll();
    }
    public BaseResponse<String> writePost(String title, String content,int category, List<MultipartFile> files) throws BaseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //jwt token 오류
        if (authentication == null || !authentication.isAuthenticated()) {
            return new BaseResponse(BaseResponseStatus.JWT_TOKEN_ERROR);
        }
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow();
        Club club = member.getClub();
        String clubName =  club.getClubName();

        //이 club 글이 있으면 작성 불가
        postRepository.findByClubName(clubName)
                .ifPresent(post -> {
                        throw new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID);
                });

        List<Image> imageList = fileHandler.parseFileInfo(files);
        Post.PostBuilder postBuilder = Post.builder()
                .clubName(clubName)
                .title(title)
                .content(content)
                .category(category);
        //Post post = Post.builder()
        //          .clubName(clubName)
        //          .image(imageList)
        for (Image image : imageList) {
            postBuilder.addImage(image);
        }

        Post post = postBuilder.build();


        //파일이 존재할 때만 처리
            if(!imageList.isEmpty()){
                for(Image image : imageList){
                    imageRepository.save(image);
                    post.addImage(image);
                }
            }
            postRepository.save(post);
        return new BaseResponse<>("글 작성 성공");
    }

    public Post readPost(Long id) throws BaseException{
        return postRepository.findById(id)
                .orElseThrow(() ->
                        new BaseException(BaseResponseStatus.NOT_EXISTS_POST)

        );

    }


    public BaseResponse<String> deletePost(Long id) throws BaseException {
        // 저장 되어있는 post 가져오기
        Post post = postRepository.findById(id).orElseThrow(() ->
                new BaseException(BaseResponseStatus.NOT_EXISTS_POST));

        // 로그인 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //jwt token 오류
        if (authentication == null || !authentication.isAuthenticated()) {
            return new BaseResponse(BaseResponseStatus.JWT_TOKEN_ERROR);
        }
        // 로그인 된 이메일
        String email = authentication.getName();
        // 로그인 된 멤버
        Member member = memberRepository.findByEmail(email).orElseThrow();
        // 로그인 된 클럽
        String clubName = member.getClub().getClubName();
        // 로그인 된 post
        Post member_post = postRepository.findByClubName(clubName).orElseThrow();


        //post id 가 같은지 검사, 같으면 삭제, 다르면 오류출력
        if (member_post.getId() == id) {
            postRepository.deleteById(id);
            return new BaseResponse<>("삭제 완료");
        } else {
            return new BaseResponse(BaseResponseStatus.INVALID_MEMBER_JWT);
        }
    }
    public void editPost(String email, String newTitle, String newContent, int newCategory, List<MultipartFile> newfiles) throws BaseException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_SUCH_MEMBER_EXIST));
        String clubName = member.getClub().getClubName();
        Post post = postRepository.findByClubName(clubName)
                .orElseThrow(()-> new BaseException(BaseResponseStatus.FAILED_GET_POST));
        List<Image> imageList = fileHandler.parseFileInfo(newfiles);
        post.setTitle(newTitle);
        post.setContent(newContent);
        post.setCategory(newCategory);
        post.setImage(imageList);
        postRepository.save(post);

    }


//    public Member loadMemberByEmail(String email) throws BaseException {
//        Member selectedUser = postRepository.findByEmail(email)
//                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_EMAIL));
//
//        return selectedUser;
//
//    }

    }



