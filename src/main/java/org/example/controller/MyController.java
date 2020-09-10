package org.example.controller;

import org.example.pojo.Person;
import org.example.utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.awt.image.BufferedImage;
import java.io.*;

@Controller
public class MyController {

    @GetMapping("/demo")
    @ResponseBody
    public String showXML() {
        Person person = new Person();
        person.setAge(13);
        person.setName("姜永健");
        System.out.println(JsonUtils.objectToJson(person));
        String src = "resources/static/1.png";
//        // 创建JAXB上下文
//        JAXBContext context = null;
//        StringWriter sw = null;
//        try {
//            context = JAXBContext.newInstance(person.getClass());
//
//        // 创建QName，这个是关键，有了这个就不要使用@XmlRootElement注解了，关于QName的资料大家自己网上找，这里不赘述
//        QName q = new QName("Person");
//        JAXBElement<Person> jaxbPerson = new JAXBElement<Person>(q, Person.class, person);
//        Marshaller m = context.createMarshaller();
//        sw = new StringWriter();
//        m.marshal(jaxbPerson, sw);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//        // 输出转换后的XML代码
//        System.out.println(sw.toString());
        return "Demo";
    }

    @RequestMapping(value="/getBiImg")
    public void getBiImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        File file=new File("/images/1.png   ");
        InputStream is=new FileInputStream(file);
        BufferedImage bi= ImageIO.read(is);
        ImageIO.write(bi, "JPEG", response.getOutputStream());
    }
}
