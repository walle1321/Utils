package org.example;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.example.utils.ZipUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TestTest {

    @Test
    public void test() throws DocumentException {
        /*String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<root>\n" +
                "  <DocInfo>\n" +
                "    <APP_CODE>KH</APP_CODE>\n" +
                "    <APP_NAME>客户</APP_NAME>\n" +
                "    <BUSI_NO>202004220000058949businum10</BUSI_NO>\n" +
                "  </DocInfo>\n" +
                "  <PAGES>\n" +
                "    <PAGE PAGE_ID=\"889e8cf617554f529dc2a34f311d7db9\" DOC_TYPE=\"KH_0101\" DOC_NAME=\"身份证正面\" PAGE_URL=\"678bbaad-8654-4a54-80e8-2388d6969dda.jpg\" PAGE_NAME=\"identityPictureName.jpg\" UP_USER=\"SLL\" UP_ORG=\"\" UP_TIME=\"2020-09-04 14:09:54\"/>\n" +
                "<PAGE PAGE_ID=\"889e8cf617554f529dc2a34f311d7db9\" DOC_TYPE=\"KH_0102\" DOC_NAME=\"身份证反面\" PAGE_URL=\"678.jpg\" PAGE_NAME=\"identityPictureName.jpg\" UP_USER=\"SLL\" UP_ORG=\"\" UP_TIME=\"2020-09-04 14:09:54\"/>\n" +
                "<PAGE PAGE_ID=\"889e8cf617554f529dc2a34f311d7db9\" DOC_TYPE=\"KH_0103\" DOC_NAME=\"身份证活体\" PAGE_URL=\"678bbaad.jpg\" PAGE_NAME=\"identityPictureName.jpg\" UP_USER=\"SLL\" UP_ORG=\"\" UP_TIME=\"2020-09-04 14:09:54\"/>\n" +
                "  </PAGES>\n" +
                "</root>";

        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        System.out.println(root.getName());

        System.out.println(root.elementText("PAGES"));

        List<Element> pages = root.elements("PAGES");
        System.out.println(pages.get(0));

        for (Element elment :
                pages) {
//            System.out.println(elment.elementText("USER_CODE"));
            System.out.println(elment.attributeValue("PAGE_ID"));
            List<Element> biz_info = elment.elements("PAGE");
//            System.out.println("size" + biz_info.size());
//
            for (Element element : biz_info) {
//                System.out.println(element.elementText("BIZ_TYPE"));
                System.out.println(element.attributeValue("DOC_TYPE"));
            }
        }*/

        Document document = null;
        //读取XML文件
        try {
            SAXReader reader = new SAXReader();
            File file = new File("src/main/resources/temporary/busi.xml");
            document = reader.read(file);
        } catch (DocumentException e) {
            throw new DocumentException("读取XML文件出错");
        }finally {

        }
        //获取根节点
        Element root = document.getRootElement();
        //获取根节点下面的子节点
        List<Element> pages = root.elements("PAGES");

        //获取遍历子节点下的孙节点，并获取孙节点的属性
        Map map = new HashMap();
        for (Element elment :
                pages) {
            System.out.println("我被执行了");
            List<Element> page = elment.elements("PAGE");
            //遍历page属性，获取属性值并存到map中
            for (Element element : page) {
                String key = element.attributeValue("DOC_TYPE");
                String value = element.attributeValue("PAGE_URL");
                map.put(key,value);
            }
        }
        System.out.println(map.get("KH_0101"));

    }

    @Test
    public void test01() throws Exception {
        //解压zip到zipID文件夹中(zip文件的前缀)
        String zipID="p";
        String zipName="p.zip";
        ZipUtil.unZip("src/main/resources/temporary/"+zipName,"src/main/resources/temporary/"+zipID+"/");


        //下面对压缩后的XML文件进行解析获取相对应照片的name并存入map中
        Document docXML = null;
        //读取XML文件
        try {
            SAXReader reader = new SAXReader();
            File file = new File("src/main/resources/temporary/"+zipID+"/busi.xml");
            docXML = reader.read(file);
        } catch (DocumentException e) {
            throw new DocumentException("读取XML文件出错");
        }finally {

        }
        //获取根节点
        Element rootXML = docXML.getRootElement();
        //获取根节点下面的子节点
        List<Element> pages = rootXML.elements("PAGES");

        //获取遍历子节点下的孙节点，并获取孙节点的属性
        Map map = new HashMap();
        for (Element elment :
                pages) {
            System.out.println("Pages被执行了");
            List<Element> page = elment.elements("PAGE");
            //遍历page属性，获取属性值并存到map中
            for (Element element : page) {
                String key = element.attributeValue("DOC_TYPE");
                String value = element.attributeValue("PAGE_URL");
                map.put(key,value);
            }
        }
        System.out.println(map.get("KH_0101"));

    }

}