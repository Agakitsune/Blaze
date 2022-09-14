package src.blaze.command.element;

public class KeywordElement extends Element<String> {

    public KeywordElement(String keyword, boolean isTemplate) {
        super(keyword, isTemplate);
    }

    @Override
    public boolean checkInput(String input) throws ElementException {
        return this.value == input;
    }
}
