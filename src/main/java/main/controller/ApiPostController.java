package main.controller;

import main.api.request.PostAddRequest;
import main.api.response.PostAddResponse;
import main.api.response.PostCalendarDto;
import main.api.response.PostsResponse;
import main.service.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ApiPostController {

    private final PostService postService;
    private final CalendarService calendarService;

    public ApiPostController(PostService postService, CalendarService calendarService) {

        this.postService = postService;
        this.calendarService = calendarService;
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
    private PostsResponse PostbyId(@PathVariable int ID
    ) {
        return postService.getPostsById(ID);
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


}
