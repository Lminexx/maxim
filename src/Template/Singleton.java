package Template;

import Rabbits.Rabbit;

import java.util.*;

public class Singleton implements Iterable<Rabbit> {

    private static Singleton instance = null;
    private Vector<Rabbit> VectorList;
    private TreeSet<Integer> treeSet;
    private HashMap<Integer, Rabbit> hashMap;
    public Singleton() {
        this.VectorList = new Vector<>();
        this.treeSet = new TreeSet<>();
        this.hashMap = new HashMap<>();
    }
    public static Singleton getInstance() {
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

    public void addObj(Rabbit rabbit){
        VectorList.add(rabbit);
        treeSet.add(rabbit.hashCode());
        hashMap.put(rabbit.timeBorn, rabbit);
    }
    public void clear(){
        VectorList.clear();
    }
    public void removeObj(Rabbit rabbit){
        VectorList.remove(rabbit);
       // treeSet.remove(rabbit);
        hashMap.remove(rabbit.timeBorn, rabbit);

    }
    @Override
    public Iterator<Rabbit> iterator() {
        return VectorList.iterator();
    }

    public Vector<Rabbit> getVectorList() {
        return VectorList;
    }

    public TreeSet<Integer> getTreeSet() {
        return treeSet;
    }

    public HashMap<Integer, Rabbit> getHashMap() {
        return hashMap;
    }
}
