package org.gfg.redis_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class PersonController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    private static String personValuePattern = "per::";
    private static String personListKey = "personList";
    private static String personSetKey = "personSet";
    private static String personHashPattern = "perHash::";

    //--- value operation ---------
    @PostMapping("/addPerson/cache")
    public boolean addPersonInCache(@RequestBody Person person) {
        String key = personValuePattern + String.valueOf(person.getId());
        redisTemplate.opsForValue().set(key, person);
        return true;
    }

    @GetMapping("/getPerson/cache")
    public Person getPersonFromCache(@RequestParam("id") String id) {
        String key = personValuePattern + id;
        return (Person) redisTemplate.opsForValue().get(key);
    }


//    ---- List Operations ----

    @PostMapping("/addPersonToList/cache")
    public boolean addPersonToListInCache(@RequestBody List<Person> persons) {
        persons.stream().forEach(p -> redisTemplate.opsForList().leftPush(personListKey, p));
        return true;
    }

    @GetMapping("/getPersonFromList/cache")
    public List<Person> getPersonListFromCache(@RequestParam("key") String key) {
        return (List<Person>) (Object) redisTemplate.opsForList().range(key, 0, -1);
    }

// ---- set operation -------

    @PostMapping("/addPersonToSet/cache")
    public boolean addPersonToSetInCache(@RequestBody List<Person> persons) {
        persons.stream().forEach(p -> redisTemplate.opsForSet().add(personSetKey, p));
        return true;
    }

    @GetMapping("/getPersonFromSet/cache")
    public Set<Person> getPersonSetFromCache(@RequestParam("key") String key) {
        return (Set<Person>) (Object) redisTemplate.opsForSet().members(key);
    }

//   --------- HashOperation  -----

    @PostMapping("/addPersonToHash/cache")
    public boolean addPersonToHashInCache(@RequestBody List<Person> persons) {
        persons.stream().forEach(p -> {
            String key = personHashPattern + p.getId();
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("name", p.getName());

            redisTemplate.opsForHash().putAll(key, map);
        });
        return true;
    }

    @GetMapping("/getPersonFromHash/cache")
    public Map<String, Object> getPersonHashFromCache(@RequestParam("key") String key) {
        return (Map<String, Object>) (Object) redisTemplate.opsForHash().entries(key);
    }

}
