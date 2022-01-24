package main.service;

import main.api.response.PostsResponse;
import main.model.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;

@Service
public class PostService {

    private int offset = 10;
    private int limit = 0;
    private int count = 0;

    private final PostRepository postRepository;
    private final Post_votesRepository post_votesRepository;
    private final Post_commentsRepository post_commentsRepository;

    public PostService(PostRepository postRepository, Post_votesRepository post_votesRepository, Post_commentsRepository post_commentsRepository) {
        this.postRepository = postRepository;
        this.post_votesRepository = post_votesRepository;
        this.post_commentsRepository = post_commentsRepository;
    }

    public PostsResponse getPosts(int offset, int limit, String mode) {

        PostsResponse postsResponse = new PostsResponse();


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

        count = 0;
        //Считаем и выводим количество постов с учетом всех фильтров
        for (int i = offset; i < limit; i++) {
            if (postsActive.get(i).equals("1") && postsModeration.get(i).equals("ACCEPTED")) {
                count++;
            }
        }
        postsResponse.setCounts(count);

        //Собираем в коллекции необходимые данные по постам
        List<Integer> postsId = new ArrayList<>();
        List<Date> postsTime = new ArrayList<>();
        List<String> postsTitle = new ArrayList<>();
        List<String> postsAnnounce = new ArrayList<>();
        List<Integer> postsLikeCount = new ArrayList<>();
        List<Posts> postsCommentPost = new ArrayList<>();
        List<String> postsViewCount = new ArrayList<>();
        List<Integer> userId = new ArrayList<>();
        List<String> userName = new ArrayList<>();

        postRepository.findAll().forEach(postRepository -> postsId.add(postRepository.getId()));
        postRepository.findAll().forEach(postRepository -> postsTime.add(postRepository.getTime()));
        postRepository.findAll().forEach(postRepository -> postsTitle.add(postRepository.getTitle()));
        postRepository.findAll().forEach(postRepository -> postsAnnounce.add(postRepository.getText()));
        post_votesRepository.findAll().forEach(post_votesRepository -> postsLikeCount.add(post_votesRepository.getValue()));
        post_commentsRepository.findAll().forEach(post_commentsRepository -> postsCommentPost.add(post_commentsRepository.getPost()));
        postRepository.findAll().forEach(postRepository -> postsViewCount.add(postRepository.getView_count()));
        postRepository.findAll().forEach(postRepository -> userId.add(postRepository.getUser().getId()));
        postRepository.findAll().forEach(postRepository -> userName.add(postRepository.getUser().getName()));

        //Формируем коллекцию для ответа
        List<Map<String, Object>> resultPosts = new ArrayList<>();
        for (int i = offset; i < limit; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", postsId.get(i));

            // выводим timestamp преобразовава Date в Unix time
            map.put("timestamp", postsTime.get(i).getTime() / 1000);

            Map<String, Object> mapUser = new HashMap<String, Object>();
            mapUser.put("id", userId.get(i));
            mapUser.put("name", userName.get(i));
            map.put("user", mapUser);

            map.put("title", postsTitle.get(i));

            //В зависимости от длины текста выводим анотацию
            String anonce = "";
            if (postsAnnounce.get(i).length() < 150) {
                anonce = postsAnnounce.get(i);
            } else {
                anonce = postsAnnounce.get(i).substring(0, 150) + "...";
            }
            map.put("announce", anonce);

            //Находим и выводим количество лайков к посту
            if (postsLikeCount.get(i) >= 0) {
                map.put("likeCount", postsLikeCount.get(i));
                map.put("dislikeCount", 0);
            } else {
                map.put("likeCount", 0);
                map.put("dislikeCount", postsLikeCount.get(i));
            }

            //Находим и выводим количество комментариев к посту
            int countComments = 0;
            for (int k = offset; k < limit; k++) {
                if (i == postsCommentPost.get(k).getId()) {
                    countComments++;
                }
            }
            map.put("commentCount", countComments);

            map.put("viewCount", postsViewCount.get(i));

            resultPosts.add(map);

        }

        //Сортируем по mode (пузырьком)

        //Сортируем по timestamp
        if (mode.equals("recent")) {
//            System.out.println(resultPosts.get(0).get("timestamp"));
            resultPosts = bubbleSortLong(resultPosts, "timestamp");
            //Делаем реверс для убывния
            resultPosts = resultPosts.subList(0, resultPosts.size());
            Collections.reverse(resultPosts);

        }
        if (mode.equals("popular")) {
            resultPosts = bubbleSortInt(resultPosts, "commentCount");
            //Делаем реверс для убывния
            resultPosts = resultPosts.subList(0, resultPosts.size());
            Collections.reverse(resultPosts);
        }
        if (mode.equals("best")) {
            resultPosts = bubbleSortInt(resultPosts, "likeCount");
            //Делаем реверс для убывния
            resultPosts = resultPosts.subList(0, resultPosts.size());
            Collections.reverse(resultPosts);
        }
        if (mode.equals("early")) {
            resultPosts = bubbleSortLong(resultPosts, "timestamp");
        }


//        System.out.println(resultPosts);

        postsResponse.setPosts(resultPosts);


        return postsResponse;
    }


    private List<Map<String, Object>> bubbleSortLong(List<Map<String, Object>> arr, String value) {
        for (int i = 0; i < arr.size() - 1; i++) {
            for (int j = arr.size() - 1; j > i; j--) {
                if ((long) arr.get(j-1).get(value) > (long) arr.get(j).get(value)) {
                    Map<String, Object> tmp = arr.get(j-1);
                    arr.set(j-1, arr.get(j));
                    arr.set(j, tmp);
                }
            }
        }
        return arr;
    }

    private List<Map<String, Object>> bubbleSortInt(List<Map<String, Object>> arr, String value) {
        for (int i = 0; i < arr.size() - 1; i++) {
            for (int j = arr.size() - 1; j > i; j--) {
                if ((int) arr.get(j-1).get(value) > (int) arr.get(j).get(value)) {
                    Map<String, Object> tmp = arr.get(j-1);
                    arr.set(j-1, arr.get(j));
                    arr.set(j, tmp);
                }
            }
        }
        return arr;
    }


}
