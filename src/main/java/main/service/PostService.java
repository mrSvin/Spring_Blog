package main.service;

import main.api.response.PostDetailsDto;
import main.api.response.PostsResponse;
import main.model.*;
import main.repository.PostRepository;
import main.repository.PostVotesRepository;
import main.repository.PostCommentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostVotesRepository postVotesRepository;
    private final PostCommentRepository postCommentRepository;

    public PostService(PostRepository postRepository, PostVotesRepository postVotesRepository, PostCommentRepository postCommentRepository) {

        this.postRepository = postRepository;
        this.postVotesRepository = postVotesRepository;
        this.postCommentRepository = postCommentRepository;
    }

    public PostsResponse getPosts(int offset, int limit, String mode) {
        PostsResponse postsResponse = new PostsResponse();

        //Собираем сущности DTO
        List<PostDetailsDto> dto = getPostDetailsDto(offset, limit, mode);
        //Выдаем ответ с количеством постов
        int countPost = dto.size();
        postsResponse.setCounts(countPost);
        postsResponse.setPosts(dto);

        return postsResponse;
    }

    public List<PostDetailsDto> getPostDetailsDto(int offset, int limit, String mode) {
        List<PostDetailsDto> result;
        if ("early".equals(mode)) {
            result = (postRepository.findByEarly(limit, offset))
                    .stream()
                    .map(this::postDetailsDTO)
                    .collect(Collectors.toList());
        } else if ("recent".equals(mode)) {
            result = (postRepository.findByRecent(limit, offset))
                    .stream()
                    .map(this::postDetailsDTO)
                    .collect(Collectors.toList());
        } else if ("best".equals(mode)) {
            result = (postRepository.findByBest(limit, offset))
                    .stream()
                    .map(this::postDetailsDTO)
                    .collect(Collectors.toList());
        } else if ("popular".equals(mode)) {
            result = (postRepository.findByPopular(limit, offset))
                    .stream()
                    .map(this::postDetailsDTO)
                    .collect(Collectors.toList());
        }
        else {
            result = (postRepository.findByEarly(limit, offset))
                    .stream()
                    .map(this::postDetailsDTO)
                    .collect(Collectors.toList());
        }

        return result;
    }
    private PostDetailsDto postDetailsDTO(Post post) {
        PostDetailsDto postDetailsDto = new PostDetailsDto();
        postDetailsDto.setId(post.getId());
        postDetailsDto.setTimestamp(post.getTime().getTime() / 1000);
        postDetailsDto.setTitle(post.getTitle());

        String annotation = post.getText();
        if (annotation.length() > 150) {
            annotation = annotation.substring(0, 150) + "...";
        }
        postDetailsDto.setAnnounce(annotation);
        postDetailsDto.setViewCount(post.getView_count());

        List<Integer> postsLikeCount = new ArrayList<>();
        postVotesRepository.findAll().forEach(postVotesRepository -> postsLikeCount.add(postVotesRepository.getValue()));
        int likeCount = postsLikeCount.get(post.getId() - 1);
        if (likeCount > 0) {
            postDetailsDto.setLikeCount(likeCount);
            postDetailsDto.setDislikeCount(0);
        } else {
            postDetailsDto.setLikeCount(0);
            postDetailsDto.setDislikeCount(Math.abs(likeCount));
        }

        List<Post> postCommentPost = new ArrayList<>();
        postCommentRepository.findAll().forEach(postCommentRepository -> postCommentPost.add(postCommentRepository.getPost()));
        //Находим и выводим количество комментариев к посту
        int countComments = 0;
        for (int k = 0; k < postCommentPost.size(); k++) {
            if (post.getId() == postCommentPost.get(k).getId()) {
                countComments++;
            }
        }
        postDetailsDto.setCommentCount(countComments);
        Map<String, Object> user = new HashMap<>();
        user.put("id", post.getUser().getId());
        user.put("user", post.getUser().getName());
        postDetailsDto.setUser(user);

        return postDetailsDto;
    }

    public PostsResponse getPostsSearch(int offset, int limit, String query) {
        PostsResponse postsResponse = new PostsResponse();

        //Собираем сущности DTO
        List<PostDetailsDto> dto = getPostSearchDetailsDto(offset, limit, query);
        //Выдаем ответ с количеством постов
        int countPost = dto.size();
        postsResponse.setCounts(countPost);
        postsResponse.setPosts(dto);

        return postsResponse;
    }
    public List<PostDetailsDto> getPostSearchDetailsDto(int offset, int limit, String query) {

        query = "%" + query + "%";

        List<PostDetailsDto> result;
        result = (postRepository.findQuery(query, limit, offset))
                .stream()
                .map(this::postDetailsDTO)
                .collect(Collectors.toList());

        return result;
    }

    public PostsResponse getPostsByDate(int offset, int limit, String date) {
        PostsResponse postsResponse = new PostsResponse();

        //Собираем сущности DTO
        List<PostDetailsDto> dto = getPostByDateDto(offset, limit, date);
        //Выдаем ответ с количеством постов
        int countPost = dto.size();
        postsResponse.setCounts(countPost);
        postsResponse.setPosts(dto);

        return postsResponse;
    }
    public List<PostDetailsDto> getPostByDateDto(int offset, int limit, String date) {

        List<PostDetailsDto> result;
        result = (postRepository.findByDate(date, limit, offset))
                .stream()
                .map(this::postDetailsDTO)
                .collect(Collectors.toList());

        return result;
    }

    public PostsResponse getPostsByTag(int offset, int limit, String tag) {
        PostsResponse postsResponse = new PostsResponse();

        //Собираем сущности DTO
        List<PostDetailsDto> dto = getPostByTagDto(offset, limit, tag);
        //Выдаем ответ с количеством постов
        int countPost = dto.size();
        postsResponse.setCounts(countPost);
        postsResponse.setPosts(dto);

        return postsResponse;
    }
    public List<PostDetailsDto> getPostByTagDto(int offset, int limit, String tag) {

        List<PostDetailsDto> result;
        result = (postRepository.findByTag(tag, limit, offset))
                .stream()
                .map(this::postDetailsDTO)
                .collect(Collectors.toList());

        return result;
    }

    public PostsResponse getPostsById(int id) {
        PostsResponse postsResponse = new PostsResponse();

        //Собираем сущности DTO
        List<PostDetailsDto> dto = getPostById(id);
        //Выдаем ответ с количеством постов
        int countPost = dto.size();
        postsResponse.setCounts(countPost);
        postsResponse.setPosts(dto);

        return postsResponse;
    }
    public List<PostDetailsDto> getPostById(int id) {

        List<PostDetailsDto> result;
        result = (postRepository.findById(id))
                .stream()
                .map(this::postDetailsDTO)
                .collect(Collectors.toList());

        return result;
    }

    public PostsResponse getPostsByModeration(int offset, int limit, String status) {
        PostsResponse postsResponse = new PostsResponse();

        //Собираем сущности DTO
        List<PostDetailsDto> dto = getPostByModeration(offset, limit, status);
        //Выдаем ответ с количеством постов
        int countPost = dto.size();
        postsResponse.setCounts(countPost);
        postsResponse.setPosts(dto);

        return postsResponse;
    }
    public List<PostDetailsDto> getPostByModeration(int offset, int limit, String status) {

        List<PostDetailsDto> result;
        result = (postRepository.findByStatus(status, limit, offset))
                .stream()
                .map(this::postDetailsDTO)
                .collect(Collectors.toList());

        return result;
    }

    public PostsResponse getPostsMy(int offset, int limit, String status, String authCoocie) {
        PostsResponse postsResponse = new PostsResponse();

        //Собираем сущности DTO
        List<PostDetailsDto> dto = getPostMy(offset, limit, status, authCoocie);
        //Выдаем ответ с количеством постов
        int countPost = dto.size();
        postsResponse.setCounts(countPost);
        postsResponse.setPosts(dto);

        return postsResponse;
    }
    public List<PostDetailsDto> getPostMy(int offset, int limit, String status, String authCoocie) {

        List<PostDetailsDto> result;
        int id = LoginService.sessions.get(authCoocie);
        List<Post> postRequest;

        int isActive;
        if (status.equals("inactive")) {
            postRequest = postRepository.findMyPostByInactive( id, limit, offset);
        } else if (status.equals("pending")) {
            isActive=1;
            status = "NEW";
            postRequest = postRepository.findMyPost(status, id, isActive, limit, offset);
        } else if (status.equals("declined")) {
            isActive=1;
            status = "DECLINED";
            postRequest = postRepository.findMyPost(status, id, isActive, limit, offset);
        } else if (status.equals("published")) {
            isActive=1;
            status = "ACCEPTED";
            postRequest = postRepository.findMyPost(status, id, isActive, limit, offset);
        } else {
            isActive =0;
            status = "";
            postRequest = postRepository.findMyPost(status, id, isActive, limit, offset);
        }

        result = (postRequest)
                .stream()
                .map(this::postDetailsDTO)
                .collect(Collectors.toList());

        return result;
    }


}
