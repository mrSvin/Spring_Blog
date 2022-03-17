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

}
