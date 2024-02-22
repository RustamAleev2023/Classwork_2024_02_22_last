import java.util.*;

public class Main {
    public static void main(String[] args) {
//        task1();
//        task2();
//        task3();
//        task4();
        task5();
//        task6();
//        task7();

    }

    //Task1
    //Напишите метод, который на вход получает коллекцию объектов, а возвращает коллекцию уже без дубликатов.
    public static void task1() {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(1);
        numbers.add(2);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        System.out.println(removeDuplicate(numbers));
    }

    public static <T> Collection<T> removeDuplicate(Collection<T> collection) {
        return new HashSet<>(collection);
    }

    //Task2
    //Напишите метод, который добавляет 1000000 элементов в ArrayList и LinkedList.
    // Напишите еще один метод, который выбирает из заполненного списка элемент наугад 100000 раз.
    // Замерьте время, которое потрачено на это.
    // Сравните результаты и предположите, почему они именно такие.
    public static void task2(){
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            arrayList.add(random.nextInt(100));
            linkedList.add(random.nextInt(100));
        }
        long startTime1 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            getElement(arrayList, random.nextInt(100000));
        }
        long endTime1 = System.nanoTime();

        long startTime2 = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            getElement(linkedList, random.nextInt(100000));
        }
        long endTime2 = System.nanoTime();
        System.out.println("Поиск в ArrayList = " + (endTime1 - startTime1));
        System.out.println("Поиск в LinkedList = " + (endTime2 - startTime2));
    }
    public static int getElement(List<Integer> collection, int index) {
        return collection.get(index);
    }

    //Task3
    //Написать итератор по массиву
    public static void task3(){
        Integer[] array = {1, 2, 3, 4, 5};
        ArrayIterator<Integer> iterator = new ArrayIterator<Integer>(array);
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
    static class ArrayIterator<T> implements Iterator<T> {
        private T[] array;
        private int index = 0;

        public ArrayIterator(T[] array){
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return index < array.length;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return array[index++];
        }
    }

    //Task4
    //Напишите итератор по двумерному массиву.
    public static void task4(){
        Integer[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9, 10}};
        Array2dIter<Integer> iter = new Array2dIter<>(array);

        while (iter.hasNext()){
            System.out.println(iter.next());
        }
    }
    static class Array2dIter<T> implements Iterator<T> {
        private T[][] array;
        private int col;
        private int row;

        public Array2dIter(T[][] array) {
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            while (row < array.length && array[row].length == col) {
                row++;
                col = 0;
            }
            return row < array.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return array[row][col++];
        }
    }

    //Task5
    //Дан итератор. Метод next() возвращает либо String, либо итератор такой же структуры
    //(то есть который опять возвращает или String, или такой же итератор).
    //Напишите поверх этого итератора другой, уже «плоский».
    //однорешение рекурсиное второе на стеках
    public static void task5() {
//        List<Object> list1 = new ArrayList<>();
//        list1.add("hello");
//        list1.add("world");
//        List<Object> list2 = new ArrayList<>();
//        list2.add(123);
//        list2.add(456);
//        List<Object> list3 = new ArrayList<>();
//        list3.add("Java");
//        list3.add("coding");
//        List<Object> mainList = new ArrayList<>();
//        mainList.add(list1.iterator());
//        mainList.add(list2.iterator());
//        mainList.add(list3.iterator());
//        DeepIterator iterator = new DeepIterator(mainList.iterator());
//        while (iterator.hasNext()){
//            System.out.println(iterator.next);
//        }

        List<Object> list = new ArrayList<>();
        list.add("Hello");
        list.add(Arrays.asList("World", "!", Arrays.asList("Welcome", "to", "Java")));
//        list.add("world");

        Iterator<String> iterator = new DeepIterator(list.iterator());

        while (iterator.hasNext()) {
            String element = iterator.next();
            System.out.println(element);
        }

    }
    static class DeepIterator implements Iterator<String> {
        private Stack<Iterator<?>> iterators;
        private String next;
        private boolean hasNext;

        public DeepIterator(Iterator<?> iterator) {
            this.iterators = new Stack<Iterator<?>>();
            iterators.push(iterator);

            updateNext();
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        private void updateNext(){

            if(iterators.empty()){
                next = null;
                hasNext = false;
                return;
            }

            Iterator current = iterators.peek();

            if (current.hasNext()) {
                Object o = current.next();
                if (o instanceof String) {
                    next = (String) o;
                    hasNext = true;
                } else if (o instanceof Iterator) {
                    Iterator iterator = (Iterator) o;
                    iterators.push(iterator);
                    updateNext();
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                iterators.pop();
                updateNext();
            }
        }

        @Override
        public String next(){

            if(!hasNext){
                throw new NoSuchElementException();
            }

            String nextToReturn = next;
            updateNext();
            return nextToReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    //Task6
    //Напишите итератор, который проходит по двум итератором.
    public static void task6(){
        ArrayList<Integer> list1 = new ArrayList<>();
        int[] numbers1 = new int[]{2,4,6,8};
        for (int i = 0; i < numbers1.length; i++) {
            list1.add(numbers1[i]);
        }
        ArrayList<Integer> list2 = new ArrayList<>();
        int[] numbers2 = new int[]{1,3,5,7,9};
        for (int i = 0; i < numbers2.length; i++) {
            list2.add(numbers2[i]);
        }

        Iterator<Integer> iterator1 = list2.iterator();
        Iterator<Integer> iterator2 = list1.iterator();

        MyIterator<Integer> iterator = new MyIterator<>(iterator1, iterator2);
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
    public static class MyIterator<T> implements Iterator<T> {
        private Iterator<T> iterator1;
        private Iterator<T> iterator2;

        public MyIterator (Iterator<T> iterator1, Iterator<T> iterator2) {
            this.iterator1 = iterator1;
            this.iterator2 = iterator2;
        }

        @Override
        public boolean hasNext() {
            while (iterator1.hasNext()) return true;
            while (iterator2.hasNext()) return true;
            return false;
        }

        @Override
        public T next() {
            if(!hasNext())
                throw new NoSuchElementException();

            while (iterator1.hasNext()) return iterator1.next();
            while (iterator2.hasNext()) return iterator2.next();
            return null;
        }
    }

    //Task7
    //Напишите метод, который получает на вход массив элементов типа К (Generic)
    //и возвращает Map<K, Integer>, где K — значение из массива, а Integer — количество вхождений в массив.
    //То есть сигнатура метода выглядит так:
    //<K> Map<K, Integer> arrayToMap(K[] ks);
    public static void task7(){

    }
    public static <K> Map<K, Integer> arrayToMap(K[] ks){
        Map<K, Integer> result = new HashMap<>();
        int count = 0;
        for (int i = 0; i < ks.length; i++) {
            count = 0;
            for (int j = 0; j < ks.length; j++) {
                if(ks[i].equals(ks[j])) {
                    count++;
                }
            }
            result.put(ks[i], count);
        }
        return result;
    }

}