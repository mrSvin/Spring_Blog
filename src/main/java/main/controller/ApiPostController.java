package main.controller;

import main.api.request.CommentAddRequest;
import main.api.request.LikeRequest;
import main.api.request.PostAddRequest;
import main.api.response.*;
import main.service.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class ApiPostController {

    private final PostService postService;
    private final CalendarService calendarService;
    private final AddImageService addImageService;

    public ApiPostController(PostService postService, CalendarService calendarService, AddImageService addImageService) {

        this.postService = postService;
        this.calendarService = calendarService;
        this.addImageService = addImageService;
    }

    @GetMapping("/post")
//    @PreAuthorize("hasAnyAuthority('user:write')")
    private PostsResponse Post(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "mode") String mode
    ) {
        return postService.getPosts(offset, limit, mode);
    }

    @GetMapping("/post/search")
//    @PreAuthorize("hasAnyAuthority('user:moderate')")
    private PostsResponse PostSearch(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "query") String query
    ) {
        return postService.getPostsSearch(offset, limit, query);
    }

    @GetMapping("/calendar")
    private PostCalendarDto Calendar(
            @RequestParam(value = "year", defaultValue = "0") Integer years) {
        return calendarService.getPosts(years);
    }

    @GetMapping("/post/byDate")
    private PostsResponse PostbyDate(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "date") String date
    ) {
        return postService.getPostsByDate(offset, limit, date);
    }

    @GetMapping("/post/byTag")
    private PostsResponse PostbyTag(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "tag") String tag
    ) {
        return postService.getPostsByTag(offset, limit, tag);
    }

    @GetMapping("/post/{ID}")
    private PostInfoResponse PostbyId(@CookieValue(value = "auth") String authCoocie,
                                      @PathVariable int ID
    ) {
        return postService.getPostsById(ID, authCoocie);
    }

    @GetMapping("/post/moderation")
    private PostsResponse PostbyModeration(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "status") String status
    ) {
        return postService.getPostsByModeration(offset, limit, status);
    }

    @GetMapping("/post/my")
    private PostsResponse PostMy(@CookieValue(value = "auth") String authCoocie,
                                 @RequestParam(value = "offset", defaultValue = "0") int offset,
                                 @RequestParam(value = "limit", defaultValue = "10") int limit,
                                 @RequestParam(value = "status") String status
    ) {
        return postService.getPostsMy(offset, limit, status, authCoocie);
    }

    @PostMapping("/post")
    private PostAddResponse addPost(@CookieValue(value = "auth") String authCoocie,
                                    @RequestBody PostAddRequest postAddRequest) {
        return postService.addPost(postAddRequest.getTimestamp(), postAddRequest.getActive(),
                postAddRequest.getTitle(), postAddRequest.getTags(), postAddRequest.getText(), authCoocie);
    }

    @PutMapping("/post/{ID}")
    private PostAddResponse editPost(@PathVariable int ID,
                                     @CookieValue(value = "auth") String authCoocie,
                                     @RequestBody PostAddRequest postAddRequest) {
        return postService.editPost(postAddRequest.getTimestamp(), postAddRequest.getActive(),
                postAddRequest.getTitle(), postAddRequest.getTags(), postAddRequest.getText(), authCoocie, ID);
    }

    @PostMapping("/comment")
    private CommentAddResponse addComment(@CookieValue(value = "auth") String authCoocie,
                                          @RequestBody CommentAddRequest commentAddRequest) {
        return postService.addComment(commentAddRequest.getParentId(), commentAddRequest.getPostId(), commentAddRequest.getText(), authCoocie);
    }

    @PostMapping("/post/like")
    private LikeResponse like (@CookieValue(value = "auth") String authCoocie,
                               @RequestBody LikeRequest likeRequest) {

        return postService.addLike(likeRequest.getPostId(), authCoocie);
    }

    @PostMapping("/post/dislike")
    private LikeResponse dislike (@CookieValue(value = "auth") String authCoocie,
                               @RequestBody LikeRequest likeRequest) {

        return postService.addDislike(likeRequest.getPostId(), authCoocie);
    }

    @RequestMapping(path = "/image", method = POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private Object addImage(@CookieValue(value = "auth") String authCoocie,
                                     @RequestPart(value = "image") MultipartFile image) throws IOException {
        return addImageService.addImage(authCoocie, image);
    }

}
