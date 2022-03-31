package main.service;

import main.api.response.*;
import main.model.*;
import main.repository.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostVotesRepository postVotesRepository;
    private final PostCommentRepository postCommentRepository;
    private final TagRepository tagRepository;
    private final Tag2postRepository tag2postRepository;
    private final UsersRepository usersRepository;

    public PostService(PostRepository postRepository, PostVotesRepository postVotesRepository, PostCommentRepository postCommentRepository, TagRepository tagRepository, Tag2postRepository tag2postRepository, UsersRepository usersRepository) {

        this.postRepository = postRepository;
        this.postVotesRepository = postVotesRepository;
        this.postCommentRepository = postCommentRepository;
        this.tagRepository = tagRepository;
        this.tag2postRepository = tag2postRepository;
        this.usersRepository = usersRepository;
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
        } else {
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

        //Cчитаем лайки и дислайки
        int likeCount = postVotesRepository.findMyPostVotesLikes(post.getId(),1);
        int dislikeCount = postVotesRepository.findMyPostVotesLikes(post.getId(),-1);

            postDetailsDto.setLikeCount(likeCount);
            postDetailsDto.setDislikeCount(dislikeCount);


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

    public PostInfoResponse getPostsById(int id) {

        PostInfoResponse postInfoResponse = new PostInfoResponse();
        Post postInfo = postRepository.findPostId(id).get(0);

        postInfoResponse.setId(id);

        Date datePost = postInfo.getTime();
        postInfoResponse.setTimestamp(datePost.getTime()/1000);

        if ((postInfo.getIs_active() == 1)) {
            postInfoResponse.setActive(true);
        } else {
            postInfoResponse.setActive(false);
        }

        Map<String, Object> userDetail = new HashMap<>();
        userDetail.put("id", postInfo.getUser().getId());
        userDetail.put("name", postInfo.getUser().getName());
        postInfoResponse.setUser(userDetail);

        postInfoResponse.setTitle(postInfo.getTitle());

        postInfoResponse.setText(postInfo.getText());

        postInfoResponse.setLikeCount(postVotesRepository.findMyPostVotesLikes(id, 1));

        postInfoResponse.setDislikeCount(postVotesRepository.findMyPostVotesLikes(id, -1));

        postInfoResponse.setViewCount(postInfo.getView_count());

        postInfoResponse.setComments(getPostCommentsDTO(id));

        List<String> tagsName = new ArrayList<>();
        List<Integer> tagsId  = tag2postRepository.findTagsByPostId(id);
        for (int i=0; i< tagsId.size(); i++) {
            String tagName = tagRepository.findTagsById(tagsId.get(i));
            tagsName.add(tagName);
        }
        postInfoResponse.setTags(tagsName);

        return postInfoResponse;
    }

    public List<PostCommentDto> getPostCommentsDTO(int id) {

        List<PostCommentDto> listComments = new ArrayList<>();

        for (int i =0; i<postCommentRepository.findCommentByPostId(id).size(); i++) {
            PostCommentDto postCommentDto = new PostCommentDto();
            PostComment postComment = postCommentRepository.findCommentByPostId(id).get(i);

            postCommentDto.setId(postComment.getId());
            postCommentDto.setTimestamp(postComment.getTime().getTime() / 1000);
            postCommentDto.setText(postComment.getText());

            Map<String, Object> userDetail = new HashMap<>();
            userDetail.put("id", postComment.getUser().getId());
            userDetail.put("name", postComment.getUser().getName());
            postCommentDto.setUser(userDetail);

            listComments.add(postCommentDto);
        }

        return listComments;
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
            postRequest = postRepository.findMyPostByInactive(id, limit, offset);
        } else if (status.equals("pending")) {
            isActive = 1;
            status = "NEW";
            postRequest = postRepository.findMyPost(status, id, isActive, limit, offset);
        } else if (status.equals("declined")) {
            isActive = 1;
            status = "DECLINED";
            postRequest = postRepository.findMyPost(status, id, isActive, limit, offset);
        } else if (status.equals("published")) {
            isActive = 1;
            status = "ACCEPTED";
            postRequest = postRepository.findMyPost(status, id, isActive, limit, offset);
        } else {
            isActive = 0;
            status = "";
            postRequest = postRepository.findMyPost(status, id, isActive, limit, offset);
        }

        result = (postRequest)
                .stream()
                .map(this::postDetailsDTO)
                .collect(Collectors.toList());

        return result;
    }

    public PostAddResponse addPost(Timestamp timestamp, int isActive, String title, List<String> tags, String text, String authCoocie) {
        PostAddResponse postAddResponse = new PostAddResponse();

        Map<String, String> error = new HashMap<>();

        if (title.length() < 3) {
            postAddResponse.setResult(false);
            error.put("title", "Заголовок не установлен");
            postAddResponse.setErrors(error);
        } else if (text.length() < 50) {
            postAddResponse.setResult(false);
            error.put("text", "Текст публикации слишком короткий");
            postAddResponse.setErrors(error);
        } else {
            postAddResponse.setResult(true);

            addPostToDB(timestamp, isActive, title, tags, text, authCoocie);
        }

        return postAddResponse;
    }

    private Date searchTimeForPost(Timestamp timestamp) {
        if (timestamp.getTime() * 1000 < System.currentTimeMillis()) {
            return new Date(System.currentTimeMillis());
        } else {
            return new Date(timestamp.getTime() * 1000);
        }
    }

    private void addPostToDB(Timestamp timestamp, int isActive, String title, List<String> tags, String text, String authCoocie) {
        Post post = new Post();
        post.setIs_active(isActive);
        post.setModeration_status("NEW");
        post.setText(text);

        //Создаем в БД Пост
        post.setTime(searchTimeForPost(timestamp));
        post.setTitle(title);
        post.setView_count(0);
        int id = LoginService.sessions.get(authCoocie);
        User user = new User();
        user.setId(id);
        post.setUser(user);
        postRepository.save(post);

        //Сохранаяем в БД Тэги
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = new Tag();
            Tag2post tag2post = new Tag2post();

            int searchTag = tagRepository.findTag(tags.get(i)).size();
            if (searchTag > 0) {
                tag.setId(tagRepository.findTag(tags.get(i)).get(0));
                tag2post.setPost(post);
                tag2post.setTag(tag);
                tag2postRepository.save(tag2post);
            } else {
                tag.setName(tags.get(i));
                tagRepository.save(tag);

                tag2post.setPost(post);
                tag2post.setTag(tag);
                tag2postRepository.save(tag2post);
            }
        }
    }

    public PostAddResponse editPost(Timestamp timestamp, int isActive, String title, List<String> tags, String text, String authCoocie, int idPost) {
        PostAddResponse postAddResponse = new PostAddResponse();

        Map<String, String> error = new HashMap<>();

        if (title.length() < 3) {
            postAddResponse.setResult(false);
            error.put("title", "Заголовок не установлен");
            postAddResponse.setErrors(error);
        } else if (text.length() < 50) {
            postAddResponse.setResult(false);
            error.put("text", "Текст публикации слишком короткий");
            postAddResponse.setErrors(error);
        } else {
            postAddResponse.setResult(true);

            editPostToDB(timestamp, isActive, title, tags, text, authCoocie, idPost);
        }

        return postAddResponse;
    }

    private void editPostToDB(Timestamp timestamp, int isActive, String title, List<String> tags, String text, String authCoocie, int idPost) {

        int id = LoginService.sessions.get(authCoocie);

        if (usersRepository.findUserInfo(id).getIs_moderator() == 1) {
            postRepository.editPostModerator(idPost, isActive, text, searchTimeForPost(timestamp), title);
        } else {
            postRepository.editPostUser(idPost, isActive, text, searchTimeForPost(timestamp), title);
        }

        //Сохранаяем в БД Тэги
        Post post = new Post();
        post.setId(idPost);
        for (int i = 0; i < tags.size(); i++) {
            Tag tag = new Tag();
            Tag2post tag2post = new Tag2post();

            int searchTag = tagRepository.findTag(tags.get(i)).size();
            if (searchTag > 0) {
                tag.setId(tagRepository.findTag(tags.get(i)).get(0));
                tag2post.setPost(post);
                tag2post.setTag(tag);
                tag2postRepository.save(tag2post);
            } else {
                tag.setName(tags.get(i));
                tagRepository.save(tag);

                tag2post.setPost(post);
                tag2post.setTag(tag);
                tag2postRepository.save(tag2post);
            }
        }

    }

    public CommentAddResponse addComment(String parentId, Integer postId, String text, String authCoocie) {
        CommentAddResponse commentAddResponse = new CommentAddResponse();
        Map<String,String> errors= new HashMap<>();
        int id = LoginService.sessions.get(authCoocie);

        int intParentId;
        if (parentId == null) {
            intParentId=0;
        } else {
            intParentId=Integer.parseInt(parentId);
        }

        if (text.length()<5) {
            commentAddResponse.setResult(false);
            errors.put("text","Текст комментария не задан или слишком короткий");
            commentAddResponse.setErrors(errors);
        } else if (postRepository.findPostId(postId).size()==0) {
            commentAddResponse.setResult(false);
            errors.put("text","Данного поста не существует");
            commentAddResponse.setErrors(errors);
        } else if (postCommentRepository.findCommentId(intParentId).size() == 0 && parentId != null) {
            commentAddResponse.setResult(false);
            errors.put("text","Данного комментария не существует");
            commentAddResponse.setErrors(errors);
        } else {
            Date timeComment = new Date(System.currentTimeMillis());
            if (parentId == null) {
                postCommentRepository.addCommentPost(text, timeComment, id, postId);
            } else {
                postCommentRepository.addCommentParent(text, timeComment, id, postId, Integer.valueOf(parentId));
            }

            commentAddResponse.setResult(true);
            commentAddResponse.setId(345);

        }

    return commentAddResponse;
    }

    public LikeResponse addLike(int postId, String authCoocie) {
        LikeResponse likeResponse = new LikeResponse();
        int id = LoginService.sessions.get(authCoocie);

        if (postVotesRepository.findUserVotesLikes(postId, 1, id)==0 && postVotesRepository.findUserVotesLikes(postId, -1, id)==0) {
            Date timeLike = new Date(System.currentTimeMillis());
            postVotesRepository.addLike(timeLike,1, postId, id);
            likeResponse.setResult(true);
        } else if (postVotesRepository.findUserVotesLikes(postId, 1, id)==1 && postVotesRepository.findUserVotesLikes(postId, -1, id)==0) {
            likeResponse.setResult(false);
        } else if (postVotesRepository.findUserVotesLikes(postId, 1, id)==0 && postVotesRepository.findUserVotesLikes(postId, -1, id)==1) {
            postVotesRepository.changeLike(1, postId, id);
            likeResponse.setResult(true);
        }

        return likeResponse;
    }

    public LikeResponse addDislike(int postId, String authCoocie) {
        LikeResponse likeResponse = new LikeResponse();
        int id = LoginService.sessions.get(authCoocie);

        if (postVotesRepository.findUserVotesLikes(postId, 1, id)==0 && postVotesRepository.findUserVotesLikes(postId, -1, id)==0) {
            Date timeLike = new Date(System.currentTimeMillis());
            postVotesRepository.addLike(timeLike,-1, postId, id);
            likeResponse.setResult(true);
        } else if (postVotesRepository.findUserVotesLikes(postId, 1, id)==0 && postVotesRepository.findUserVotesLikes(postId, -1, id)==1) {
            likeResponse.setResult(false);
        } else if (postVotesRepository.findUserVotesLikes(postId, 1, id)==1 && postVotesRepository.findUserVotesLikes(postId, -1, id)==0) {
            postVotesRepository.changeLike(-1, postId, id);
            likeResponse.setResult(true);
        }

        return likeResponse;
    }

}
