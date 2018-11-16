package test;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lilianga on 2018/9/25.
 */
public class compare implements Comparable{

    int age;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Object o) {
        compare c = (compare) o;
        if (this.age>c.getAge()){
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        compare c= new compare();
        c.setAge(12);
        c.setName("cccc");
        compare b = new compare();
        b.setAge(13);
        b.setName("bbb");

        List<compare> list= new ArrayList<>();
        list.add(c);
        list.add(b);
        c.compareTo(b);

        Map<String,List<compare>> map = new HashMap<>();
        map.put("key", list);

        String json = JSONObject.toJSONString(map);
        System.out.println(json);
    }

}
