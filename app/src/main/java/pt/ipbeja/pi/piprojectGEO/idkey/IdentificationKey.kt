package pt.ipbeja.pi.piprojectGEO.idkey

import android.content.Context
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.SAXException
import java.io.IOException
import java.io.InputStream
import java.util.Locale
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

/**
 * Object describing an identification key and static methods to load from a XML file,
 * an identification key is described by a set of question nodes and a set of result nodes all
 * identified by a string. The result nodes are the terminal nodes that conclude an identification.
 */
class IdentificationKey
private constructor() {
    private val orders: MutableList<String> = ArrayList()

    private val nodes = HashMap<String, QuestionNode>()
    private val results = HashMap<String, ResultNode>()

    fun getOrders(): List<String> {
        return orders
    }

    /**
     * Adds a question node to the key
     *
     * @param node a question node
     */
    fun addQuestion(node: QuestionNode) {
        nodes[node.id] = node
    }

    /**
     * Adds a result node to the key
     *
     * @param node a result node
     */
    fun addResult(node: ResultNode) {
        results[node.id] = node
        val s = node.ordem
        orders.add(s)
    }

    /**
     * Gets a question node from the key with the id
     *
     * @param id the id of the node
     * @return the question node
     */
    fun getQuestion(id: String): QuestionNode? {
        return nodes[id]
    }

    /**
     * Gets a result node from the key with the id
     *
     * @param id the id of the node
     * @return the result node
     */
    fun getResult(id: String): ResultNode? {
        return results[id]
    }

    fun getResultByOrder(order: String): ResultNode? {
        val id = orders.indexOf(order) + 1
        val sId = "R$id"
        return results[sId]
    }

    /**
     * Checks if the node with the id is a question node
     *
     * @param id the id of the node
     * @return true if the node is a question node false otherwise
     */
    fun isQuestion(id: String): Boolean {
        return nodes.containsKey(id)
    }

    /**
     * Checks if the node with the id is a result node
     *
     * @param id the id of the node
     * @return true if the node is a result node false otherwise
     */
    fun isResult(id: String): Boolean {
        return results.containsKey(id)
    }

    companion object {
        private var instance: IdentificationKey? = null

        @Throws(IOException::class)
        fun getInstance(ctx: Context): IdentificationKey? {
            if (instance == null) {
                // https://stackoverflow.com/questions/4212320/get-the-current-language-in-device
                // JPBXML
                val s = Locale.getDefault().language
                var `is`: InputStream? = null
                `is` = if (s.substring(0, 2) == "pt") {
                    ctx.assets.open("chave.xml")
                } else {
                    ctx.assets.open("chave-en.xml")
                }
                instance = loadXML(`is`)
            }
            return instance
        }

        /**
         * Creates a node option from the XML element
         *
         * @param e the option XML element
         * @return the node option
         */
        private fun parseOption(e: Element): KeyOption {
            val gotoId = e.getAttribute("goto")

            val text = e.getElementsByTagName("text").item(0).childNodes.item(0).nodeValue
            val description =
                e.getElementsByTagName("description").item(0).childNodes.item(0).nodeValue
            val imageLocation =
                e.getElementsByTagName("imageLocation").item(0).childNodes.item(0).nodeValue

            return KeyOption(gotoId, description, imageLocation, text)
        }

        /**
         * Creates a question node from the XML element
         *
         * @param e the question XML element
         * @return the question node
         */
        private fun parseQuestion(e: Element): QuestionNode {
            val id = e.getAttribute("id")
            val question = e.getElementsByTagName("question").item(0).childNodes.item(0).nodeValue

            val options = e.getElementsByTagName("options").item(0) as Element

            val eA = options.getElementsByTagName("option").item(0) as Element
            val eB = options.getElementsByTagName("option").item(1) as Element

            val optionA = parseOption(eA)
            val optionB = parseOption(eB)

            val questionNode = QuestionNode(id, question, optionA, optionB)

            return questionNode
        }

        /**
         * Creates a result node from the XML element
         *
         * @param e the result XML element
         * @return the result node
         */
        private fun parseResult(e: Element): ResultNode {
            val id = e.getAttribute("id")
            val ordem = e.getElementsByTagName("ordem").item(0).childNodes.item(0).nodeValue
            val description =
                e.getElementsByTagName("description").item(0).childNodes.item(0).nodeValue
            val imageLocation =
                e.getElementsByTagName("imageLocation").item(0).childNodes.item(0).nodeValue

            val resultNode = ResultNode(id, ordem, description, imageLocation)

            return resultNode
        }

        /**
         * Loads a key XML creating an identification key object
         * The XML file should follow the following structure
         *
         * - chave
         * - nodes
         * - node
         * - question
         * - options
         * - option
         * - text
         * - description
         * - imageLocation
         * - option
         * - ...
         * - ...
         * - results
         * - result
         * - ordem
         * - description
         * - imageLocation
         * - ...
         *
         * @param is input stram of an XML file describing the key
         * @return the identification key object
         */
        fun loadXML(`is`: InputStream?): IdentificationKey? {
            val key = IdentificationKey()

            try {
                val dbFactory = DocumentBuilderFactory.newInstance()
                val dBuilder = dbFactory.newDocumentBuilder()
                val doc = dBuilder.parse(`is`)

                val nodes = doc.getElementsByTagName("nodes").item(0) as Element
                var nList = nodes.getElementsByTagName("node")

                for (i in 0 until nList.length) {
                    val tempNode = nList.item(i)
                    if (tempNode.nodeType == Node.ELEMENT_NODE) {
                        val question = parseQuestion(tempNode as Element)
                        key.addQuestion(question)
                        //Log.e("$$$$$$$$$$$$->", "Encontrou");
                    }
                }

                val results = doc.getElementsByTagName("results").item(0) as Element
                nList = results.getElementsByTagName("result")

                for (i in 0 until nList.length) {
                    val tempNode = nList.item(i)
                    if (tempNode.nodeType == Node.ELEMENT_NODE) {
                        val result = parseResult(tempNode as Element)
                        key.addResult(result)
                        //Log.e("$$$$$$$$$$$$->", "Encontrou Resultado");
                    }
                }

                return key
            } catch (e: ParserConfigurationException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: SAXException) {
                e.printStackTrace()
            }

            return null
        }
    }
}