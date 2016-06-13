/*
 * 此代码创建于 2016年6月7日 下午3:50:12。
 */
package com.apollo.demos.base.jdk8;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.nanoTime;
import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;
import static java.util.Arrays.parallelPrefix;
import static java.util.Arrays.parallelSetAll;
import static java.util.Arrays.parallelSort;
import static java.util.Arrays.setAll;
import static java.util.Arrays.sort;
import static java.util.Collections.emptySet;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Stream {

    public static void main(String[] args) {
        List<Integer> numbers = asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        //功能：查找指定整数容器中大于3，小于7的数有几个。
        int count = 0;
        for (Integer number : numbers) {
            if (number > 3 && number < 7) {
                count++;
            }
        }
        System.out.println("Count = " + count);

        //同样的功能，使用Stream + Lambda表达式就简化到一句话完成。
        System.out.println("Count(Stream) = " + numbers.stream().filter(i -> i > 3 && i < 7).count());

        //惰性求值特性，这点在性能上很重要，类似于延迟加载，用得时候再计算。
        numbers.stream().filter(i -> {
            System.out.println("i = " + i);
            return i > 3 && i < 7;
        });//.count(); //不加count()就不会触发过滤执行。

        //把每个数打印出来。
        numbers.stream().forEach(i -> System.out.print(i + " ")); //打印每个数据。
        System.out.println();

        //或者使用更方便的joining提供了很方便的字符串拼接能力。
        System.out.println(numbers.stream().map(String::valueOf).collect(joining(",", "[", "]")));

        //collect和count是同一个级别的，但使用更为广泛。
        List<Integer> collected = numbers.stream().filter(i -> i > 5).collect(toList()); //收集过滤后的数据。
        System.out.println(collected.stream().map(String::valueOf).collect(joining(",", "[", "]")));

        //功能：把所有数字加10。
        collected = new ArrayList<Integer>();
        for (Integer number : numbers) {
            collected.add(number + 10);
        }
        System.out.println(collected.stream().map(String::valueOf).collect(joining(",", "[", "]")));

        //map写法同样精简，map和filter功能各有用处，是同一个级别的工具。
        collected = numbers.stream().map(i -> i + 10).collect(toList());
        System.out.println(collected.stream().map(String::valueOf).collect(joining(",", "[", "]")));

        //flatMap用于多个子集的stream扁平化，有些场景下非常有用的。
        collected = of(numbers, asList(10, 11, 12)).flatMap(is -> is.stream()).map(i -> i + 10).collect(toList());
        System.out.println(collected.stream().map(String::valueOf).collect(joining(",", "[", "]")));

        //求集合最小值和最大值，comparing实际上是接受一个函数，返回另外一个函数，是函数式编程的典型思维。
        System.out.println("Min = " + numbers.stream().min(comparing(i -> i)).get());
        System.out.println("Max = " + numbers.stream().max(comparing(i -> i)).get());

        //reduce是一种更加通用的迭代模式，实际上count，min和max都是reduce操作。
        System.out.println("Sum = " + numbers.stream().reduce(0, (s, i) -> s + i));

        //找出所有偶数，并且加10后输出，典型的链式用法。
        collected = numbers.stream().filter(i -> i % 2 == 0).map(i -> i + 10).collect(toList());
        System.out.println(collected.stream().map(String::valueOf).collect(joining(",", "[", "]")));

        //mapToInt返回的是一个IntStream，尽管Java对基本类型有自动装箱拆箱，但针对基本类型的Stream可以避免装箱拆箱，性能更好，JDK8支持int，long，double。
        IntSummaryStatistics stats = numbers.stream().mapToInt(i -> i).summaryStatistics();
        System.out.printf("Min = %d, Max = %d, Ave = %f, Sum = %d\n", stats.getMin(), stats.getMax(), stats.getAverage(), stats.getSum());

        //Set是无序的，所以Set的stream也是无序的，不要对顺序有期待。
        collected = new HashSet<>(asList(3, 2, 1)).stream().collect(toList());
        System.out.println(collected.stream().map(String::valueOf).collect(joining(",", "[", "]")));

        //toCollection允许指定特定的Collection，这里::的语法很精简。
        collected = numbers.stream().collect(toCollection(LinkedList::new));
        System.out.println(collected.stream().map(String::valueOf).collect(joining(",", "[", "]")));

        //最大值，最小值和平均值的另一种实现。
        System.out.println("Min = " + numbers.stream().collect(minBy(comparing(i -> i))).get());
        System.out.println("Max = " + numbers.stream().collect(maxBy(comparing(i -> i))).get());
        System.out.println("Ave = " + numbers.stream().collect(averagingInt(i -> i)));

        //把奇数和偶数分成两个组，分别输出。
        Map<Boolean, List<Integer>> p = numbers.stream().collect(partitioningBy(i -> i % 2 == 0));
        System.out.println(p.get(true).stream().map(String::valueOf).collect(joining(",", "[", "]")));
        System.out.println(p.get(false).stream().map(String::valueOf).collect(joining(",", "[", "]")));

        //groupingBy比partitioningBy支持更高的灵活度，groupingBy可以分多于2组的情况。
        Map<String, List<Integer>> g = numbers.stream().collect(groupingBy(i -> i % 2 == 0 ? "even" : "odd"));
        System.out.println(g.get("even").stream().map(String::valueOf).collect(joining(",", "[", "]")));
        System.out.println(g.get("odd").stream().map(String::valueOf).collect(joining(",", "[", "]")));

        //用reduce完成joining字符串拼接的功能，稍显啰嗦。
        System.out.println(numbers.stream()
                                  .map(String::valueOf)
                                  .reduce(new StringJoiner(",", "[", "]"), StringJoiner::add, StringJoiner::merge)
                                  .toString());

        //reducing是对收集器的归一化处理，这是一个统一的实现，看上去有些恶心，这也是我们需要定制Collector的原因。
        System.out.println(numbers.stream()
                                  .map(String::valueOf)
                                  .collect(reducing(new StringJoiner(",", "[", "]"),
                                                    i -> new StringJoiner(",", "[", "]").add(i),
                                                    StringJoiner::merge))
                                  .toString());

        //自定义Collector。
        System.out.println(numbers.stream().map(String::valueOf).collect(new StringCollector(",", "[", "]")));

        //Map新增了一下有用的方法。
        Cache c = new Cache();
        System.out.println(c.getValue1(1));
        System.out.println(c.getValue2(2));
        c.showCache1();
        c.showCache2();

        //准备并发数据。
        Integer[] data = new Integer[1000]; //不要单纯的尝试增加数据量，在isOdd中只有i % 2 == 0;一句时，使用1个亿的数据量依然是串行Stream更快。
        setAll(data, i -> i);
        List<Integer> is = asList(data);

        //并发的Stream性能有明显提升。
        long start = currentTimeMillis();
        System.out.println("Count = " + is.stream().filter(Stream::isOdd).count() + " (Elapse " + (currentTimeMillis() - start) + ")");
        start = currentTimeMillis();
        System.out.println("Count(Parallel) = " + is.parallelStream().filter(Stream::isOdd).count() + " (Elapse " + (currentTimeMillis() - start) + ")");

        //比较SetAll并发和非并发的性能差异。
        start = nanoTime();
        setAll(data, i -> i);
        System.out.println("Set All Elapse " + (nanoTime() - start));
        start = nanoTime();
        parallelSetAll(data, i -> i);
        System.out.println("Parallel Set All Elapse " + (nanoTime() - start));

        //比较SetAll并发和非并发的性能差异。
        start = nanoTime();
        sort(data);
        System.out.println("Sort Elapse " + (nanoTime() - start));
        start = nanoTime();
        parallelSort(data);
        System.out.println("Parallel Sort Elapse " + (nanoTime() - start));

        //Prefix只有并发的，可以累计叠加值。
        System.out.println(asList(data).subList(0, 10).stream().map(String::valueOf).collect(joining(",", "[", "]")));
        parallelPrefix(data, Integer::sum); //parallelPrefix会修改原数组，产生的结果通过data体现。
        System.out.println(asList(data).subList(0, 10).stream().map(String::valueOf).collect(joining(",", "[", "]")));
    }

    private static boolean isOdd(Integer i) {
        try {
            sleep(1);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return i % 2 == 0;
    }

    public static class StringCollector implements Collector<String, StringJoiner, String> {

        private final String m_delimiter;

        private final String m_prefix;

        private final String m_suffix;

        public StringCollector(String delimiter, String prefix, String suffix) {
            m_delimiter = delimiter;
            m_prefix = prefix;
            m_suffix = suffix;
        }

        @Override
        public Supplier<StringJoiner> supplier() {
            return () -> new StringJoiner(m_delimiter, m_prefix, m_suffix);
        }

        @Override
        public BiConsumer<StringJoiner, String> accumulator() {
            return StringJoiner::add;
        }

        @Override
        public BinaryOperator<StringJoiner> combiner() {
            return StringJoiner::merge;
        }

        @Override
        public Function<StringJoiner, String> finisher() {
            return StringJoiner::toString;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return emptySet();
        }

    }

    public static class Cache {

        private Map<Integer, String> m_cache = new HashMap<>();

        public String getValue1(Integer key) {
            String value = m_cache.get(key);

            if (value == null) {
                value = readValueFromDB(key);
                m_cache.put(key, value);
            }

            return value;
        }

        public String getValue2(Integer key) {
            return m_cache.computeIfAbsent(key, this::readValueFromDB); //this::这种写法很特别，只有这样才能传递成员方法。
        }

        public void showCache1() {
            for (Entry<Integer, String> entry : m_cache.entrySet()) {
                System.out.println("Key = " + entry.getKey() + " , Value = " + entry.getValue());
            }
        }

        public void showCache2() {
            m_cache.forEach((k, v) -> System.out.println("Key = " + k + " , Value = " + v));
        }

        private String readValueFromDB(Integer key) {
            return key.toString();
        }

    }

}
