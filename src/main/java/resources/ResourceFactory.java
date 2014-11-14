package resources;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ResourceFactory {
    private static ResourceFactory resourceFactory = null;

    public static ResourceFactory instance () {
        if (resourceFactory != null) {
            return resourceFactory;
        } else {
            resourceFactory = new ResourceFactory();
            return resourceFactory;
        }
    }

    private ResourceFactory () {

    }

    public static Object getObject(String xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            Handler handler = new Handler();
            saxParser.parse(xmlFile, handler);

            return handler.getObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
