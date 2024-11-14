package pt.ipbeja.pi.piprojectGEO.idkey

/**
 * Node representing an intermediate step in an identification, identified by a node id,
 * contains the question relevant to the step, and both options for the answers.
 */
class QuestionNode

/**
 * Creates a question node with the question and both options.
 *
 * @param id identification of the node
 * @param question the question of this step
 * @param optionA the option A of this step
 * @param optionB the option B of this step
 */(
    /**
     * Get the id of the node
     *
     * @return the id of the node
     */
    val id: String,
    /**
     * Get the question of the step
     *
     * @return the question of the step
     */
    val question: String,
    /**
     * Get the A option of the step
     *
     * @return the A option of the step
     */
    val optionA: KeyOption,
    /**
     * Get the B option of the step
     *
     * @return the B option of the step
     */
    val optionB: KeyOption
) {
    /**
     * The string representation of the node
     *
     * @return the string representation of the node
     */
    override fun toString(): String {
        return "QuestionNode{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", optionA=" + optionA +
                ", optionB=" + optionB +
                '}'
    }
}