package hr.game.tinyepicdungeonsadventures.docs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DocumentationGenerator {

    private static final Logger log = LoggerFactory.getLogger(DocumentationGenerator.class);

    private static final String MODIFIER_CELL_START = "<td><span class='modifier'>";
    private static final String TYPE_CELL_START = "<td><span class='type'>";
    private static final String NAME_CELL_START = "<td><span class='name'>";
    private static final String SPAN_CELL_END = "</span></td>";
    private static final String TR_START = "<tr>";
    private static final String TR_END = "</tr>\n";
    private static final String TABLE_END = "</table>\n";

    public static void main(String[] args) {
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Project Documentation</title>\n");
        htmlBuilder.append("<style>" +
                "body { font-family: Arial, sans-serif; margin: 20px; }" +
                "h1 { color: #2E4053; }" +
                "h2 { color: #5D6D7E; border-bottom: 2px solid #AEB6BF; padding-bottom: 5px; }" +
                "table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }" +
                "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
                "th { background-color: #f2f2f2; }" +
                ".modifier { font-style: italic; color: #7F8C8D; }" +
                ".type { color: #2980B9; }" +
                ".name { font-weight: bold; }" +
                "</style>\n");
        htmlBuilder.append("</head>\n<body>\n");
        htmlBuilder.append("<h1>Project Documentation</h1>\n");

        try {
            String basePackage = "hr.game.tinyepicdungeonsadventures";
            List<Class<?>> classes = findClasses(basePackage);
            for (Class<?> c : classes) {
                if (!c.equals(DocumentationGenerator.class)) {
                    htmlBuilder.append(generateClassHtml(c));
                }
            }
        } catch (Exception ex) {
            log.error("An error occurred during documentation generation.", ex);
        }

        htmlBuilder.append("</body>\n</html>");

        try (FileWriter writer = new FileWriter("documentation.html")) {
            writer.write(htmlBuilder.toString());
            log.info("Documentation successfully generated into documentation.html");
        } catch (IOException ex) {
            log.error("Error writing documentation file.", ex);
        }
    }

    private static String generateClassHtml(Class<?> c) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>Class: ").append(c.getSimpleName()).append("</h2>\n");

        sb.append("<h3>Fields</h3>\n<table>\n");
        sb.append("<tr><th>Modifiers</th><th>Type</th><th>Name</th></tr>\n");
        for (Field field : c.getDeclaredFields()) {
            sb.append(TR_START);
            sb.append(MODIFIER_CELL_START).append(Modifier.toString(field.getModifiers())).append(SPAN_CELL_END);
            sb.append(TYPE_CELL_START).append(field.getType().getSimpleName()).append(SPAN_CELL_END);
            sb.append(NAME_CELL_START).append(field.getName()).append(SPAN_CELL_END);
            sb.append(TR_END);
        }
        sb.append(TABLE_END);

        sb.append("<h3>Constructors</h3>\n<table>\n");
        sb.append("<tr><th>Modifiers</th><th>Name</th><th>Parameters</th></tr>\n");
        for (Constructor<?> constructor : c.getDeclaredConstructors()) {
            sb.append(TR_START);
            sb.append(MODIFIER_CELL_START).append(Modifier.toString(constructor.getModifiers())).append(SPAN_CELL_END);
            sb.append(NAME_CELL_START).append(constructor.getDeclaringClass().getSimpleName()).append(SPAN_CELL_END);
            sb.append("<td>");
            for (Class<?> paramType : constructor.getParameterTypes()) {
                sb.append("<span class='type'>").append(paramType.getSimpleName()).append("</span> ");
            }
            sb.append("</td>");
            sb.append(TR_END);
        }
        sb.append(TABLE_END);

        sb.append("<h3>Methods</h3>\n<table>\n");
        sb.append("<tr><th>Modifiers</th><th>Return Type</th><th>Name</th><th>Parameters</th></tr>\n");
        for (Method method : c.getDeclaredMethods()) {
            sb.append(TR_START);
            sb.append(MODIFIER_CELL_START).append(Modifier.toString(method.getModifiers())).append(SPAN_CELL_END);
            sb.append(TYPE_CELL_START).append(method.getReturnType().getSimpleName()).append(SPAN_CELL_END);
            sb.append(NAME_CELL_START).append(method.getName()).append(SPAN_CELL_END);
            sb.append("<td>");
            for (Class<?> paramType : method.getParameterTypes()) {
                sb.append("<span class='type'>").append(paramType.getSimpleName()).append("</span> ");
            }
            sb.append("</td>");
            sb.append(TR_END);
        }
        sb.append(TABLE_END);

        return sb.toString();
    }

    private static List<Class<?>> findClasses(String basePackage) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = basePackage.replace('.', '/');
        URL resource = classLoader.getResource(path);

        if (resource == null) {
            log.warn("Could not find package directory for: {}", basePackage);
            return new ArrayList<>();
        }

        File directory = new File(resource.getFile().replace("%20", " "));
        List<Class<?>> classes = new ArrayList<>();
        findClassesInDirectory(directory, basePackage, classes);
        return classes;
    }

    private static void findClassesInDirectory(File directory, String packageName, List<Class<?>> classes) throws ClassNotFoundException {

        if (!directory.exists())
            return;

        File[] files = directory.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (file.isDirectory()) {
                findClassesInDirectory(file, packageName + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class") && !file.getName().contains("$")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
    }
}
