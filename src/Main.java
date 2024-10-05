public class Main {
    public static void main(String[] args) {
        // Создаем объект нашей кастомной HashMap
        CustomHashMap<String, Integer> map = new CustomHashMap<>();

        // Добавляем несколько значений
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
        map.put("Four", 4);
        map.put("Five", 5);
        map.put("Six", 6);
        map.put("Seven", 7);
        map.put("Eight", 8);
        map.put("Nine", 9);

        // До добавления 9-го элемента все должно храниться в списке
        // Преобразование списка в дерево происходит на 9-ом элементе при colliison

        // Получаем значения по ключу
        System.out.println("Key 'One': " + map.get("One")); // Output: 1
        System.out.println("Key 'Nine': " + map.get("Nine")); // Output: 9

        // Пробуем найти несуществующий ключ
        System.out.println("Key 'Ten': " + map.get("Ten")); // Output: null

        // Добавляем дополнительные значения для демонстрации работы дерева
        map.put("Ten", 10);
        map.put("Eleven", 11);
        map.put("Twelve", 12);

        // Проверяем, что значения успешно добавились
        System.out.println("Key 'Ten': " + map.get("Ten")); // Output: 10
        System.out.println("Key 'Twelve': " + map.get("Twelve")); // Output: 12

        // Проверяем работу удаления
        map.remove("One");
        System.out.println("Key 'One' after removal: " + map.get("One")); // Output: null

        // Пробуем найти уже удалённый ключ
        System.out.println("Contains 'One'? " + map.containsKey("One")); // Output: false
    }
}