package net.codejava.spring.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Controller
public class HomeController {

        @RequestMapping(value = "/")
        public ModelAndView goHome(HttpServletResponse response) throws IOException {
                return new ModelAndView("home");
        }

        @RequestMapping(value = "/viewXSLT")
        public ModelAndView viewXSLT(HttpServletRequest request,HttpServletResponse response) throws IOException {
                // builds absolute path of the XML file
                String xmlFile = "resources/citizens.xml";
                String contextPath = request.getServletContext().getRealPath("");
                String xmlFilePath = contextPath + File.separator + xmlFile;

                Source source = new StreamSource(new File(xmlFilePath));

                // adds the XML source file to the model so the XsltView can detect
                ModelAndView model = new ModelAndView("XSLTView");
                model.addObject("xmlSource", source);

                return model;
        }

        @RequestMapping(value = "/viewXSLTtry")
        public ModelAndView viewXSLTtry(HttpServletRequest request,HttpServletResponse response) throws IOException {
                // builds absolute path of the XML file
                String xmlFile = "resources/myCDcollection_1.xml";
                String contextPath = request.getServletContext().getRealPath("");
                String xmlFilePath = contextPath + File.separator + xmlFile;

                Source source = new StreamSource(new File(xmlFilePath));

                // adds the XML source file to the model so the XsltView can detect
                ModelAndView model = new ModelAndView("XSLTViewTry");
                model.addObject("xmlSource", source);

                return model;
        }

        @RequestMapping(value = "/viewXSLTAccessLog")
        public ModelAndView viewXSLTAccessLog(HttpServletRequest request,HttpServletResponse response) throws IOException {
                // builds absolute path of the XML file
                String xmlFile = "resources/accessLog.xml";
                String contextPath = request.getServletContext().getRealPath("");
                String xmlFilePath = contextPath + File.separator + xmlFile;

                Source source = new StreamSource(new File(xmlFilePath));

                // adds the XML source file to the model so the XsltView can detect
                ModelAndView model = new ModelAndView("XSLTViewAccessLog");
                model.addObject("xmlSource", source);

                return model;
        }

        @RequestMapping(value = "/viewXsl")
        public ModelAndView viewXml(HttpServletRequest request, HttpServletResponse response) throws Exception {
                // 构建XML文件的绝对路径

                String xmlFile = "resources/a.xml";
                String contextPath = request.getServletContext().getRealPath("");
                String xmlFilePath = contextPath + File.separator + xmlFile;

                Source source = new StreamSource(new File(xmlFilePath));

                // 将XML源文件添加到模型中，以便XsltView能够检测
                ModelAndView model = new ModelAndView("XSLTViewXml");
                model.addObject("xmlSource", source);

                return model;

        }

        //获取xml-for
        @RequestMapping(value = "/viewXml")
        public ModelAndView viewXmlFor(HttpServletRequest request, HttpServletResponse response) throws Exception {
                //获取文档对象
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.newDocument();

                //创建请求方法
                HttpGet httpget = new HttpGet("https://redan-api.herokuapp.com/story/");

                //获取响应对象
                CloseableHttpResponse response1 = HttpClients.createDefault().execute(httpget);
                try {
                        //获取响应体
                        HttpEntity entity1 = response1.getEntity();
                        if (entity1 != null) {
                                //设置编码字符集
                                String retSrc = EntityUtils.toString(entity1, "UTF-8");

                                //转换为json数据并且获取result中的数据
                                JSONArray storyList = new JSONObject(retSrc).getJSONArray("result");
                                //获取根标签的值
                                JSONObject sx = new JSONObject(retSrc);
                                String string11 = sx.get("status").toString();
                                //创建根目录
                                Element document = doc.createElement("document");
                                document.setAttribute("status", string11);
                                doc.appendChild(document);

                                //获取所有数据
                                JSONObject jsonObject = storyList.getJSONObject(0);

                                //获取第一层所有key
                                Iterator<String> keys = jsonObject.keys();
                                //遍历第一层
                                for (int i = 0; i < jsonObject.length(); i++) {
                                        //获取key
                                        String next = keys.next();
                                        //获取值
                                        String string = jsonObject.get(next).toString();
                                        //创建第一层标签
                                        Element nextElement = doc.createElement(next);
                                        //判断值是否是json格式
                                        if (!string.endsWith("]") && !string.endsWith("}")) {
                                                System.out.println("1:" + next + "   " + string);
                                                //添加文本
                                                nextElement.setTextContent(string);
                                        }
                                        //添加标签
                                        document.appendChild(nextElement);
                                        //判断第二层是否是json数组
                                        if (string.endsWith("]")) {
                                                //获取json数组
                                                JSONArray jsonArray = jsonObject.getJSONArray(next);
                                                //遍历json数组
                                                for (int j = 0; j < jsonArray.length(); j++) {
                                                        //获取json对象
                                                        JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                                        //获取json对象使用的key数组
                                                        Iterator<String> keys1 = jsonObject1.keys();
                                                        //遍历第二层json
                                                        for (int b = 0; b < jsonObject1.length(); b++) {
                                                                //获取key
                                                                String next1 = keys1.next();
                                                                //获取值
                                                                String toString = jsonObject1.get(next1).toString();
                                                                //创建第二层标签
                                                                Element next1Element = doc.createElement(next1);
                                                                //判断是否为json对象
                                                                if (!toString.endsWith("}")) {
                                                                        System.out.println("2:" + next1 + "     " + toString);
                                                                        //添加文本
                                                                        next1Element.setTextContent(toString);
                                                                }
                                                                //添加节点
                                                                nextElement.appendChild(next1Element);
                                                                //判断第三层是否是json对象
                                                                if (toString.endsWith("}")) {
                                                                        //获取json对象
                                                                        JSONObject jsonObject3 = jsonObject1.getJSONObject(next1);
                                                                        //获取key数组
                                                                        Iterator<String> keys2 = jsonObject3.keys();
                                                                        //遍历第三层
                                                                        for (int n = 0; n < jsonObject3.length(); n++) {
                                                                                //获取key
                                                                                String next2 = keys2.next();
                                                                                //创建第二层标签
                                                                                Element next2Element = doc.createElement(next2);
                                                                                //添加文本值
                                                                                next2Element.setTextContent(jsonObject3.getString(next2));
                                                                                //添加节点
                                                                                next1Element.appendChild(next2Element);
                                                                                System.out.println("4:" + next2 + "   " + jsonObject3.getString(next2));
                                                                        }
                                                                }

                                                        }
                                                }
                                                //判断第二层是否为json对象
                                        } else if (string.endsWith("}")) {
                                                //获取json对象
                                                JSONObject jsonObject2 = jsonObject.getJSONObject(next);
                                                //获取对象的key数组
                                                Iterator<String> keys1 = jsonObject2.keys();
                                                //遍历第二层
                                                for (int j = 0; j < jsonObject2.length(); j++) {
                                                        //获取key
                                                        String next1 = keys1.next();
                                                        //创建第二层标签
                                                        Element next1Element = doc.createElement(next1);
                                                        //添加文本值
                                                        next1Element.setTextContent(jsonObject2.getString(next1));
                                                        //添加节点
                                                        nextElement.appendChild(next1Element);
                                                        System.out.println("3:" + next1 + "   " + jsonObject2.getString(next1));
                                                }
                                        }

                                }

                        }

                } catch (Exception e) {
                        System.out.println(e);
                }
                //TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(response.getOutputStream()));

                Source source = new DOMSource(doc);

                // 将XML源文件添加到模型中，以便XsltView能够检测
                ModelAndView model = new ModelAndView("XSLTViewXml");
                model.addObject("xmlSource", source);

                return model;
        }

        //获取xml- iteration
        @RequestMapping(value = "/viewXmlIteration")
        public ModelAndView viewXmlIteration(HttpServletRequest request, HttpServletResponse response) throws Exception {
                //获取文档对象
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.newDocument();

                //创建请求方法
                HttpGet httpget = new HttpGet("https://redan-api.herokuapp.com/story/");

                //获取响应对象
                CloseableHttpResponse response1 = HttpClients.createDefault().execute(httpget);
                try {
                        //获取响应体
                        HttpEntity entity1 = response1.getEntity();
                        if (entity1 != null) {
                                //设置编码字符集
                                String retSrc = EntityUtils.toString(entity1, "UTF-8");

                                //转换为json数据并且获取result中的数据
                                JSONArray storyList = new JSONObject(retSrc).getJSONArray("result");
                                //获取根标签的值
                                JSONObject sx = new JSONObject(retSrc);
                                String string11 = sx.get("status").toString();
                                //创建根目录
                                Element document = doc.createElement("document");
                                document.setAttribute("status", string11);
                                doc.appendChild(document);

                                //获取所有数据
                                JSONObject jsonObject = storyList.getJSONObject(0);

                                //获取第一层所有key
                                Iterator<String> keys = jsonObject.keys();
                                //遍历第一层
                                while (keys.hasNext()) {
                                        //获取key
                                        String next = keys.next();

                                        //获取值
                                        String string = jsonObject.get(next).toString();
                                        //创建第一层标签
                                        Element nextElement = doc.createElement(next);
                                        //判断值是否是json格式
                                        if (!string.endsWith("]") && !string.endsWith("}")) {
                                                System.out.println("1:" + next + "   " + string);
                                                //添加文本
                                                nextElement.setTextContent(string);
                                        }
                                        //添加标签
                                        document.appendChild(nextElement);
                                        //判断第二层是否是json数组
                                        if (string.endsWith("]")) {
                                                //获取json数组
                                                JSONArray jsonArray = jsonObject.getJSONArray(next);
                                                //遍历json数组
                                                int i = 0;
                                                while (i < jsonArray.length()) {
                                                        //获取json对象
                                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                        //获取json对象使用的key数组
                                                        Iterator<String> keys1 = jsonObject1.keys();
                                                        i++;
                                                        //遍历第二层json
                                                        while (keys1.hasNext()) {
                                                                //获取key
                                                                String next1 = keys1.next();
                                                                //获取值
                                                                String toString = jsonObject1.get(next1).toString();
                                                                //创建第二层标签
                                                                Element next1Element = doc.createElement(next1);
                                                                //判断是否为json对象
                                                                if (!toString.endsWith("}")) {
                                                                        System.out.println("2:" + next1 + "     " + toString);
                                                                        //添加文本
                                                                        next1Element.setTextContent(toString);
                                                                }
                                                                //添加节点
                                                                nextElement.appendChild(next1Element);
                                                                //判断第三层是否是json对象
                                                                if (toString.endsWith("}")) {
                                                                        //获取json对象
                                                                        JSONObject jsonObject3 = jsonObject1.getJSONObject(next1);
                                                                        //获取key数组
                                                                        Iterator<String> keys2 = jsonObject3.keys();
                                                                        //遍历第三层
                                                                        while (keys2.hasNext()) {
                                                                                //获取key
                                                                                String next2 = keys2.next();
                                                                                //创建第二层标签
                                                                                Element next2Element = doc.createElement(next2);
                                                                                //添加文本值
                                                                                next2Element.setTextContent(jsonObject3.getString(next2));
                                                                                //添加节点
                                                                                next1Element.appendChild(next2Element);
                                                                                System.out.println("4:" + next2 + "   " + jsonObject3.getString(next2));
                                                                        }
                                                                }

                                                        }
                                                }
                                                //判断第二层是否为json对象
                                        } else if (string.endsWith("}")) {
                                                //获取json对象
                                                JSONObject jsonObject2 = jsonObject.getJSONObject(next);
                                                //获取对象的key数组
                                                Iterator<String> keys1 = jsonObject2.keys();
                                                //遍历第二层
                                                while (keys1.hasNext()) {
                                                        //获取key
                                                        String next1 = keys1.next();
                                                        //创建第二层标签
                                                        Element next1Element = doc.createElement(next1);
                                                        //添加文本值
                                                        next1Element.setTextContent(jsonObject2.getString(next1));
                                                        //添加节点
                                                        nextElement.appendChild(next1Element);
                                                        System.out.println("3:" + next1 + "   " + jsonObject2.getString(next1));
                                                }
                                        }

                                }

                        }

                } catch (Exception e) {
                        System.out.println(e);
                }
                //TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(response.getOutputStream()));

                Source source = new DOMSource(doc);

                // 将XML源文件添加到模型中，以便XsltView能够检测
                ModelAndView model = new ModelAndView("XSLTViewXml");
                model.addObject("xmlSource", source);

                return model;
        }

        //获取xml- for 
        @RequestMapping(value = "/viewXmlFor")
        public void viewXmlFor2(HttpServletRequest request, HttpServletResponse response) throws Exception {
                //获取文档对象
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.newDocument();

                //创建请求方法
                HttpGet httpget = new HttpGet("https://redan-api.herokuapp.com/story/");

                //获取响应对象
                CloseableHttpResponse response1 = HttpClients.createDefault().execute(httpget);
                try {
                        //获取响应体
                        HttpEntity entity1 = response1.getEntity();
                        if (entity1 != null) {
                                //设置编码字符集
                                String retSrc = EntityUtils.toString(entity1, "UTF-8");

                                //转换为json数据并且获取result中的数据
                                JSONArray storyList = new JSONObject(retSrc).getJSONArray("result");
                                //获取根标签的值
                                JSONObject sx = new JSONObject(retSrc);
                                String string11 = sx.get("status").toString();
                                //创建根目录
                                Element document = doc.createElement("document");
                                document.setAttribute("status", string11);
                                doc.appendChild(document);

                                //获取所有数据
                                JSONObject jsonObject = storyList.getJSONObject(0);

                                //遍历第一层
                                for (Iterator<String> keys = jsonObject.keys(); keys.hasNext();) {

                                        //获取key
                                        String next = keys.next();
                                        //获取值
                                        String string = jsonObject.get(next).toString();
                                        //创建第一层标签
                                        Element nextElement = doc.createElement(next);
                                        //判断值是否是json格式
                                        if (!string.endsWith("]") && !string.endsWith("}")) {
                                                System.out.println("1:" + next + "   " + string);
                                                //添加文本
                                                nextElement.setTextContent(string);
                                        }
                                        //添加标签
                                        document.appendChild(nextElement);
                                        //判断第二层是否是json数组
                                        if (string.endsWith("]")) {
                                                //遍历json数组
                                                int i = 0;
                                                for (JSONArray jsonArray = jsonObject.getJSONArray(next); (i < jsonArray.length());) {
                                                        //获取json对象
                                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                        i++;
                                                        //遍历第二层json
                                                        for (Iterator<String> keys1 = jsonObject1.keys(); keys1.hasNext();) {
                                                                //获取key
                                                                String next1 = keys1.next();
                                                                //获取值
                                                                String toString = jsonObject1.get(next1).toString();
                                                                //创建第二层标签
                                                                Element next1Element = doc.createElement(next1);
                                                                //判断是否为json对象
                                                                if (!toString.endsWith("}")) {
                                                                        System.out.println("2:" + next1 + "     " + toString);
                                                                        //添加文本
                                                                        next1Element.setTextContent(toString);
                                                                }
                                                                //添加节点
                                                                nextElement.appendChild(next1Element);
                                                                //判断第三层是否是json对象
                                                                if (toString.endsWith("}")) {
                                                                        //获取json对象
                                                                        JSONObject jsonObject3 = jsonObject1.getJSONObject(next1);
                                                                        //遍历第三层
                                                                        for (Iterator<String> keys2 = jsonObject3.keys(); keys2.hasNext();) {
                                                                                //获取key
                                                                                String next2 = keys2.next();
                                                                                //创建第二层标签
                                                                                Element next2Element = doc.createElement(next2);
                                                                                //添加文本值
                                                                                next2Element.setTextContent(jsonObject3.getString(next2));
                                                                                //添加节点
                                                                                next1Element.appendChild(next2Element);
                                                                                System.out.println("4:" + next2 + "   " + jsonObject3.getString(next2));
                                                                        }
                                                                }

                                                        }
                                                }
                                                //判断第二层是否为json对象
                                        } else if (string.endsWith("}")) {
                                                //获取json对象
                                                JSONObject jsonObject2 = jsonObject.getJSONObject(next);
                                                //遍历第二层
                                                for (Iterator<String> keys1 = jsonObject2.keys(); keys1.hasNext();) {
                                                        //获取key
                                                        String next1 = keys1.next();
                                                        //创建第二层标签
                                                        Element next1Element = doc.createElement(next1);
                                                        //添加文本值
                                                        next1Element.setTextContent(jsonObject2.getString(next1));
                                                        //添加节点
                                                        nextElement.appendChild(next1Element);
                                                        System.out.println("3:" + next1 + "   " + jsonObject2.getString(next1));
                                                }
                                        }

                                }

                        }

                } catch (Exception e) {
                        System.out.println(e);
                }
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(response.getOutputStream()));

//                Source source = new DOMSource(doc);
//
//                // 将XML源文件添加到模型中，以便XsltView能够检测
//                ModelAndView model = new ModelAndView("XSLTViewXml");
//                model.addObject("xmlSource", source);
//
//                return model;
        }
}
