class TreeNode<K, V> extends Entry<K, V> {
    TreeNode<K, V> left;
    TreeNode<K, V> right;
    TreeNode<K, V> parent;
    boolean isRed;  // Для реализации красно-черного дерева

    public TreeNode(K key, V value) {
        super(key, value);
        this.isRed = true; // Новые узлы начинаются красными
    }
}
