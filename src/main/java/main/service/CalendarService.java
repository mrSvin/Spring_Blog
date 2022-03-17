package main.service;


import main.api.response.PostCalendarDto;
import main.api.response.PostDetailsDto;
import main.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalendarService {

    private final PostRepository postRepository;

    public CalendarService(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    public PostCalendarDto getPosts(Integer year) {

        if (year == 0) {
            year = Calendar.getInstance().get(Calendar.YEAR);
        }

        List<String> date = postRepository.findYear(year);
        List<Integer> dateCount = postRepository.findYearCount(year);

        PostCalendarDto postCalendarDto = new PostCalendarDto();
        Map<String, Integer> calendar = new TreeMap<>();
        for (int i = 0; i<date.size(); i++) {
            calendar.put(date.get(i), dateCount.get(i));

        }
        postCalendarDto.setPosts(calendar);
        postCalendarDto.setYears(year);

        return postCalendarDto;
    }


}
