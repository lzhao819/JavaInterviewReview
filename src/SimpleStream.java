import java.util.*;
import java.util.stream.Collectors;

public class SimpleStream {
    public static void main(String[] args){
        // 1. 过滤出长度>3 的字符串并转大写
        List<String> names = new ArrayList<>();
        names.add("A");
        List<String> l1 = names.stream()
                .filter(s->s.length()>3)
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        // 2. 整数去重后升序
        List<Integer> nums = new ArrayList<>();
        nums.add(1);
        List<Integer> l2 = nums.stream()
                .distinct().sorted().collect(Collectors.toList());

        // 3. 按年龄升序取前 3 个名字
        List<String> l3 = names.stream()
                .sorted(Comparator.comparing(Person::getAge))
                .limit(3).collect(Collectors.toList());

        // 4. 统计每个字母出现次数：Map<字母, 次数>
        String str = "aabbcc";
        Map<Character, Long> r4 = str.chars()
                .mapToObj(c->(char)c)
                .collect(Collectors.groupingBy(c->c,Collectors.counting()));

        // 5. 整数列表求和（reduce 与 summingInt 两种写法）
        int sum1 = nums.stream().mapToInt(n -> n).sum();
        int sum2 = nums.stream().reduce(0, Integer::sum);

        // 6. 按 id 转成 Map<id, name>（重复 key 时保留旧值）
        Map<Integer, String> r6 = persons.stream()
                .collect(Collectors.toMap(Person::getId, Person::getName, (oldV, newV) -> oldV));

        // 7. 按"是否偶数"分区：Map<Boolean, List<Integer>>
        Map<Boolean, List<Integer>> r7 = nums.stream()
                .collect(Collectors.partitioningBy(n->(n%2==0)));
        // 8. 嵌套列表扁平化
        List<List<Integer>> nested = Arrays.asList(Arrays.asList(1,2), Arrays.asList(3,4));
        List<Integer> r8 = nested.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList()); // [1,2,3,4]

        // 9. 匹配：是否存在大于 100 的数
        boolean r9 = nums.stream().anyMatch(n -> n > 100);

        // 10. 从"可能为空的列表"安全取第一个元素，没有就给默认
        String r10 = names.stream()
                .findFirst()
                .orElse("默认值");
    }


}
