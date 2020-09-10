package org.example;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.example.pojo.Person;
import org.example.pojo.Student;
import org.example.utils.JsonUtils;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        Person person = new Person();
        person.setAge(13);
        person.setName("姜永健");
        Student student = new Student("张三",14);
//        String s = JsonUtils.objectToJson(person);
//        System.out.println("Object转JSON"+s);
//        List list = new ArrayList();
//        list.add(person);
//        list.add(student);
//        Person person1 = (Person) list.get(0);
//        System.out.println("第一次之前"+list.get(0));
//        System.out.println("第一次转"+person1.toString());
//        String listJson = JsonUtils.objectToJson(list);
//        System.out.println(listJson);
//        List<Person> jsonToList = JsonUtils.jsonToList(listJson, Person.class);
//        System.out.println(jsonToList);
//        System.out.println("3   "+jsonToList.get(0));
//        Object personList = jsonToList.get(0);
//        System.out.println("最后一次"+personList.toString());
//        Person person2 = null;
//        person2 = (Person) personList;

        Map map = new HashMap<>();
        map.put("person",person);
        map.put("student",student);
        String s = JsonUtils.objectToJson(map);
        System.out.println("map转Bean   "+((Person)map.get("person")).toString());
        System.out.println("map转Json    "+s);
        Map<String, Object> toMap = JsonUtils.jsonToMap(s,String.class,Object.class);
        System.out.println("map   "+map);
        System.out.println("toMap    "+toMap);
        System.out.println(((Person)toMap.get("person")).toString());



    }


    public static void test() throws DocumentException {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<root>"
                + "<BASE_DATA>"
                + "<USER_CODE>test</USER_CODE>"
                + "<USER_NAME>bb</USER_NAME>"
                + "<ORG_CODE>5010100</ORG_CODE>"
                + "<COM_CODE>5010100</COM_CODE>"
                + "<ORG_NAME>bb</ORG_NAME>"
                + "<ROLE_CODE>shuanglu</ROLE_CODE>"
                + "<ONE_BATCH>1</ONE_BATCH>"
                + "<BIZ_INFO>"
                + "<BIZ_TYPE APP_CODE=\"KH_0101\" APP_NAME=\"客户\"/>"
                + "</BIZ_INFO>"
                + "</BASE_DATA>"
                + "<META_DATA>"
                + "<BATCH>"
                + "<APP_CODE>KH_0101</APP_CODE>"
                + "<APP_NAME>身份证正面</APP_NAME>"
                + "<KHSQ_NO>110104194110162030insuredIdentityNo</KHSQ_NO>"
                + "<KH_NAME>吴升良insuredName</KH_NAME>"
                + "<CLASSIFY_LIMIT>2</CLASSIFY_LIMIT>"
                + "</BATCH>"
                //+"<PAGEIDS>"
                //+"<!-- 需要获取资源的影像代码 -->"
                //+"<PAGEID>71f7a0cb-b900-49f8-975c-1676a35217be</PAGEID>"
                //+"</PAGEIDS>"
                + "<DOWNLOAD  TYPE=\"1\"></DOWNLOAD>"
                + "</META_DATA>"
                + "</root>";
        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        System.out.println(root.getName());

        List<Element> BASE_DATA = root.elements("BASE_DATA");

        for (Element elment :
                BASE_DATA) {
            System.out.println(elment.elementText("USER_CODE"));
            System.out.println(elment.attributeValue("BIZ_TYPE"));
            List<Element> biz_info = elment.elements("BIZ_INFO");
            System.out.println("size"+biz_info.size());

            for (Element element:biz_info){
                System.out.println(element.elementText("BIZ_TYPE"));
                System.out.println(element.attributeValue("BIZ_TYPE"));
            }
        }


    }
}
