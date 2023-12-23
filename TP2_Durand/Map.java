import static java.util.Objects.hash;

public class Map {
    private Entry[] entries;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public Map() {
        entries = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    private static class Entry {
        private Object key;
        private Object value;
        private Entry next;

        public Entry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = hash(key);
        Entry current = entries[index];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        Entry newEntry = new Entry(key, value);
        newEntry.next = entries[index];
        entries[index] = newEntry;
        size++;
    }

    public String get(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = hash(key);
        Entry current = entries[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return (String) current.value;
            }
            current = current.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int hash(Object key) {
        return Math.abs(key.hashCode()) % entries.length;
    }

    public static void main(String[] args) {
        Map map = new Map();
        map.put("hello", "world");
        map.put("hello", "world2");
        map.put("hello2", "world");
        System.out.println(map.get("hello"));
        System.out.println(map.get("hello2"));
        System.out.println(map.get("hello3"));
        System.out.println(map.size());
        System.out.println(map.isEmpty());
    }
}
