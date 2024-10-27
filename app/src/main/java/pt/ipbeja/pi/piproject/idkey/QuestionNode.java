//package pt.ipbeja.pi.piproject.idkey;
//
///**
// * Node representing an intermediate step in an identification, identified by a node id,
// * contains the question relevant to the step, and both options for the answers.
// */
//public class QuestionNode
//{
//    private String id;
//    private String question;
//    private KeyOption optionA;
//    private KeyOption optionB;
//
//    /**
//     * Creates a question node with the question and both options.
//     *
//     * @param id identification of the node
//     * @param question the question of this step
//     * @param optionA the option A of this step
//     * @param optionB the option B of this step
//     */
//    public QuestionNode(String id, String question, KeyOption optionA, KeyOption optionB) {
//        this.id = id;
//        this.question = question;
//        this.optionA = optionA;
//        this.optionB = optionB;
//    }
//
//    /**
//     * Get the id of the node
//     *
//     * @return the id of the node
//     */
//    public String getId() {
//        return id;
//    }
//
//    /**
//     * Get the question of the step
//     *
//     * @return the question of the step
//     */
//    public String getQuestion() {
//        return question;
//    }
//
//    /**
//     * Get the A option of the step
//     *
//     * @return the A option of the step
//     */
//    public KeyOption getOptionA() {
//        return optionA;
//    }
//
//    /**
//     * Get the B option of the step
//     *
//     * @return the B option of the step
//     */
//    public KeyOption getOptionB() {
//        return optionB;
//    }
//
//    /**
//     * The string representation of the node
//     *
//     * @return the string representation of the node
//     */
//    @Override
//    public String toString() {
//        return "QuestionNode{" +
//                "id='" + id + '\'' +
//                ", question='" + question + '\'' +
//                ", optionA=" + optionA +
//                ", optionB=" + optionB +
//                '}';
//    }
//}
