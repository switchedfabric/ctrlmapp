import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class XMLHelper {
	public static String formatXML(String input)
    {
        try
        {
            final Document document = parseXml(input);
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(3);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
            return input;
        }
    }

    private static Document parseXml(String in)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            Document doc = db.parse(is);
            return doc;
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String getElement(String xml, String key)
    {
        try
        {
        	// System.out.println("Looking for [" + key + "] in " + formatXML(xml));
            Document doc = parseXml(xml);
            
            NodeList nodeList = doc.getElementsByTagName(key);
            // Element rootElement = doc.getDocumentElement();
            // NodeList nodes = rootElement.getChildNodes();

            return ((Element)nodeList.item(0)).getTextContent();
            
            /*
            for(int i=0; i < nodes.getLength(); i++){
            	Node node = nodes.item(i);

              if(node instanceof Element){
                //a child element to process
                Element child = (Element) node;
                // String attribute = child.getAttribute("width");
                System.out.println("---> " + child.getNodeName() + ", " + child.getNodeType() + ", attr is " + Node.ELEMENT_NODE);
                System.out.println("---> " + child.getTagName() + ", " + child.getTextContent());
              }
            }
            return ""; // node.getNodeValue();
            */
            
        } catch (Exception e)
        {
        	System.out.println("Some error in getElement:");
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
