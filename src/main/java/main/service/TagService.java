package main.service;

import main.api.response.TagResponse;
import main.model.*;
import main.repository.PostRepository;
import main.repository.Tag2postRepository;
import main.repository.TagRepository;
import org.springframework.aop.AopInvocationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class TagService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final Tag2postRepository tag2postRepository;

    public TagService(PostRepository postRepository, TagRepository tagRepository, Tag2postRepository tag2postRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.tag2postRepository = tag2postRepository;
    }

    //Находим Вес по запросу query
    public TagResponse getTags(String query) {

        TagResponse tagResponse = new TagResponse();
        tagResponse.setTags(calculateWeightTag(query));

        return tagResponse;
    }

    //Находим Вес без запроса query
    public TagResponse getTagsAll() {

        TagResponse tagResponse = new TagResponse();
        tagResponse.setTags(calculateWeightTag(""));

        return tagResponse;
    }

    private List<Map<String, Object>> calculateWeightTag(String query) {

        List<Map<String, Object>> resultweight = new ArrayList<>();

        int queryTagId = -1;  // номер строки запрашиваемого тега в таблице

        List<String> tags = new ArrayList<>();
        tagRepository.findAll().forEach(tagRepository -> tags.add(tagRepository.getName()));

        //Ищем наличие тэга в бд и определяем его id
        Integer querySQL = tagRepository.findIdByName(query);
        if (querySQL == null) {
            System.out.println("Запрос не найден");
        } else {
            queryTagId = querySQL - 1;
        }

        //Находим количество публикаций у каждого тэга
         List<Integer> countPostTags = tag2postRepository.findCountTag();
        System.out.println("Количество публикаций у тэгов: " + countPostTags);

        // Ненормализованный вес каждого тега
        if (countPostTags.size() == 0) {
            return resultweight;
        }
        int maxWeightTag = Collections.max(countPostTags);
        double oneProcent = maxWeightTag/100.0;

        List<Double> notNormWeightTags = new ArrayList<>();
        for (int i = 0; i < countPostTags.size(); i++) {
            Double notNormWeight = (Double.valueOf(countPostTags.get(i))/oneProcent)/100.0;
            notNormWeightTags.add(notNormWeight);
        }
        System.out.println("Ненормализованный вес каждого тега: " + notNormWeightTags);

        //Закидываем результат в map
        //Если запрос есть в таблице
        if (queryTagId > -1) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", tags.get(queryTagId));
            map.put("weight", notNormWeightTags.get(queryTagId));
            resultweight.add(map);
        } else {
            for (int i = 0; i < tags.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", tags.get(i));
                map.put("weight", notNormWeightTags.get(i));
                resultweight.add(map);
            }
        }

        return resultweight;
    }


}
