public class CustomHashMap<K, V> {
    private Entry<K, V>[] table;
    private int capacity = 16;
    private int treeifyThreshold = 8; // Порог для преобразования списка в дерево

    public CustomHashMap() {
        table = new Entry[capacity];
    }

    private int getHash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    // Метод для добавления элемента
    public void put(K key, V value) {
        int index = getHash(key);
        Entry<K, V> existing = table[index];

        if (existing == null) {
            table[index] = new Entry<>(key, value);
        } else {
            int count = 0;
            Entry<K, V> current = existing;
            Entry<K, V> prev = null;

            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value; // Обновляем значение, если ключ уже существует
                    return;
                }
                prev = current;
                current = current.next;
                count++;
            }

            prev.next = new Entry<>(key, value);

            // Если количество элементов превышает порог, преобразуем в дерево
            if (count >= treeifyThreshold - 1) {
                table[index] = treeify(index);
            }
        }
    }

    // Метод для преобразования списка в дерево
    private TreeNode<K, V> treeify(int index) {
        Entry<K, V> head = table[index];
        TreeNode<K, V> root = null;

        while (head != null) {
            TreeNode<K, V> newNode = new TreeNode<>(head.key, head.value);

            if (root == null) {
                root = newNode;
            } else {
                // Вставляем элемент в бинарное дерево
                root = insertTreeNode(root, newNode);
            }

            head = head.next;
        }

        return root;
    }

    // Вставка узла в бинарное дерево
    private TreeNode<K, V> insertTreeNode(TreeNode<K, V> root, TreeNode<K, V> newNode) {
        if (root == null) {
            return newNode;
        }

        int cmp = newNode.key.hashCode() - root.key.hashCode();

        if (cmp < 0) {
            root.left = insertTreeNode(root.left, newNode);
        } else if (cmp > 0) {
            root.right = insertTreeNode(root.right, newNode);
        }

        return root;
    }

    // Метод для поиска элемента в дереве
    private V getFromTree(TreeNode<K, V> root, K key) {
        while (root != null) {
            int cmp = key.hashCode() - root.key.hashCode();

            if (cmp < 0) {
                root = root.left;
            } else if (cmp > 0) {
                root = root.right;
            } else {
                return root.value;
            }
        }
        return null;
    }

    // Метод для удаления элемента
    public void remove(K key) {
        int index = getHash(key);
        Entry<K, V> entry = table[index];

        if (entry == null) {
            return; // Ничего не делаем, если элементов нет
        }

        if (entry instanceof TreeNode) {
            // Если это дерево, удаляем из дерева
            table[index] = removeTreeNode((TreeNode<K, V>) entry, key);
        } else {
            // Если это связанный список, удаляем из списка
            table[index] = removeEntryFromList(entry, key);
        }
    }

    // Метод для удаления узла из дерева
    private TreeNode<K, V> removeTreeNode(TreeNode<K, V> root, K key) {
        if (root == null) return null;

        int cmp = key.hashCode() - root.key.hashCode();

        if (cmp < 0) {
            root.left = removeTreeNode(root.left, key);
        } else if (cmp > 0) {
            root.right = removeTreeNode(root.right, key);
        } else {
            // Узел найден, начинаем удаление
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // Узел с двумя дочерними узлами: ищем наименьший элемент в правом поддереве
            TreeNode<K, V> minNode = findMin(root.right);
            root.key = minNode.key;
            root.value = minNode.value;
            root.right = removeTreeNode(root.right, minNode.key);
        }
        return root;
    }

    // Поиск минимального узла (для замены при удалении узла с двумя дочерними)
    private TreeNode<K, V> findMin(TreeNode<K, V> root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    // Удаление элемента из связанного списка
    private Entry<K, V> removeEntryFromList(Entry<K, V> head, K key) {
        Entry<K, V> current = head;
        Entry<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    return current.next; // Удаление первого элемента
                } else {
                    prev.next = current.next; // Пропускаем удаляемый элемент
                }
                return head;
            }
            prev = current;
            current = current.next;
        }
        return head;
    }

    // Метод для получения значения по ключу
    public V get(K key) {
        int index = getHash(key);
        Entry<K, V> entry = table[index];

        if (entry instanceof TreeNode) {
            // Если это дерево, ищем в дереве
            return getFromTree((TreeNode<K, V>) entry, key);
        } else {
            // Если это связанный список, ищем по списку
            while (entry != null) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
                entry = entry.next;
            }
        }

        return null;
    }

    // Проверка наличия ключа
    public boolean containsKey(K key) {
        return get(key) != null;
    }
}
