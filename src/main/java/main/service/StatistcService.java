package main.service;

import main.api.response.StatisticResponse;
import main.repository.PostRepository;
import main.repository.PostVotesRepository;
import org.springframework.stereotype.Service;

@Service
public class StatistcService {

    private final PostRepository postRepository;
    private final PostVotesRepository postVotesRepository;

    public StatistcService(PostRepository postRepository, PostVotesRepository postVotesRepository) {
        this.postRepository = postRepository;
        this.postVotesRepository = postVotesRepository;
    }

    public StatisticResponse myStatistic(String authCoocie) {
        StatisticResponse statisticResponse = new StatisticResponse();

        int idUser = LoginService.sessions.get(authCoocie);

        statisticResponse.setPostsCount(postRepository.countMyPosts(idUser));
        statisticResponse.setLikesCount(postVotesRepository.statisticLikesCount(idUser));
        statisticResponse.setDislikesCount(postVotesRepository.statisticDislikesCount(idUser));
        statisticResponse.setViewCount(postRepository.countViewsMyPosts(idUser));
        long firstPost = postRepository.firstMyPost(idUser).getTime()/1000;
        statisticResponse.setFirstPublication(firstPost);

        return statisticResponse;
    }

    public StatisticResponse allStatistic() {
        StatisticResponse statisticResponse = new StatisticResponse();

        statisticResponse.setPostsCount(postRepository.countAllPosts());
        statisticResponse.setLikesCount(postVotesRepository.statisticAllLikesCount());
        statisticResponse.setDislikesCount(postVotesRepository.statisticAllDislikesCount());
        statisticResponse.setViewCount(postRepository.countViewsAllPosts());
        long firstPost = postRepository.firstPost().getTime()/1000;
        statisticResponse.setFirstPublication(firstPost);

        return statisticResponse;
    }

}
