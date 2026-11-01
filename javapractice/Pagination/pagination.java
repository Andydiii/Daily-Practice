import java.util.List;
import java.util.ArrayList;
class pagination {
    public static List<Integer> getPage(List<Integer> items, int pageNumber, int pageSize) {
        int start = pageNumber * pageSize;
        
        if (start >= items.size()) {
            return new ArrayList<> ();
        }

        int from = start;
        int to = Math.min(start + pageSize, items.size());
        
        ArrayList newArray = new ArrayList<> ();
        for (int i = from; i < to; i++) {
            newArray.add(items.get(i));
        }

        return newArray;
    }

    public static void main(String[] args) {
        List<Integer> items = new ArrayList<> ();
        items.add(1);
        items.add(2);
        items.add(3);
        items.add(4);
        items.add(5);
        List<Integer> page = getPage(items, 0, 2);
        for (int i = 0; i < page.size(); i++) {
            System.out.println(page.get(i));
        }
    }
}