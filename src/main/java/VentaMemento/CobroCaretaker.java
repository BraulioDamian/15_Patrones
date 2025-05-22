package VentaMemento;

import java.util.Stack;
public class CobroCaretaker {
    private Stack<CobroMemento> mementos = new Stack<>();
    private Stack<CobroMemento> redoStack = new Stack<>();

    public void addMemento(CobroMemento memento){
        mementos.push(memento);
        redoStack.clear();
    }

    public CobroMemento deshacer(){
        if(!mementos.isEmpty()){
            CobroMemento memento = mementos.pop();
            redoStack.push(memento);
            return memento;
        }
        return null;
    }

    public CobroMemento rehacer(){
        if(!redoStack.isEmpty()){
            CobroMemento memento = redoStack.pop();
            mementos.push(memento);
            return memento;
        }
        return null;
    }
}
