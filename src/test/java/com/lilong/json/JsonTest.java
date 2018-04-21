package com.lilong.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.lilong.MyListenerProcessor;
import com.lilong.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * 类名称：JsonTest<br>
 * 类描述：<br>
 * 创建时间：2018年04月19日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
public class JsonTest {

    static Logger log = LoggerFactory.getLogger(JsonTest.class);

    /**
     * 读取文件类容为字符串
     *
     * @return
     */
    public static String jsonRead(String filePath) {
        String realPath = MyListenerProcessor.class.getClassLoader().getResource(filePath).getPath();
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(new File(realPath), "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }

    public static void main(String[] args) {


        String jsonStr = jsonRead("data/case/test.json");
        System.out.println(jsonStr);
        jsonStr = "{'stu':[{'name':1}]}";
//        String json = JsonPath.read(jsonStr, "$.stu").toString();
        String json  = JSONPath.read(jsonStr,"$.stu" ).toString();
        Class a = String.class;
        List<User> list = getList(json, User.class);
        // List<Stu> list  = JSON.parseObject(json, new TypeReference<List<Stu>>(){});
        System.out.println(list.get(0).getName());
//        TypeRef<List<Map>> typeRef = new TypeRef<List<Map>>(){};
//        List<Map> stu =  JsonPath.parse(jsonStr).read("$.stu",typeRef);
//        Stu a = stu.get(0);
//        System.out.println(stu.toString());

    }

    public static <T> List<T> getList(String json, Class clazz) {
        List<T> list = JSON.parseArray(json, clazz);
        return list;
    }

//    @SuppressWarnings("unchecked")
//    public static <T> List<T> getGenericList(String json, String jsonPath,
//                                             Class<T> clazz) {
//        String jsonResult =null;
//        if(jsonPath==null||"".equals(jsonPath))
//        {
//            jsonResult=json;
//        }else
//        {
//            jsonResult = JsonPath.read(json, jsonPath).toString();
//        }
//
//        Type listtype = new TypeToken<List<T>>() {
//        }.getType();
//        return (List<T>) new Gson().fromJson(jsonResult, listtype);
//    }


    @Test
    public void test1() {
        String jsonStr = "{\n" +
                "    \"store\": {\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        },\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"author\": \"刘慈欣\",\n" +
                "                \"price\": 8.95,\n" +
                "                \"category\": \"科幻\",\n" +
                "                \"title\": \"三体\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"author\": \"itguang\",\n" +
                "                \"price\": 12.99,\n" +
                "                \"category\": \"编程语言\",\n" +
                "                \"title\": \"go语言实战\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        JSONObject jsonObject = JSON.parseObject(jsonStr);

        log.info(jsonObject.toString());

        //得到所有的书
        List<Book> books = (List<Book>) JSONPath.eval(jsonObject, "$.store.book");
        books = (List<Book>) JSONPath.read(jsonStr, "$.store.book");
        log.info("books={}", books.get(0).getAuthor());

        //得到所有的书名
        List<String> titles = (List<String>) JSONPath.eval(jsonObject, "$.store.book.title");
        log.info("titles={}", titles);

        //第一本书title
        String title = (String) JSONPath.read(jsonStr, "$.store.book[0].title");
        log.info("title={}", title);
//        Book bok = (Book) JSONPath.read(jsonStr, "$.store.book[0].*");

        //price大于10元的book
        List<Book> list = (List<Book>) JSONPath.read(jsonStr, "$.store.book[price > 10]");
        log.info("price大于10元的book={}", list);

        //price大于10元的title
        List<String> list2 = (List<String>) JSONPath.read(jsonStr, "$.store.book[price > 10].title");
        log.info("price大于10元的title={}", list2);

        //category(类别)为科幻的book
        List<Book> list3 = (List<Book>) JSONPath.read(jsonStr, "$.store.book[category = '科幻']");
        log.info("category(类别)为科幻的book={}", list3);


        //bicycle的所有属性值

        Collection<String> values = (Collection<String>) JSONPath.eval(jsonObject, "$.store.bicycle.*");

        log.info("bicycle的所有属性值={}", values);


        //bicycle的color和price属性值
        List<String> read = (List<String>) JSONPath.read(jsonStr, "$.store.bicycle['color','price']");

        log.info("bicycle的color和price属性值={}", read);
    }

}
