package assignment2;

import java.util.HashMap;

public class History {
    private HashMap<Integer, String> history = new HashMap<Integer, String>();

    public History(HashMap history) {
        this.history.equals(history);
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }

    public void add(int num, String value) {
        history.put(num, value);
    }


    public void getHistoy(String[] prev) {
        for (HashMap.Entry<Integer, String> entry : history.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            System.out.println(prev[key] + "->" + value);
        }
        System.out.println("--------");
    }
}
