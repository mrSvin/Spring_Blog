package main.service;

import main.api.response.TagResponse;
import main.model.*;
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

        int countPosts = (int) postRepository.count();
        int countTag = 0;
        double dWeightHibernate = 0;
        double maxWeightTag = 0;
        int queryTagId = -1;  // номер строки запраштваемого тега в таблице

        List<String> tags = new ArrayList<>();
        List<Tags> tagsId = new ArrayList<>();
        List<Integer> countPostTags = new ArrayList<>();
        List<Double> weightTags = new ArrayList<>();
        List<Double> koefTags = new ArrayList<>();
        List<Double> notNormWeightTags = new ArrayList<>();

        tagRepository.findAll().forEach(tagRepository -> tags.add(tagRepository.getName()));


        //Проверяем наличие тэга "query" в таблице
        for (int i = 0; i < tags.size(); i++) {
            if (query.equals(tags.get(i))) {
                queryTagId=i;
            }
        }


        //Находим количество публикаций у каждого тэга
        tag2postRepository.findAll().forEach(tag2postRepository -> tagsId.add(tag2postRepository.getTag()));
        for (int i = 0; i < tags.size(); i++) {
            for (int k = 0; k < tagsId.size(); k++) {
                if (tagsId.get(k).getName().equals(tags.get(i))) {
                    countTag++;
                }
            }
            countPostTags.add(countTag);
            countTag = 0;
        }
        System.out.println("Количество публикаций у тэгов: " + countPostTags);

        //Находим вес у каждого тэга
        for (int i = 0; i < tags.size(); i++) {
            dWeightHibernate = countPostTags.get(i) * 1.0 / countPosts;
            weightTags.add(dWeightHibernate);
        }
        System.out.println("Вес публикаций у тэгов: " + weightTags);

        //Находим максимальный Вес
//        Collections.sort(weightTags);
        maxWeightTag = weightTags.stream().max(Double::compare).get();
        System.out.println("Максимальный вес: " + maxWeightTag);

        // Находим коэффициенты нормализации для каждого тэга
        for (int i = 0; i < tags.size(); i++) {
            koefTags.add(maxWeightTag / weightTags.get(i));
        }
        System.out.println("Коэффициенты нормализации у тэгов: " + koefTags);

        // Ненормализованный вес каждого тега
        for (int i = 0; i < tags.size(); i++) {
            notNormWeightTags.add(weightTags.get(i) * koefTags.get(i));
        }
        System.out.println("Ненормализованный вес каждого тега: " + notNormWeightTags);


        //Закидываем результат в map
        //Если запрос есть в таблице
        if (queryTagId>-1) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", tags.get(queryTagId));
            map.put("weight", notNormWeightTags.get(queryTagId));
            resultweight.add(map);
        } else  {

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
