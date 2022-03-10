package main.service;

import main.api.response.TagResponse;
import main.model.*;
import main.repository.PostRepository;
import main.repository.Tag2postRepository;
import main.repository.TagRepository;
import org.springframework.aop.AopInvocationException;
import org.springframework.stereotype.Service;

import java.util.*;

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
        double maxWeightTag = 0;
        int queryTagId = -1;  // номер строки запрашиваемого тега в таблице

        List<String> tags = new ArrayList<>();
        tagRepository.findAll().forEach(tagRepository -> tags.add(tagRepository.getName()));

        //Ищем наличие тэга в бд и определяем его id
        String querySQL = tagRepository.findIdByName(query);
        if (querySQL == null) {
            System.out.println("Запрос не найден");
        } else {
            queryTagId = Integer.parseInt(querySQL) - 1;
        }

        //Находим количество публикаций у каждого тэга
         List<Integer> countPostTags = tag2postRepository.findCountTag();
        System.out.println("Количество публикаций у тэгов: " + countPostTags);

        //Находим вес у каждого тэга
        float count = countPostTags.size();
        List<Double> weightTag = tag2postRepository.findWeightTag(count);
        System.out.println("Вес публикаций у тэгов: " + weightTag);

        //Находим максимальный Вес
        maxWeightTag = tag2postRepository.findMaxWeightTag(count);
        System.out.println("Максимальный вес: " + maxWeightTag);

        // Находим коэффициенты нормализации для каждого тэга
        List<Double> koefTags = tag2postRepository.findKoefTag(maxWeightTag);
        System.out.println("Коэффициенты нормализации у тэгов: " + koefTags);

        // Ненормализованный вес каждого тега
        List<Double> notNormWeightTags = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            notNormWeightTags.add(weightTag.get(i) * koefTags.get(i));
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
