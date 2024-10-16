package pt.ipbeja.pi.piproject.idkey;

import android.content.Context;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Object describing an identification key and static methods to load from a XML file,
 * an identification key is described by a set of question nodes and a set of result nodes all
 * identified by a string. The result nodes are the terminal nodes that conclude an identification.
 */
public class IdentificationKey
{
    private final List<String> orders;
    private HashMap<String, QuestionNode> nodes;
    private HashMap<String, ResultNode> results;

    private static IdentificationKey instance = null;

    public static IdentificationKey getInstance(Context ctx) throws IOException {
        if(instance == null) {
            // https://stackoverflow.com/questions/4212320/get-the-current-language-in-device
            // JPBXML
            String s = Locale.getDefault().getLanguage();
            InputStream is = null;
            if (s.substring(0,2).equals("pt")) {
                is = ctx.getAssets().open("chave.xml");
            }
            else {
                is = ctx.getAssets().open("chave-en.xml");
            }
            instance = IdentificationKey.loadXML(is);
        }
        return instance;
    }

    public List<String> getOrders() {
        return orders;
    }

    /**
     * Creates and identification key object
     */
    private IdentificationKey()
    {
        this.nodes = new HashMap<String, QuestionNode>();
        this.results = new HashMap<String, ResultNode>();
        this.orders = new ArrayList<>();
    }

    /**
     * Adds a question node to the key
     *
     * @param node a question node
     */
    public void addQuestion(QuestionNode node)
    {
        this.nodes.put(node.getId(), node);
    }

    /**
     * Adds a result node to the key
     *
     * @param node a result node
     */
    public void addResult(ResultNode node)
    {
        this.results.put(node.getId(), node);
        String s = node.getOrdem();
        this.orders.add(s);
    }

    /**
     * Gets a question node from the key with the id
     *
     * @param id the id of the node
     * @return the question node
     */
    public QuestionNode getQuestion(String id)
    {
        return this.nodes.get(id);
    }

    /**
     * Gets a result node from the key with the id
     *
     * @param id the id of the node
     * @return the result node
     */
    public ResultNode getResult(String id)
    {
        return this.results.get(id);
    }

    public ResultNode getResultByOrder(String order)
    {
        int id = this.orders.indexOf(order) + 1;
        String sId = "R" + id;
        return this.results.get(sId);
    }

    /**
     * Checks if the node with the id is a question node
     *
     * @param id the id of the node
     * @return true if the node is a question node false otherwise
     */
    public boolean isQuestion(String id)
    {
        return this.nodes.containsKey(id);
    }

    /**
     * Checks if the node with the id is a result node
     *
     * @param id the id of the node
     * @return true if the node is a result node false otherwise
     */
    public boolean isResult(String id)
    {
        return this.results.containsKey(id);
    }

    /**
     * Creates a node option from the XML element
     *
     * @param e the option XML element
     * @return the node option
     */
    private static KeyOption parseOption(Element e)
    {
        String gotoId = e.getAttribute("goto");

        String text = e.getElementsByTagName("text").item(0).getChildNodes().item(0).getNodeValue();
        String description = e.getElementsByTagName("description").item(0).getChildNodes().item(0).getNodeValue();
        String imageLocation = e.getElementsByTagName("imageLocation").item(0).getChildNodes().item(0).getNodeValue();

        return new KeyOption(gotoId, description, imageLocation, text);
    }

    /**
     * Creates a question node from the XML element
     *
     * @param e the question XML element
     * @return the question node
     */
    private static QuestionNode parseQuestion(Element e)
    {
        String id = e.getAttribute("id");
        String question = e.getElementsByTagName("question").item(0).getChildNodes().item(0).getNodeValue();

        Element options = (Element) e.getElementsByTagName("options").item(0);

        Element eA = (Element) options.getElementsByTagName("option").item(0);
        Element eB = (Element) options.getElementsByTagName("option").item(1);

        KeyOption optionA = parseOption(eA);
        KeyOption optionB = parseOption(eB);

        QuestionNode questionNode = new QuestionNode(id, question, optionA, optionB);

        return questionNode;
    }

    /**
     * Creates a result node from the XML element
     *
     * @param e the result XML element
     * @return the result node
     */
    private static ResultNode parseResult(Element e)
    {
        String id = e.getAttribute("id");
        String ordem = e.getElementsByTagName("ordem").item(0).getChildNodes().item(0).getNodeValue();
        String description = e.getElementsByTagName("description").item(0).getChildNodes().item(0).getNodeValue();
        String imageLocation = e.getElementsByTagName("imageLocation").item(0).getChildNodes().item(0).getNodeValue();

        ResultNode resultNode = new ResultNode(id, ordem, description, imageLocation);

        return resultNode;
    }

    /**
     * Loads a key XML creating an identification key object
     * The XML file should follow the following structure
     *
     * - chave
     *    - nodes
     *        - node
     *          - question
     *          - options
     *              - option
     *                  - text
     *                  - description
     *                  - imageLocation
     *              - option
     *                  - ...
     *        - ...
     *    - results
     *        - result
     *          - ordem
     *          - description
     *          - imageLocation
     *        - ...
     *
     * @param is input stram of an XML file describing the key
     * @return the identification key object
     */
    public static IdentificationKey loadXML(InputStream is)
    {
        IdentificationKey key = new IdentificationKey();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element nodes = (Element) doc.getElementsByTagName("nodes").item(0);
            NodeList nList = nodes.getElementsByTagName("node");

            for(int i = 0; i < nList.getLength(); i++)
            {

                Node tempNode = nList.item(i);
                if(tempNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    QuestionNode question = parseQuestion((Element) tempNode);
                    key.addQuestion(question);
                    //Log.e("$$$$$$$$$$$$->", "Encontrou");
                }
            }

            Element results = (Element) doc.getElementsByTagName("results").item(0);
            nList = results.getElementsByTagName("result");

            for(int i = 0; i < nList.getLength(); i++)
            {

                Node tempNode = nList.item(i);
                if(tempNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    ResultNode result = parseResult((Element) tempNode);
                    key.addResult(result);
                    //Log.e("$$$$$$$$$$$$->", "Encontrou Resultado");
                }
            }

            return key;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }
}
