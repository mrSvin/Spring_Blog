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

import static java.lang.Math.abs;

@Service
public class PostService {


    private final PostRepository postRepository;
    private final PostVotesRepository postVotesRepository;
    private final PostCommentRepository postCommentRepository;
    private String activePostTrue = "1";
    private String activeModerationTrue = "ACCEPTED";


    public PostService(PostRepository postRepository, PostVotesRepository postVotesRepository, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postVotesRepository = postVotesRepository;
        this.postCommentRepository = postCommentRepository;
    }

    public PostsResponse getPosts(int offset, int limit, String mode) {
        PostsResponse postsResponse = new PostsResponse();

        //Выдаем ответ с количеством постов
        int countPost = countPost(offset, limit);
        postsResponse.setCounts(countPost);

        //Собираем сущности DTO
        List<PostDetailsDto> dto = getPostDetailsDto();
        //Сортируем сущности в зависимости от выбранного типа
        List<PostDetailsDto> sortedDto = sortPosts(dto, mode);

        postsResponse.setPosts(sortedDto);

        return postsResponse;
    }

    private int countPost(int offset, int limit) {

        List<String> postsActive = new ArrayList<>();
        List<String> postsModeration = new ArrayList<>();
        postRepository.findAll().forEach(postRepository -> postsActive.add(postRepository.getIs_active()));
        postRepository.findAll().forEach(tagRepository -> postsModeration.add(tagRepository.getModeration_status()));

        if (offset > postsActive.size()) {
            offset = postsActive.size();
        }
        if (limit > postsActive.size()) {
            limit = postsActive.size();
        }

        int countPosts = 0;
        //Считаем и выводим количество постов с учетом всех фильтров
        for (int i = offset; i < limit; i++) {
            if (postsActive.get(i).equals(activePostTrue) && postsModeration.get(i).equals(activeModerationTrue)) {
                countPosts++;
            }
        }
        return countPosts;
    }

    public List<PostDetailsDto> sortPosts(List<PostDetailsDto> result, String mode) {
        List<PostDetailsDto> sorterResult;
        //best
        if (mode.equals("best")) {
            sorterResult = result
                    .stream()
                    .sorted(Comparator.comparingInt(PostDetailsDto::getLikeCount))
                    .collect(Collectors.toList());

            Collections.reverse(sorterResult);
        } else if (mode.equals("popular")) {
            sorterResult = result
                    .stream()
                    .sorted(Comparator.comparingInt(PostDetailsDto::getCommentCount))
                    .collect(Collectors.toList());

            Collections.reverse(sorterResult);
        } else if (mode.equals("recent")) {
            sorterResult = result
                    .stream()
                    .sorted(Comparator.comparingLong(PostDetailsDto::getTimestamp))
                    .collect(Collectors.toList());
        } else if (mode.equals("early")) {
            sorterResult = result
                    .stream()
                    .sorted(Comparator.comparingLong(PostDetailsDto::getTimestamp))
                    .collect(Collectors.toList());

            Collections.reverse(sorterResult);
        } else {
            sorterResult = result;
        }

        return sorterResult;
    }

    public List<PostDetailsDto> getPostDetailsDto() {
        List<PostDetailsDto> result = ((List<Post>) postRepository.findAll())
                .stream()
                .map(this::postDetailsDTO)
                .collect(Collectors.toList());

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

}
