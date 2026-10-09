# 第1周周一 · Java集合与HashMap复习讲义

**本讲义对应计划文档「第1周·周一」：**时段A 集合框架总览与 HashMap 原理（1\.5h）＋时段B 读 HashMap 源码（1\.5h）＋时段C 链表算法（1h）。全部内容已浓缩在本文档中，**不需要再查任何外部资料**。建议顺序：先通读「一、二」建立整体认知，再对照「三」的源码理解细节，最后用「四」自测、按「五」练算法。

# 一、集合框架总览（约15分钟）

Java 集合分两大体系：**Collection**（单列，存一个个元素）和 **Map**（双列，存键值对）。List 有序可重复，Set 无序不可重复，Queue 是队列；Map 按 key 组织。面试常考的具体类：ArrayList、LinkedList、Vector、HashSet、LinkedHashSet、TreeSet、HashMap、LinkedHashMap、TreeMap、Hashtable、ConcurrentHashMap。

今天先吃透两个最常被拿来对比的：**ArrayList 与 LinkedList**，以及面试绝对主角 **HashMap**。

|维度|ArrayList|LinkedList|
|---|---|---|
|**底层结构**|Object\[\] 数组，连续内存|双向链表（Node 含 prev/data/next）|
|**随机访问 get\(i\)**|O\(1\)，直接按下标取|O\(n\)，需从头/尾遍历|
|**中间插入/删除**|O\(n\)，要移动后续元素|先 O\(n\) 定位，再 O\(1\) 改指针|
|**尾部追加**|均摊 O\(1\)，满时扩容|O\(1\)|
|**扩容机制**|默认容量 10，扩容为原 1\.5 倍（Arrays\.copyOf 拷数组）|无容量概念，随用随建节点|
|**内存特征**|连续内存、CPU 缓存局部性好|每个节点多两个指针，内存开销更大|
|**适用场景**|查询多、尾部增删（日常最常用）|频繁头尾操作（可当双端队列）|
|**线程安全**|否|否|

**一句话记忆：**ArrayList 像连续数组（查快），LinkedList 像手拉手的节点链（头尾增删快）；实际业务中 ArrayList 用得更多，因为连续内存更友好。

# 二、HashMap 原理精讲（时段A 核心，约60分钟）

## 底层结构：从「数组\+链表」到「数组\+链表\+红黑树」

HashMap 的底层是 **Node 数组（table）＋ 冲突时挂链表**。key 经 hash 定位到数组下标，多个 key 撞到同一个下标就串成链表。JDK 1\.8 起，当**链表长度 ≥ 8 且数组长度 ≥ 64** 时，链表转成**红黑树**，把最坏查询从 O\(n\) 降到 O\(logn\)；当红黑树节点数 ≤ 6 时再退回链表。

默认参数：初始容量 16，加载因子 0\.75，扩容阈值 threshold = 容量 × 0\.75（第一次扩容发生在第 13 个元素插入时）。

## put 完整流程（必须能默写）

1. 初始化：判断hashmap是否为空，若为空则**resize\(\)**进行扩容

2. 定位桶（寻址）：二次哈希\-\-求hash再计算下标

    1. h = key\.hashCode\(\)；最终 hash = h ^ \(h \>\>\> 16\)——高 16 位与低 16 位异或，让高位参与桶定位，减少碰撞。

    2. i = \(n \- 1\) \& hash，等价于 hash % n（n 为 2 的幂时），位运算更快。

3. 如果没有碰撞则直接放入桶中

4. 有碰撞，按链表尾插法或红黑树插入

    - 如果链表长度超过阈值\(treeify\_threshold 8\)，且总节点超过64，链表转化成红黑树

    - 如果链表长度低于\(untreeify\_threshold=6\)就把红黑树转回成链表

    - 如果节点\(key\)已经存在就覆盖旧值并返回旧值

5. 如果桶满了\(容量16\*负载因子load\_factor0\.75\) 则resize为原来容量的2倍

6. 返回null

## 扩容机制 resize（1\.8 的高明之处）

当 size \> threshold 时触发扩容：**容量翻倍**（新容量 = 旧容量 \<\< 1），threshold 同步翻倍。元素迁移时**不用重新计算 hash**，只看元素 hash 在「新增的那一位」：\`e\.hash \& oldCap\` 为 0 的留在原位置，为 1 的移到「原位置 \+ oldCap」。迁移用两条链表（lo 链/hi 链）整体搬运

JDK 1\.8 的尾插法避免了 1\.7 头插法在多线程扩容数据迁移时容易死循环的问题。

- 由于1\.7链表采用头插法，数据迁移过程中并没有产生新的对象而是改变了节点的引用：迁移前 A\-\>B, 迁移后 B\-\>A，两个线程同时迁移易造成B\-\>A\-\>B死循环

- 1\.8扩容采用尾插法，保持了原来的顺序，避免了死循环

## 三个高频「为什么」（背熟，面试必问）

|问题|答案|
|---|---|
|**为什么容量是 2 的幂**|① 定位用 \(n\-1\) \& hash 而非取模，n 是 2 的幂时二者等价且位运算更快；② 扩容翻倍后，元素只用看新增的一位（e\.hash \& oldCap），迁移简单高效。|
|**为什么加载因子是 0\.75**|时间和空间的折中：太大冲突多、查询变慢，太小频繁扩容浪费空间。官方按泊松分布计算，0\.75 下链表长度到 8 的概率约千万分之六，可以认为几乎不会发生。|
|**为什么树化阈值 8、退化阈值 6**|树节点约是普通节点的 2 倍内存，冲突到 8 个才值得付出树化代价；退化设 6 是留 2 个数的缓冲，避免频繁「树化↔退化」来回抖动。|
|**为什么树化还要求数组 ≥ 64**|数组容量小的时候，扩容比树化更划算——treeifyBin 里若数组长度 \< 64，只会触发扩容、不会真正树化。|

## hash 扰动函数（为什么高 16 位异或低 16 位）

定位下标时是 \`\(n\-1\) \& hash\`，而 n\-1 通常很小（如 16 只有低 4 位是 1），**key 的 hashCode 高 16 位根本不参与运算**，碰撞概率高。于是先用 \`h ^ \(h \>\>\> 16\)\` 把高 16 位「搅」进低 16 位，让高位也影响桶定位，减少碰撞。null key 的 hash 记为 0，落在 table\[0\]。

## JDK 1\.7 vs 1\.8 对比（面试最爱问）

|维度|JDK 1\.7|JDK 1\.8|
|---|---|---|
|**底层结构**|数组 \+ 链表|数组 \+ 链表 \+ 红黑树|
|**插入方式**|头插法|尾插法|
|**扩容迁移**|重新计算 hash、逐个 rehash 插入|按 e\.hash \& oldCap 拆成两条链，整链搬移|
|**并发风险**|头插 \+ 多线程扩容可能形成环形链表，get 死循环|尾插解决死循环，但仍有数据覆盖、丢失问题|
|**树化**|无|链表 ≥ 8 且数组 ≥ 64 转红黑树|

## 高频追问合集（顺带背熟）

- **允许 null key/value？**允许。null key 的 hash 为 0，放在 table\[0\] 的链表/树里。

- **与 Hashtable 的区别？**Hashtable 不允许 null，方法用 synchronized 加全表锁，性能差，已过时。

- **与 ConcurrentHashMap 的区别？**1\.7 用分段锁；1\.8 用 CAS \+ synchronized 只锁桶，并发粒度更细。

- **为什么重写 equals 必须重写 hashCode？**HashMap 先比 hash 再比 equals；hashCode 不同直接判定为不同 key，会出现「逻辑相等却 get 不到」。

- **HashMap 线程不安全的体现？**1\.7 扩容死循环；1\.8 并发 put 数据覆盖、size 不准。

- **查询时间复杂度？**平均 O\(1\)；冲突严重时最坏 O\(logn\)（红黑树）。

# 三、实战：读 HashMap 源码（时段B，约90分钟）

打开你本地的 JDK 源码（IDEA 里按住 Ctrl 点 HashMap 即可），按下面路线读，再对照本文的解读核对理解。

**阅读路线：**static hash\(\) → put\(\) → putVal\(\) → resize\(\) → treeifyBin\(\) → get\(\)。重点看 putVal 和 resize 的分支逻辑。

```java
static final int hash(Object key) {
    int h;
    // 高16位与低16位异或：让高位参与桶定位，减少碰撞
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    // ① 数组为空 → 先扩容初始化（默认容量16）
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    // ② 桶为空 → 直接放新节点
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        // ③ 桶首节点就是相同 key → 记下 e 待覆盖
        if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        // ④ 桶是红黑树 → 走树插入
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            // ⑤ 链表：尾插遍历
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    // 链表长度达到8（binCount == 7）→ 尝试树化
                    if (binCount >= TREEIFY_THRESHOLD - 1)
                        treeifyBin(tab, hash);
                    break;
                }
                if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
                    break; // 找到相同 key
                p = e;
            }
        }
        // ⑥ 找到相同 key → 覆盖旧值并返回旧值
        if (e != null) {
            V oldValue = e.value;
            e.value = value;
            return oldValue;
        }
    }
    ++modCount;
    // ⑦ 元素数超过阈值 → 扩容
    if (++size > threshold)
        resize();
    return null;
}
```

**解读：**putVal 的 ⑦ 步与上面的流程一一对应。注意第 ⑤ 步里 \`binCount\` 从 0 开始，插入的是第 9 个节点时 binCount == 7，此时触发 treeifyBin——这正对应「链表长度 ≥ 8」的条件。

```java
final void treeifyBin(Node<K,V>[] tab, int hash) {
    int n, index; Node<K,V> e;
    // 数组长度 < 64 → 先扩容，不树化（容量小的时候扩容更划算）
    if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
        resize();
    // 数组长度 ≥ 64 且桶非空 → 链表转红黑树
    else if ((e = tab[index = (n - 1) & hash]) != null) {
        // 把链表节点逐个转成 TreeNode，再构建红黑树
        TreeNode<K,V> hd = null, tl = null;
        do {
            TreeNode<K,V> p = replacementTreeNode(e, null);
            if (tl == null) hd = p;
            else { p.prev = tl; tl.next = p; }
            tl = p;
        } while ((e = e.next) != null);
        if ((tab[index] = hd) != null)
            hd.treeify(tab);
    }
}
```

**解读：**MIN\_TREEIFY\_CAPACITY = 64。所以「链表 ≥ 8」只是必要条件，数组长度 ≥ 64 才会真正树化；否则先走 resize\(\) 扩容。

```java
// 新容量 = 旧容量 << 1（翻倍），threshold 同步翻倍
// 迁移时只看元素 hash 在「新增的那一位」：
if ((e.hash & oldCap) == 0) {
    // 该位为 0 → 留在原位置（lo 链表）
    if (loTail == null) loHead = e;
    else loTail.next = e;
    loTail = e;
} else {
    // 该位为 1 → 移到「原位置 + oldCap」（hi 链表）
    if (hiTail == null) hiHead = e;
    else hiTail.next = e;
    hiTail = e;
}
// 迁移结束：table[j] = loHead，table[j + oldCap] = hiHead
```

**解读：**oldCap 是 2 的幂（如 16 = 10000b），\`e\.hash \& oldCap\` 只看 hash 的第 5 位。这就是「容量是 2 的幂」带来的红利——不用重算 hash，一次位运算决定去留。

**今日输出物（做完才算完成时段B）：**

* [x] 对照上面的流程图，自己在纸上画一遍 put 流程图（不要看文档）

* [x] 口头解释「为什么 binCount == 7 时树化」和「为什么 MIN\_TREEIFY\_CAPACITY = 64」

* [x] 在源码里找到 resize\(\)，说出 lo/hi 两条链的用途

# 四、今日自测（口述 10 分钟，答不上就回看对应小节）

1. 口述 HashMap 的 put 完整流程（对着「二、put 完整流程」自查）。

2. 1\.8 为什么引入红黑树？树化的两个条件是什么？

3. 为什么容量必须是 2 的幂？

4. 加载因子为什么是 0\.75？

5. 树化阈值为什么是 8？退化阈值为什么是 6？

6. 1\.7 与 1\.8 有哪些差异？1\.7 为什么会死循环？

7. hash 方法为什么把高 16 位异或到低 16 位？

8. ArrayList 与 LinkedList 的区别，以及各自适用场景？

# 五、算法参考（时段C 附赠，两道链表题）

## 反转链表

思路：迭代法，三个指针 prev / cur / next，逐个把节点的 next 指向前驱。

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;   // 前驱，最终成为新链表头
        ListNode cur = head;    // 当前要处理的节点
        while (cur != null) {
            ListNode next = cur.next; // ① 先保存后继，防止断链
            cur.next = prev;          // ② 反转当前节点的指针
            prev = cur;               // ③ prev 前移
            cur = next;               // ④ cur 前移
        }
        return prev; // 原链表尾即新链表头
    }
}
// 时间复杂度 O(n)，空间复杂度 O(1)
```

## 合并两个有序链表

思路：哨兵节点（dummy）简化头节点处理，双指针比较取值，最后接上剩余部分。

```java
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0); // 哨兵节点，避免单独处理头节点
        ListNode tail = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }
        // 接上剩余部分（两个链表最多只剩一个非空）
        tail.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }
}
// 时间复杂度 O(m+n)，空间复杂度 O(1)
```

做完建议去 LeetCode 提交验证（搜索题号 206、21），AC 即完成今日算法目标。

> （注：部分内容由豆包工作 AI 生成）
