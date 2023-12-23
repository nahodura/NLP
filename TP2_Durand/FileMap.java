import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class FileMap{

    private HashMap<File, ArrayList<Integer>> fileMap;

    public FileMap() {
        this.fileMap = new HashMap<>();
    }


    public void addFile(File file, Integer position) {
        //If the file is not already in the map, add the file to the FileMap.
        if (!fileMap.containsKey(file)) {
            fileMap.put(file, new ArrayList<>());
        }
        //Add the position of the word to the FileMap.
        if (fileMap.get(file).contains(position)){
            return;
        }
        fileMap.get(file).add(position);
    }

    public ArrayList<Integer> getPositions(File file) {
        return fileMap.get(file);
    }

    public ArrayList<File> getNamesOfFiles() {
        ArrayList tab = new ArrayList();
        for (File file : fileMap.keySet()) {
            tab.add(file.getName());
        }
        return tab;
    }

    public ArrayList<File> getFiles() {
        return new ArrayList<>(fileMap.keySet());
    }
}
